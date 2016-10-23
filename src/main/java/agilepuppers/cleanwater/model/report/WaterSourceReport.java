package agilepuppers.cleanwater.model.report;

import agilepuppers.cleanwater.model.HashMapConvertible;
import agilepuppers.cleanwater.model.user.UserAccount;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

public class WaterSourceReport extends Report {

    private WaterType waterType;
    private WaterCondition waterCondition;

    /**
     * Create a new Report
     *
     * @param ID                A unique numerical identifier for the report
     * @param author            The UserAccount that created the report. Must not be null.
     * @param lat               The latitude of the report
     * @param lon               The longitude of the report
     * @param waterType         The type of water
     * @param waterCondition    The condition of the water
     */
    public WaterSourceReport(int ID, UserAccount author, double lat, double lon, WaterType waterType, WaterCondition waterCondition) {
        super(ID, author, lat, lon);

        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }


    //getters

    public WaterType getWaterType() {
        return waterType;
    }

    public WaterCondition getWaterCondition() {
        return waterCondition;
    }


    //serialization support

    public static final String TYPE_KEY = "type";
    public static final String CONDITION_KEY = "condition";

    /**
     * Creates a HashMap that represents this report
     *
     * @return a HashMap representation of this object
     */
    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = super.toHashMap();

        hashMap.put(TYPE_KEY, this.waterType.toString());
        hashMap.put(CONDITION_KEY, this.waterCondition.toString());

        return hashMap;
    }

    /**
     * Creates a report from a HashMap
     *
     * @param hashMap the HashMap that represents the object
     */
    public WaterSourceReport(HashMap<String, String> hashMap) {
        super(hashMap);

        this.waterType = WaterType.valueOf(hashMap.get(TYPE_KEY));
        this.waterCondition = WaterCondition.valueOf(hashMap.get(CONDITION_KEY));
    }

    public static Factory<WaterSourceReport> factory = new Factory<WaterSourceReport>() {
        @Override
        public WaterSourceReport fromHashMap(HashMap<String, String> hashMap) {
            return new WaterSourceReport(hashMap);
        }
    };

}
