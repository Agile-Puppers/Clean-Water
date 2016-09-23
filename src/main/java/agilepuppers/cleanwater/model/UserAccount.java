package agilepuppers.cleanwater.model;

import agilepuppers.cleanwater.App;

public class UserAccount {

    // this is prolly not secure at all but it'll work for now i think
    private final String USERNAME;
    private final String PASSWORD;

    public UserAccount(String username, String password) {
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    public boolean credentialsAreValid() {
        return USERNAME.equals("user") && PASSWORD.equals("pass");
    }

}
