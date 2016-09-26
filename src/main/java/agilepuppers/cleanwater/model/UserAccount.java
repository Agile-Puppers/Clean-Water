package agilepuppers.cleanwater.model;

public class UserAccount {

    private final int ID;
    private final String USERNAME;
    private final String PASSWORD;

    private final UserProfile profile;

    public UserAccount(int id, String username, String password, UserProfile profile) {
        this.ID = id;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.profile = profile;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public UserProfile getProfile() {
        return profile;
    }

}
