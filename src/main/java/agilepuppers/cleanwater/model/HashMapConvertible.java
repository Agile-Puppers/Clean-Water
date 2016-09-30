package agilepuppers.cleanwater.model;

import java.util.HashMap;

public abstract class HashMapConvertible {


    /**
     * Builds an instance of the object from a HashMap.
     * Should be implemented in any class that extends HashMapConvertible.
     *
     * @param source a HashMap to build the object from
     */
    public HashMapConvertible(HashMap<String, String> source) {
        //override this
    }


    /**
     * Empty constructor to allow for instantiation paths that don't use HashMaps
     */
    public HashMapConvertible() { }


    /**
     * Builds a HashMap representation of the object
     *
     * @return the object represented as a Hash Map
     */
    public abstract HashMap<String, String> convertToHashMap();


    /**
     * Converts the result of convertToHashMap() to a comma-separated list of key-value pairs
     *
     * @return a string representation of the HashMap representation
     */
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
