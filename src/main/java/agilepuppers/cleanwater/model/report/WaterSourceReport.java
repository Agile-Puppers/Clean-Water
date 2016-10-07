package agilepuppers.cleanwater.model.report;

import agilepuppers.cleanwater.model.HashMapConvertible;
import agilepuppers.cleanwater.model.user.UserAccount;

import java.util.Date;
import java.util.HashMap;

public class WaterSourceReport implements HashMapConvertible {

    private Date timeCreated;
    private int reportId;

    private String authorName;
    private String authorUsername;

    private String location;
    private WaterType waterType;
    private WaterCondition waterCondition;

    /**
     * Create a new Water Source Report
     *
     * @param author The UserAccount that created the report. Must not be null.
     * @param location The reported location of the water.
     * @param waterType The type of the water.
     * @param waterCondition The condition of the water.
     */
    public WaterSourceReport(UserAccount author, String location, WaterType waterType, WaterCondition waterCondition) {
        this.timeCreated = new Date();
        this.reportId = (int) (Math.random() * 1000000000);

        this.authorUsername = author.getUsername();
        this.authorName = author.getProfile().getName();

        this.location = location;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }


    //getters

    public Date getTimeCreated() {
        return timeCreated;
    }

    public int getReportId() {
        return reportId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public String getReportedLocation() {
        return location;
    }

    public WaterType getWaterType() {
        return waterType;
    }

    public WaterCondition getWaterCondition() {
        return waterCondition;
    }


    //serialization support

    public static final String TIME_CREATED_KEY = "time";
    public static final String ID_KEY = "id";
    public static final String AUTHOR_NAME_KEY = "author";
    public static final String AUTHOR_USERNAME_KEY = "username";
    public static final String LOCATION_KEY = "location";
    public static final String TYPE_KEY = "type";
    public static final String CONDITION_KEY = "condition";

    /**
     * Creates a HashMap that represents this report
     * @return a HashMap representation of this object
     */
    @Override public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put(TIME_CREATED_KEY, this.timeCreated.toString());
        hashMap.put(ID_KEY, this.reportId + "");

        hashMap.put(AUTHOR_NAME_KEY, this.authorName);
        hashMap.put(AUTHOR_USERNAME_KEY, this.authorUsername);

        hashMap.put(LOCATION_KEY, this.location);
        hashMap.put(TYPE_KEY, this.waterType.toString());
        hashMap.put(CONDITION_KEY, this.waterCondition.toString());

        return hashMap;
    }

}