package actions.generation;

import entities.Entity;
import entities.static_objects.Tree;
import world_map.Coordinates;

public class TreeGeneration extends EntityGeneration {
    public TreeGeneration(double percentage) {
        super(percentage);
    }

    public TreeGeneration() {
    }

    @Override
    protected Entity createEntity(Coordinates coordinates) {
        return new Tree(coordinates);
    }

    @Override
    protected String getClassName() {
        return "Tree";
    }
}
