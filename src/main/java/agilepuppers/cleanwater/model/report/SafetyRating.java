package agilepuppers.cleanwater.model.report;

/**
 * Created by cal on 10/23/16.
 */
public enum SafetyRating {

    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private String friendlyString;

    private SafetyRating(String friendly) {
        this.friendlyString = friendly;
    }

    public String getFriendlyString() {
        return friendlyString;
    }

}
