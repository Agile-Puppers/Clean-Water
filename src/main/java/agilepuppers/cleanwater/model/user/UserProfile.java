package agilepuppers.cleanwater.model.user;

import java.util.HashMap;

public class UserProfile {

    //instance variables
    private String TITLE;
    private String NAME;
    private String EMAIL;
    private String ADDRESS;

    /**
     * Base constructor for creating a new User Profile.
     */
    public UserProfile() {
        this.TITLE = "";
        this.NAME = "";
        this.EMAIL = "";
        this.ADDRESS = "";
    }

    /**
     * Constructor for a new User Profile
     * @param title the users title
     * @param name the users name
     * @param email the users email address
     * @param address the users home address
     */
    public UserProfile(String title, String name, String email, String address) {
        this.TITLE = title;
        this.NAME = name;
        this.EMAIL = email;
        this.ADDRESS = address;
    }

    /**
     * Converts a user profile hash map into a new user profile
     * @param hashMap the hash map of the user profile instance variables
     */
    public UserProfile(HashMap<String, String> hashMap) {
        this.TITLE = hashMap.get(UserAccount.TITLE_KEY);
        this.NAME = hashMap.get(UserAccount.NAME_KEY);
        this.EMAIL = hashMap.get(UserAccount.EMAIL_KEY);
        this.ADDRESS = hashMap.get(UserAccount.ADDRESS_KEY);
    }

    /**
     * Getter for the users title
     * @return the users title
     */
    public String getTitle() { return TITLE; }

    /**
     * Getter for the users name
     * @return the users name
     */
    public String getName() { return NAME; }

    /**
     * Getter for the users email address
     * @return the users email address
     */
    public String getEmail() { return EMAIL; }

    /**
     * Getter for the users home address
     * @return the users home address
     */
    public String getAddress() { return ADDRESS; }

    /**
     * Setter for the users title
     * @param title the users title
     */
    public void setTitle(String title) { this.TITLE = title; }

    /**
     * Setter for the users name
     * @param name the users name
     */
    public void setName(String name) { this.NAME = name; }

    /**
     * Setter for the users email address
     * @param email the users email address
     */
    public void setEmail(String email) { this.EMAIL = email; }

    /**
     * Setter for the users home address
     * @param address the users home address
     */
    public void setAddress(String address) { this.ADDRESS = address; }

}
