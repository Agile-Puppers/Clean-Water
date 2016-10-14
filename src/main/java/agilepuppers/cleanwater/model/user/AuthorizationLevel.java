package agilepuppers.cleanwater.model.user;

public enum AuthorizationLevel {

    USER(1),
    WORKER(2),
    MANAGER(3),
    ADMIN(4);

    private int rank;

    private AuthorizationLevel(int permissionLevel) {
        this.rank = permissionLevel;
    }

    /**
     * Check if the AuthorizationLevel has equal or greater permissions than the given level
     * @param level the AuthorizationLevel to compare to
     * @return true if this AuthorizationLevel is greater than or equal to the given level
     */
    public boolean isAtLeast(AuthorizationLevel level) {
        return level.rank <= this.rank;
    }

}
