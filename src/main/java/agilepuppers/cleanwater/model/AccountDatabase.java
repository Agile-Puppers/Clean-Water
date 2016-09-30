package agilepuppers.cleanwater.model;

import agilepuppers.cleanwater.App;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountDatabase {

    // non-instantiable class. (static methods only)
    private AccountDatabase() {}

    private static File databaseFile;


    /**
     * Sets the databaseFile
     * @param path the relative path of the file to use
     */
    public static void setFile(String path) {
        databaseFile = new File(path);
    }


    /**
     * Creates the databaseFile if it does not exist
     */
    private static void createFile() {
        if (databaseFile != null && !databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                App.current.error(App.FATAL, "Could not create user account database");
            }
        }
    }

    /**
     * Loads and parses date from the databaseFile.
     * Each line is expeted to be a comma-separated-list of key/value pairs.
     *
     * @return a List of Hash Maps where each entry in the List corresponds to a line in the file.
     */
    private static List<HashMap<String, String>> loadData() {
        createFile();
        try {
            //read lines from the .txt
            List<String> lines = Files.readAllLines(databaseFile.toPath());
            List<HashMap<String, String>> data = new ArrayList<>();

            //parse each line individually
            for (String line : lines) {

                //each line is a comma-separated-list of key/value pairs
                String[] columns = line.split(",");
                HashMap<String, String> dictionary = new HashMap<>();

                for (String keyValuePair : columns) {
                    if (keyValuePair.contains("=")) {
                        String[] keyValue = keyValuePair.split("=");
                        String key = keyValue[0];
                        String value = keyValue[1];

                        dictionary.put(key, value);
                    }
                }

                if (dictionary.size() > 0) {
                    data.add(dictionary);
                }

            }

            return data;
        } catch (IOException e) {
            App.current.error(App.FATAL, "Could not load user account data");
            return null;
        }
    }

    /**
     * Persists the given User Account to the databaseFile
     *
     * @param account a fully-instantiated User Account to save to disk
     * @return true if the operation was successful
     */
    public static boolean addAccount(UserAccount account) {
        if (getUserAccount(account.getUsername()) != null) {
            return false;
        }

        String entry = account.serialize();

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(databaseFile.getPath(), true)));
            out.println(entry);
            out.close();
        } catch (IOException e) {
            App.current.error(App.RECOVERABLE, "Could not access user account database");
            return false;
        }
        return true;
    }

    /**
     * Retrieves a User Account object from the databaseFile
     *
     * @param username the username of the account to retrieve
     * @return the User Account object for the give username. Returns null if there is no matching account.
     */
    public static UserAccount getUserAccount(String username) {
        List<HashMap<String, String>> data = loadData();
        for (HashMap<String, String> userInfo : data) {

            //deserialize into UserAccount object
            UserAccount account = new UserAccount(userInfo);

            if (account.getUsername() != null && account.getUsername().equals(username)) {
                return account;
            }
        }

        return null;
    }

}
