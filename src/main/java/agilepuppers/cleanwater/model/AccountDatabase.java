package agilepuppers.cleanwater.model;

import agilepuppers.cleanwater.App;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class AccountDatabase {

    // non-instantiable class. (static methods only)
    private AccountDatabase() {}

    private static File databaseFile;

    // id, username, password
    private static final int userInfoSize = 4;

    public static void setFile(String path) {
        databaseFile = new File(path);
    }

    private static void createFile() {
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                App.current.error(App.FATAL, "Could not create user account database");
            }
        }
    }

    private static String[][] loadData() {
        createFile();
        try {
            List<String> lines = Files.readAllLines(databaseFile.toPath());
            String[][] data = new String[lines.size()][userInfoSize];
            for (int i = 0; i < lines.size(); ++i) {
                String[] line = lines.get(i).split(",");
                for (int j = 0; j < userInfoSize && j < line.length; ++j) {
                    data[i][j] = line[j];
                }
            }
            return data;
        } catch (IOException e) {
            App.current.error(App.FATAL, "Could not load user account data");
            return null;
        }
    }

    public static boolean addAccount(UserAccount account) {
        if (getUserAccount(account.getUsername()) != null) {
            return false;
        }
        String entry = account.getID() + "," + account.getUsername() + "," + account.getPassword() + "," + account.getAuthorization().toString();
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(databaseFile.getPath(), true)));
            out.println(entry);
            out.close();
        } catch (IOException e) {
            App.current.error(App.RECOVERABLE, "Could not access user account database");
        }
        return true;
    }

    public static UserAccount getUserAccount(String username) {
        String[][] data = loadData();
        for (String[] user : data) {
            if (user.length >= userInfoSize && user[1].equals(username)) {
                return new UserAccount(Integer.parseInt(user[0]), user[1], user[2], AuthorizationLevel.valueOf(user[3]), new UserProfile());
            }
        }
        return null;
    }

}
