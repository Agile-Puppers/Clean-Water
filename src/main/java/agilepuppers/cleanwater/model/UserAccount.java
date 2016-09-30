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

    private final UserProfile profile;

    public UserAccount(String username, String password, AuthorizationLevel authorization, UserProfile profile) {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.AUTHORIZATION = authorization;
        this.profile = profile;
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public AuthorizationLevel getAuthorization() {
        return AUTHORIZATION;
    }

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

        this.profile = null; //no serialization support for Profile yet
    }

    /**
     * Builds a HashMap that represents the object
     *
     * @return this object represented as a HashMap
     */
    @Override
    public HashMap<String, String> convertToHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(USERNAME_KEY, USERNAME);
        map.put(PASSWORD_KEY, PASSWORD);
        map.put(AUTHORIZATION_KEY, AUTHORIZATION.toString());

        return map;
    }

}
