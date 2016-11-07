package agilepuppers.cleanwater.model;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextDatabase<T extends HashMapConvertible> {

    private final String filePath;
    private final String UID;
    private final String columnDelimiter;
    private final String keyValueDelimiter;

    private final HashMapConvertible.Factory<T> factory;

    private final Queue<Action> actionQueue;

    /**
     * @param filePath          the path to database file
     * @param UID               the property or column that is used to compare entries. intended to stand to "unique ID".
     * @param columnDelimiter   the character (or string) that is used to split up the columns of each entry
     * @param keyValueDelimiter the character (or string) used to split up each key/value pair
     * @param factory           instance of the factory that can create a new instance of the modeled object from a hashmap
     */
    public TextDatabase(String filePath, String UID, String columnDelimiter, String keyValueDelimiter, HashMapConvertible.Factory<T> factory) {
        this.filePath = filePath;
        this.UID = UID;
        this.columnDelimiter = columnDelimiter;
        this.keyValueDelimiter = keyValueDelimiter;

        this.factory = factory;

        actionQueue = new LinkedList<>();
    }

    /**
     * @param filePath the path to database file
     * @param UID      the property or column that is used to compare entries. intended to stand to "unique ID".
     * @param factory  instance of the factory that can create a new instance of the modeled object from a hashmap
     */
    public TextDatabase(String filePath, String UID, HashMapConvertible.Factory<T> factory) {
        this(filePath, UID, "|", "=", factory);
    }

    /**
     * Creates the database file if it doesn't already exist.
     *
     * @return the database file
     * @throws IOException if the database file cannot be accessed
     */
    private File getDatabaseFile() throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // create all parent directories
        file.createNewFile(); // create the file if it doesn't already exist
        return file;
    }

    /**
     * Parses an array of key/value pairs into a HashMap
     *
     * @param properties an array of key/value pairs
     * @return a HashMap representation of the given array
     */
    private HashMap<String, String> hashMapFromPropertyList(String[] properties) {
        HashMap<String, String> entry = new HashMap<>();

        for (String property : properties) {
            if (property.contains(keyValueDelimiter)) {
                String[] pair = property.split(Pattern.quote(keyValueDelimiter));
                if (pair.length < 2) continue;
                entry.put(pair[0], pair[1]);
            }
        }

        return entry;
    }

    /**
     * Uses the modelClass to instantiate an object from the given HashMap
     *
     * @param hashMap the HashMap to use
     * @return a fully instantiated object. Returns null if the modelClass doesn't have the appropriate constructor.
     */
    private T modelObjectFromHashMap(HashMap<String, String> hashMap) {
        if (hashMap == null) return null;
        return factory.fromHashMap(hashMap);
    }

    /**
     * Queues an ADD action, does not do it immediately
     *
     * @param item item to add to the database
     */
    public void queueAddEntry(T item) {
        if (item == null) return;
        actionQueue.add(new Action(ActionType.ADD_ROW, item));
    }

    /**
     * Immediately performs an ADD action.
     *
     * @param item item to add to the database
     * @throws IOException if the database file cannot be accessed
     */
    public void addEntry(T item) throws IOException {
        this.queueAddEntry(item);
        this.flushQueue();
    }

    /**
     * Queues an UPDATE action, does not do it immediately
     *
     * @param item item to update in the database
     */
    public void queueUpdateEntry(T item) {
        if (item == null) return;
        actionQueue.add(new Action(ActionType.UPDATE_ROW, item));
    }

    /**
     * Immediately performs an UPDATE action.
     *
     * @param item the item to update in the database
     * @throws IOException if the database file cannot be accessed
     */
    public void updateEntry(T item) throws IOException {
        this.queueUpdateEntry(item);
        this.flushQueue();
    }

    /**
     * Queues a REMOVE action, does not do it immediately
     *
     * @param item item to remove from the database
     */
    public void queueRemoveEntry(T item) {
        if (item == null) return;
        actionQueue.add(new Action(ActionType.REMOVE_ROW, item));
    }

    /**
     * Immediately performs a REMOVE action.
     *
     * @param item item to remove from the database
     * @throws IOException if the database file cannot be accessed
     */
    public void removeEntry(T item) throws IOException {
        this.queueRemoveEntry(item);
        this.flushQueue();
    }

    /**
     * Returns the entry with the specified id as a hashmap
     *
     * @param id the unique identifier of the item to be queried
     * @return hashmap representing the entry queried
     * @throws IOException if the database file cannot be accessed
     */
    public HashMap<String, String> queryEntryInfo(String id) throws IOException {
        if (id == null) return null;
        File file = this.getDatabaseFile();

        List<String> lines = Files.readAllLines(file.toPath());
        for (String line : lines) {
            if (line.trim().length() == 0) continue;
            String[] columns = line.split(Pattern.quote(columnDelimiter));
            HashMap<String, String> entry = hashMapFromPropertyList(columns);
            if (id.equals(entry.get(UID))) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Returns a fully instantiated object with the specified id
     *
     * @param id the unique identifier of the item to be queried
     * @return a fully instantiated object. Returns null if the object doesn't exist,
     * or the modelClass doesn't have the appropriate constructor.
     * @throws IOException if the database file cannot be accessed
     */
    public T queryEntry(String id) throws IOException {
        HashMap<String, String> entry = this.queryEntryInfo(id);
        if (entry == null) {
            return null;
        }

        return this.modelObjectFromHashMap(entry);
    }

    /**
     * Get a list of all items in the database
     *
     * @return an array of all items in the database
     * @throws IOException if the database file cannot be accessed
     */
    public List<T> queryAllEntries() throws IOException {
        File file = this.getDatabaseFile();
        List<String> lines = Files.readAllLines(file.toPath());

        //map the lines into model objects

        return lines.stream().map(line -> {
            String[] columns = line.split(Pattern.quote(columnDelimiter));
            HashMap<String, String> entry = this.hashMapFromPropertyList(columns);
            return this.modelObjectFromHashMap(entry);
        }).filter(object -> {
            return object != null;
        }).collect(Collectors.toList());
    }

    /**
     * Attempts to do all queued actions
     *
     * @throws IOException if the database file cannot be accessed
     */
    public void flushQueue() throws IOException {
        File file = this.getDatabaseFile();

        //create a list of lines in the database, parsed into HashMaps
        List<HashMap<String, String>> data = new ArrayList<>();

        List<String> lines = Files.readAllLines(file.toPath());
        for (String line : lines) {
            if (line.trim().length() == 0) continue;
            String[] columns = line.split(Pattern.quote(columnDelimiter));
            HashMap<String, String> entry = hashMapFromPropertyList(columns);
            data.add(entry);
        }

        //perform actions in the queue
        while (actionQueue.peek() != null) {
            Action action = actionQueue.poll();
            HashMap<String, String> newEntry = action.item.toHashMap();

            if (newEntry.get(UID) != null) { //if the new entry is valid

                //perform ADD or UPDATE action
                if (action.type == ActionType.ADD_ROW || action.type == ActionType.UPDATE_ROW) {

                    //if the entry already exists in the databse, update the entry
                    boolean exists = false;
                    for (int i = 0; i < data.size(); ++i) {
                        if (newEntry.get(UID).equals(data.get(i).get(UID))) {
                            exists = true;
                            data.set(i, newEntry);
                        }
                    }

                    //if the entry doesn't exist in the database, add it
                    if (!exists) {
                        data.add(newEntry);
                    }

                } else if (action.type == ActionType.REMOVE_ROW) {
                    //remove the rows matching the given entry
                    data.removeIf(row -> newEntry.get(UID).equals(row.get(UID)));
                }
            }
        }

        //rewrite the file with edits
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false)));
        for (HashMap<String, String> entry : data) {
            List<String> columnList = new ArrayList<>();

            //sanitize keys and values
            entry.forEach((key, value) -> {
                if (key != null && value != null && !key.equals("") && !value.equals("")) {
                    key = key.replaceAll(Pattern.quote(columnDelimiter), "");
                    value = value.replaceAll(Pattern.quote(columnDelimiter), "");
                    key = key.replaceAll(Pattern.quote(keyValueDelimiter), "");
                    value = value.replaceAll(Pattern.quote(keyValueDelimiter), "");
                    columnList.add(key + keyValueDelimiter + value);
                }
            });

            //write the entry if it isn't empty
            if (columnList.size() > 0) {
                String line = columnList.get(0);
                for (int i = 1; i < columnList.size(); ++i) {
                    line += columnDelimiter + columnList.get(i);
                }

                out.println(line);
            }
        }

        out.close();
    }

    class Action {
        final ActionType type;
        final T item;

        Action(ActionType type, T item) {
            this.type = type;
            this.item = item;
        }
    }

    enum ActionType {
        NONE, ADD_ROW, UPDATE_ROW, REMOVE_ROW
    }

}
