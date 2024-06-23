package entities.creatures;

import entities.Entity;
import world_map.Coordinates;
import world_map.WorldMap;

public class Herbivore extends Creature {
    public Herbivore(int hp, int moveSpeed, Coordinates coordinates) {
        super(hp, moveSpeed, coordinates);
    }

    public Herbivore(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    protected String getTargetClassName() {
        return "Grass";
    }

    @Override
    protected void interactWithTarget(Entity target, WorldMap worldMap) {
        target.getErased(worldMap);
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        moveTowardsOrInteractWithTarget(worldMap);
    }
}
