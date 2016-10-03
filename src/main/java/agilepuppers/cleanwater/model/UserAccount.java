package agilepuppers.cleanwater.model;

import java.util.HashMap;

public class UserAccount extends HashMapConvertible {

    //serialization keys
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String AUTHORIZATION_KEY = "authlevel";

    //instance variables
    private final String USERNAME;
    private final String PASSWORD;
    private final AuthorizationLevel AUTHORIZATION;

    private UserProfile profile;
    
    /**
     * Constructor for a new User Account
     * @param username the account's username
     * @param password the account's password
     * @param authorization the account's authorization level
     * @param profile the account's profile
     */
    public UserAccount(String username, String password, AuthorizationLevel authorization, UserProfile profile) {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.AUTHORIZATION = authorization;
        this.profile = profile;
    }

    /**
     * Getter for the account's username
     * @return the account's username
     */
    public String getUsername() {
        return USERNAME;
    }

    /**
     * Getter for the account's password
     * @return the account's password
     */
    public String getPassword() {
        return PASSWORD;
    }

    /**
     * Getter for the account's authorization level
     * @return the account's authorization level
     */
    public AuthorizationLevel getAuthorization() {
        return AUTHORIZATION;
    }
    
    /**
     * Getter for the account's profile
     * @return the account's profile
     */
    public UserProfile getProfile() {
        return profile;
    }


    /**
     * Builds a UserAccount from the given HashMap
     *
     * @param source the HashMap to build a UserAccount from
     */
    public UserAccount(HashMap<String, String> source) {
        super(source);

        this.USERNAME = source.get(USERNAME_KEY);
        this.PASSWORD = source.get(PASSWORD_KEY);

        String authString = source.get(AUTHORIZATION_KEY);
        this.AUTHORIZATION = AuthorizationLevel.valueOf(authString);

        this.profile = new UserProfile(source);
    }

    /**
     * Builds a HashMap that represents the object
     *
     * @return this object represented as a HashMap
     */
    @Override
    public HashMap<String, String> convertToHashMap() {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> profileMap = profile.convertToHashMap();
        map.put(USERNAME_KEY, USERNAME);
        map.put(PASSWORD_KEY, PASSWORD);
        map.put(AUTHORIZATION_KEY, AUTHORIZATION.toString());
        map.putAll(profileMap);

        return map;
    }

}
