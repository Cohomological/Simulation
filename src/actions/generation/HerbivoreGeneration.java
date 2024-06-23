package actions.generation;

import entities.creatures.Herbivore;
import entities.Entity;
import world_map.Coordinates;

import static entities.creatures.Creature.CREATURE_DEFAULT_HP;
import static entities.creatures.Creature.CREATURE_DEFAULT_MOVE_SPEED;

public class HerbivoreGeneration extends EntityGeneration {
    private int hp = CREATURE_DEFAULT_HP;
    private int moveSpeed = CREATURE_DEFAULT_MOVE_SPEED;

    public HerbivoreGeneration(double percentage, int hp, int moveSpeed) {
        super(percentage);
        this.hp = hp;
        this.moveSpeed = moveSpeed;
    }

    @Override
    protected Entity createEntity(Coordinates coordinates) {
        return new Herbivore(hp, moveSpeed, coordinates);
    }

    public HerbivoreGeneration(double percentage) {
        super(percentage);
    }

    @Override
    protected String getClassName() {
        return "Herbivore";
    }
}
