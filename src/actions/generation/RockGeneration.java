package actions.generation;

import entities.Entity;
import entities.static_objects.Rock;
import world_map.Coordinates;

public class RockGeneration extends EntityGeneration {
    public RockGeneration(double percentage) {
        super(percentage);
    }

    public RockGeneration() {
    }

    @Override
    protected Entity createEntity(Coordinates coordinates) {
        return new Rock(coordinates);
    }

    @Override
    protected String getClassName() {
        return "Rock";
    }
}
