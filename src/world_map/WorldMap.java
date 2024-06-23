package world_map;

import entities.Entity;
import exceptions.EntityNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class WorldMap {

    private static final int DEFAULT_WIDTH = 20;
    private static final int DEFAULT_HEIGHT = 20;
    private final int width;
    private final int height;
    private final HashMap<Coordinates, Entity> worldMap = new HashMap<>();

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public WorldMap() {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    public boolean isCorrectCoordinates(Coordinates coordinates) {
        int x = coordinates.x;
        int y = coordinates.y;

        boolean isXCorrect = x >= 0 && x <= width;
        boolean isYCorrect = y >= 0 && y <= width;

        return isXCorrect && isYCorrect;
    }
    public void setEntity(Coordinates coordinates, Entity entity) {
        worldMap.put(coordinates, entity);
    }
    public boolean isOccupied(Coordinates coordinates) {
        return worldMap.containsKey(coordinates);
    }
    public Entity getEntity(Coordinates coordinates) throws EntityNotFoundException{
        if (!isOccupied(coordinates)) {
            throw new EntityNotFoundException("No entity found at given coordinates.");
        }

        return worldMap.get(coordinates);
    }

    public void removeEntity(Coordinates coordinates) {
        if (isOccupied(coordinates)) {
            worldMap.remove(coordinates);
        }
    }
    public MapInfo getMapInfo() {
        return new MapInfo(width, height);
    }

    public Collection<Entity> getEntities() {
        // Violates encapsulation? Is there a better way - question for the review
        return worldMap.values();
    }

    public Set<Coordinates> getOccupiedCells() {
        return worldMap.keySet();
    }
}
