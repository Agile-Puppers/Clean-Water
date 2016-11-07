package agilepuppers.cleanwater.model.report;

import agilepuppers.cleanwater.model.user.UserAccount;

import java.util.HashMap;

public class WaterPurityReport extends Report {

    private final SafetyRating safetyRating;
    private double contaminantPPM;
    private double virusPPM;

    /**
     * Create a new Report
     *
     * @param ID                A unique numerical identifier for the report
     * @param author            The UserAccount that created the report. Must not be null.
     * @param lat               The latitude of the report
     * @param lon               The longitude of the report
     * @param safetyRating      A rating on how safe the water is
     * @param contaminantPPM    A count of how many contaminants there are per million particles
     * @param virusPPM          A count of how many viruses there are per million particles
     */
    public WaterPurityReport(int ID, UserAccount author, double lat, double lon, SafetyRating safetyRating, double contaminantPPM, double virusPPM) {
        super(ID, author, lat, lon);

        this.safetyRating = safetyRating;
        this.contaminantPPM = contaminantPPM;
        this.virusPPM = virusPPM;
    }


    //getters

    public SafetyRating getSafetyRating() {
        return this.safetyRating;
    }

    public double getContaminantPPM() {
        return this.contaminantPPM;
    }

    public double getVirusPPM() {
        return this.virusPPM;
    }


    //serialization support

    public static final String SAFETY_KEY = "safety";
    public static final String CONTAMINANT_KEY = "contaminants";
    public static final String VIRUS_KEY = "viruses";

    /**
     * Creates a HashMap that represents this report
     *
     * @return a HashMap representation of this object
     */
    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = super.toHashMap();

        hashMap.put(SAFETY_KEY, this.getSafetyRating().toString());
        hashMap.put(CONTAMINANT_KEY, String.valueOf(this.contaminantPPM));
        hashMap.put(VIRUS_KEY, String.valueOf(this.virusPPM));

        return hashMap;
    }

    /**
     * Creates a report from a HashMap
     *
     * @param hashMap the HashMap that represents the object
     */
    public WaterPurityReport(HashMap<String, String> hashMap) {
        super(hashMap);

        this.safetyRating = SafetyRating.valueOf(hashMap.get(SAFETY_KEY));

        String contaminantString = hashMap.get(CONTAMINANT_KEY);
        try {
            this.contaminantPPM = Double.valueOf(contaminantString);
        } catch (NumberFormatException e) {
            this.contaminantPPM = 0;
        }

        String virusString = hashMap.get(VIRUS_KEY);
        try {
            this.virusPPM = Double.valueOf(virusString);
        } catch (NumberFormatException e) {
            this.virusPPM = 0;
        }
    }

    public static final Factory<WaterPurityReport> factory = new Factory<WaterPurityReport>() {
        @Override
        public WaterPurityReport fromHashMap(HashMap<String, String> hashMap) {
            return new WaterPurityReport(hashMap);
        }
    };

}
