package entities;

import exceptions.EntityNotFoundException;
import world_map.Coordinates;
import world_map.WorldMap;

public abstract class Entity {
    public Coordinates coordinates;

    public void getErased(WorldMap worldMap) {
        try {
            worldMap.removeEntity(coordinates);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
