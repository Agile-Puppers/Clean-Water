package agilepuppers.cleanwater.model.report;

public enum WaterCondition {

    POTABLE("Potable"),
    WASTE("Waste"),
    TREATABLE_CLEAR("Treatable (Clear)"),
    TREATABLE_MUDDY("Treatable (Muddy)");

    private final String friendlyString;

    WaterCondition(String friendly) {
        this.friendlyString = friendly;
    }

    public String getFriendlyString() {
        return friendlyString;
    }

}
