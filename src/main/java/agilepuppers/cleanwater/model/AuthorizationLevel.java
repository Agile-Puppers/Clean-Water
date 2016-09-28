package agilepuppers.cleanwater.model;

/**
 * Created by cal on 9/27/16.
 */
public enum AuthorizationLevel {

    USER(1),
    WORKER(2),
    MANAGER(3),
    ADMIN(4);

    private int permissionLevel;

    private AuthorizationLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public boolean isAtLeast(AuthorizationLevel level) {
        return level.permissionLevel <= this.permissionLevel;
    }

}
