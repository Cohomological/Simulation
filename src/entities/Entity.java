package entities;

import world_map.Coordinates;
import world_map.WorldMap;

public abstract class Entity {
    public Coordinates coordinates;

    public void getErased(WorldMap worldMap) {
        worldMap.removeEntity(coordinates);
    }
}
