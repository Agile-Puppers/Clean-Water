package agilepuppers.cleanwater.model;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

public class TextDatabase<T extends HashMapConvertible> {

    private final String filePath;
    private final String UID;
    private final String delimiter;
    private final String assoc;

    private final Queue<Action> actionQueue;

    public TextDatabase(String filePath, String UID, String delimiter, String assoc) {
        this.filePath = filePath;
        this.UID = UID;
        this.delimiter = delimiter;
        this.assoc = assoc;
        actionQueue = new LinkedList<>();
    }

    private HashMap<String, String> hashMapFromPropertyList(String[] properties) {
        HashMap<String, String> entry = new HashMap<>();
        for (String property : properties) {
            if (property.contains(assoc)) {
                String[] pair = property.split(Pattern.quote(assoc));
                if (pair.length < 2) continue;
                entry.put(pair[0], pair[1]);
            }
        }
        return entry;
    }

    public void queueAddRow(T item) {
        if (item == null) return;
        actionQueue.add(new Action(ActionType.ADDROW, item));
    }

    public void queueUpdateRow(T item) {
        if (item == null) return;
        actionQueue.add(new Action(ActionType.UPDATEROW, item));
    }

    public void queueRemoveRow(T item) {
        if (item == null) return;
        actionQueue.add(new Action(ActionType.REMOVEROW, item));
    }

    public HashMap<String, String> queryRow(String id) throws IOException {
        if (id == null) return null;
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // create all parent directories
        file.createNewFile(); // create the file if it doesn't already exist

        List<String> lines = Files.readAllLines(file.toPath());
        for (String line : lines) {
            if (line.trim().length() == 0) continue;
            String[] columns = line.split(Pattern.quote(delimiter));
            HashMap<String, String> entry = hashMapFromPropertyList(columns);
            if (id.equals(entry.get(UID))) {
                return entry;
            }
        }
        return null;
    }

    public void flushQueue() throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // create all parent directories
        file.createNewFile(); // create the file if it doesn't already exist

        List<HashMap<String, String>> data = new ArrayList<>();

        List<String> lines = Files.readAllLines(file.toPath());
        for (String line : lines) {
            if (line.trim().length() == 0) continue;
            String[] columns = line.split(Pattern.quote(delimiter));
            HashMap<String, String> entry = hashMapFromPropertyList(columns);
            data.add(entry);
        }

        actionQueue.stream().forEach(action -> {
            HashMap<String, String> entry = action.item.toHashMap();
            if (entry.get(UID) != null) {
                if (action.type == ActionType.ADDROW || action.type == ActionType.UPDATEROW) {
                    boolean exists = false;
                    for (int i = 0; i < data.size(); ++i) {
                        if (entry.get(UID).equals(data.get(i).get(UID))) {
                            exists = true;
                            data.set(i, entry);
                        }
                    }
                    if (!exists) data.add(entry);
                } else if (action.type == ActionType.REMOVEROW) {
                    data.removeIf(row -> entry.get(UID).equals(row.get(UID)));
                }
            }
        });

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false)));
        for (HashMap<String, String> entry : data) {
            List<String> columnList = new ArrayList<>();
            entry.forEach((key, value) -> columnList.add(key + assoc + value));
            out.println(StringUtils.join(columnList, delimiter));
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
        NONE, ADDROW, UPDATEROW, REMOVEROW
    }

}