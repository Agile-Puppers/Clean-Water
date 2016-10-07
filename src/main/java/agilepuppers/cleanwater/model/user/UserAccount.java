package agilepuppers.cleanwater.model.user;

import agilepuppers.cleanwater.model.HashMapConvertible;

import java.util.HashMap;

public class UserAccount implements HashMapConvertible {

    //serialization keys
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String AUTHORIZATION_KEY = "authlevel";
    public static final String TITLE_KEY = "title";
    public static final String NAME_KEY = "fullname";
    public static final String EMAIL_KEY = "emailaddress";
    public static final String ADDRESS_KEY = "homeaddress";

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
     * @param hashmap the HashMap to build a UserAccount from
     */
    public UserAccount(HashMap<String, String> hashmap) {
        this.USERNAME = hashmap.get(USERNAME_KEY);
        this.PASSWORD = hashmap.get(PASSWORD_KEY);

        String authString = hashmap.get(AUTHORIZATION_KEY);
        this.AUTHORIZATION = AuthorizationLevel.valueOf(authString);

        this.profile = new UserProfile(hashmap);
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = new HashMap<>();

        map.put(USERNAME_KEY, USERNAME);
        map.put(PASSWORD_KEY, PASSWORD);
        map.put(AUTHORIZATION_KEY, AUTHORIZATION.toString());
        map.put(TITLE_KEY, profile.getTitle());
        map.put(NAME_KEY, profile.getName());
        map.put(EMAIL_KEY, profile.getEmail());
        map.put(ADDRESS_KEY, profile.getAddress());

        return map;
    }

}
