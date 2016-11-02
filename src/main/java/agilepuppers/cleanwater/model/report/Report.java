package agilepuppers.cleanwater.model.report;

import agilepuppers.cleanwater.model.HashMapConvertible;
import agilepuppers.cleanwater.model.user.UserAccount;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public abstract class Report implements HashMapConvertible {

    private Date timeCreated;
    private int reportId;

    private String authorName;
    private String authorUsername;

    private double lat;
    private double lon;

    /**
     * Create a new Report
     *
     * @param ID        A unique numerical identifier for the report
     * @param author    The UserAccount that created the report. Must not be null.
     * @param lat       The latitude of the report
     * @param lon       The longitude of the report
     */
    public Report(int ID, UserAccount author, double lat, double lon) {
        this.timeCreated = new Date();
        this.reportId = ID;

        this.authorUsername = author.getUsername();
        if (author.getProfile() != null) {
            this.authorName = author.getProfile().getName();
        }

        this.lat = lat;
        this.lon = lon;
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

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }

    public String getAuthorDisplayName() {
        if (this.authorName != null) {
            return this.authorName;
        } else {
            return this.authorUsername;
        }
    }


    //serialization support

    public static final String TIME_CREATED_KEY = "time";
    public static final String ID_KEY = "id";
    public static final String AUTHOR_NAME_KEY = "author";
    public static final String AUTHOR_USERNAME_KEY = "username";
    public static final String LAT_KEY = "lat";
    public static final String LON_KEY = "lon";

    /**
     * Creates a HashMap that represents this report
     *
     * @return a HashMap representation of this object
     */
    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put(TIME_CREATED_KEY, this.timeCreated.toString());
        hashMap.put(ID_KEY, String.valueOf(this.reportId));

        hashMap.put(AUTHOR_NAME_KEY, this.authorName);
        hashMap.put(AUTHOR_USERNAME_KEY, this.authorUsername);

        hashMap.put(LAT_KEY, String.valueOf(this.lat));
        hashMap.put(LON_KEY, String.valueOf(this.lon));

        return hashMap;
    }

    /**
     * Creates a report from a HashMap
     *
     * @param hashMap the HashMap that represents the object
     */
    public Report(HashMap<String, String> hashMap) {
        try {
            String timeCreatedString = hashMap.get(TIME_CREATED_KEY);
            String dateFormat = "EEE MMM d HH:mm:ss zzz yyyy";
            this.timeCreated = new SimpleDateFormat(dateFormat).parse(timeCreatedString);
        } catch (ParseException e) {
            this.timeCreated = new Date();
        }

        try {
            String idString = hashMap.get(ID_KEY);
            this.reportId = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            this.reportId = -1;
        }

        this.authorName = hashMap.get(AUTHOR_NAME_KEY);
        this.authorUsername = hashMap.get(AUTHOR_USERNAME_KEY);

        this.lat = Double.valueOf(hashMap.get(LAT_KEY));
        this.lon = Double.valueOf(hashMap.get(LON_KEY));
    }

}
