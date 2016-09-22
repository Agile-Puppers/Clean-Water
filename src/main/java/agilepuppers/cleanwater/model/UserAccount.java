package agilepuppers.cleanwater.model;

public class UserAccount {

    // this is prolly not secure at all but it'll work for now i think
    private final String USERNAME;
    private final String PASSWORD;

    public UserAccount(String un, String pw) {
        this.USERNAME = un;
        this.PASSWORD = pw;
    }

    private boolean validate() {
        // TODO
        return false;
    }

    public boolean login() {
        if (validate()) {
            // etc.
            return true;
        }
        // etc.
        return false;
    }

}
