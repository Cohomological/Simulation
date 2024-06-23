package path_finding;

import world_map.Coordinates;
import world_map.WorldMap;

public abstract class PathFinder {
    protected final int moveSpeed;
    protected final String targetName;

    public PathFinder(int moveSpeed, String targetName) {
        this.moveSpeed = moveSpeed;
        this.targetName = targetName;
    }

    public abstract SearchResult searchForTarget(WorldMap worldMap, Coordinates currentCoordinates);
}
