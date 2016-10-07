package agilepuppers.cleanwater.model;

import java.util.HashMap;

public interface HashMapConvertible {

    /**
     * Builds a HashMap representation of the object
     *
     * @return the object represented as a Hash Map
     */
    HashMap<String, String> toHashMap();

}
