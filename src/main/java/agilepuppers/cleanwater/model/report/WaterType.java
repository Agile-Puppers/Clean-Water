package agilepuppers.cleanwater.model.report;

public enum WaterType {

    BOTTLED("Bottled Water"),
    WELL("Well Water"),
    STREAM("Stream Water"),
    LAKE("Lake Water"),
    SPRING("Spring Water"),
    OTHER("Water");

    private String friendlyString;

    private WaterType(String friendly) {
        this.friendlyString = friendly;
    }

    public String getFriendlyString() {
        return friendlyString;
    }

}
