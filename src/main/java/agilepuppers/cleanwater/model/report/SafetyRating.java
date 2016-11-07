package agilepuppers.cleanwater.model.report;

public enum SafetyRating {

    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String friendlyString;

    SafetyRating(String friendly) {
        this.friendlyString = friendly;
    }

    public String getFriendlyString() {
        return friendlyString;
    }

}
