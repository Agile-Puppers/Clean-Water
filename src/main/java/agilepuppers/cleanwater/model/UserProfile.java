package agilepuppers.cleanwater.model;

import java.util.HashMap;

public class UserProfile {

    private final String TITLE_KEY = "title";
    private final String NAME_KEY = "fullname";
    private final String EMAIL_KEY = "emailaddress";
    private final String ADDRESS_KEY = "homeaddress";

    //instance variables
    private String TITLE;
    private String NAME;
    private String EMAIL;
    private String ADDRESS;

    public UserProfile() {
        this.TITLE = "";
        this.NAME = "";
        this.EMAIL = "";
        this.ADDRESS = "";
    }

    public UserProfile(String title, String name, String email, String address) {
        this.TITLE = title;
        this.NAME = name;
        this.EMAIL = email;
        this.ADDRESS = address;
    }

    public void setTitle(String title) { this.TITLE = title; }
    public void setName(String name) { this.NAME = name; }
    public void setEmail(String email) { this.EMAIL = email; }
    public void setAddress(String address) { this.ADDRESS = address; }

    public String getTitle() { return TITLE; }
    public String getName() { return NAME; }
    public String getEmail() { return EMAIL; }
    public String getAddress() { return ADDRESS; }


    //serialization methods

    public HashMap<String, String> convertToHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(TITLE_KEY, TITLE);
        map.put(NAME_KEY, NAME);
        map.put(EMAIL_KEY, EMAIL);
        map.put(ADDRESS_KEY, ADDRESS);

        return map;
    }

    public UserProfile(HashMap<String, String> hashMap) {
        this.TITLE = hashMap.get(TITLE_KEY);
        this.NAME = hashMap.get(NAME_KEY);
        this.EMAIL = hashMap.get(EMAIL_KEY);
        this.ADDRESS = hashMap.get(ADDRESS_KEY);
    }

}
