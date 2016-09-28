package agilepuppers.cleanwater.model;

import java.util.HashMap;

public abstract class HashMapConvertible {

    //builds an instance of the object from a HashMap
    public HashMapConvertible(HashMap<String, String> source) {
        //override this
    }

    public HashMapConvertible() { }

    public abstract HashMap<String, String> convertToHashMap();

    //converts the result of convertToHashMap() to a comma-separated list of key-value pairs
    public String serialize() {
        StringBuilder builder = new StringBuilder();

        HashMap<String, String> map = this.convertToHashMap();
        for (String key : map.keySet()) {
            String value = map.get(key);

            builder.append(key + "=" + value + ",");
        }

        //create a string and trim the trailing comma
        String output = builder.toString().substring(0, builder.length() - 1);
        return output;
    }

}
