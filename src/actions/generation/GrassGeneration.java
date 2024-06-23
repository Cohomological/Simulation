package actions.generation;

import entities.Entity;
import entities.static_objects.Grass;
import world_map.Coordinates;

public class GrassGeneration extends EntityGeneration {
    public GrassGeneration(double percentage) {
        super(percentage);
    }


    @Override
    protected Entity createEntity(Coordinates coordinates) {
        return new Grass(coordinates);
    }

    @Override
    protected String getClassName() {
        return "Grass";
    }
}
