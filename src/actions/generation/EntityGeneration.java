package actions.generation;

import actions.Action;
import entities.Entity;
import world_map.Coordinates;
import world_map.MapInfo;
import world_map.WorldMap;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

public abstract class EntityGeneration extends Action {
    private double percentage = 0.2;
    private int area;
    private MapInfo mapInfo;
    private int counter = 0;
     public EntityGeneration(double percentage)  {
        this.percentage = percentage;
    }

    public EntityGeneration() {
    }

    @Override
    public void perform(WorldMap worldMap) {
        mapInfo = worldMap.getMapInfo();
        area = mapInfo.width() * mapInfo.height();

        if (isLowerThanPercentage(worldMap)) {
            spawn(worldMap);
        }

        counter = 0;
    }

    private void spawn(WorldMap worldMap) {
        long deficit = Math.round(area * percentage) - counter;
        if (deficit <= 0) {
            return;
        }

        Set<Coordinates> occupiedCells = worldMap.getOccupiedCells();
        int width = mapInfo.width();
        int height = mapInfo.height();

        while (deficit > 0) {
            Random random = new Random();
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Coordinates coordinates = new Coordinates(x, y);

            if (!occupiedCells.contains(coordinates)) {
                worldMap.setEntity(coordinates, createEntity(coordinates));
                deficit--;
            }
        }
    }

    protected abstract Entity createEntity(Coordinates coordinates);

    private boolean isLowerThanPercentage(WorldMap worldMap) {
        Collection<Entity> entities = worldMap.getEntities();

        for (Entity entity : entities) {
            if (entity.getClass().getSimpleName().equals(getClassName())) {
                counter += 1;
            }
        }
        return ((double)counter / area) - percentage < 0;
    }

    protected abstract String getClassName();

}
