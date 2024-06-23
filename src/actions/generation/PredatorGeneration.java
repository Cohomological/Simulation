package actions.generation;

import entities.Entity;
import entities.creatures.Predator;
import world_map.Coordinates;

import static entities.creatures.Creature.CREATURE_DEFAULT_HP;
import static entities.creatures.Creature.CREATURE_DEFAULT_MOVE_SPEED;
import static entities.creatures.Predator.PREDATOR_DEFAULT_ATTACK_DAMAGE;

public class PredatorGeneration extends EntityGeneration {
    private int hp = CREATURE_DEFAULT_HP;
    private int moveSpeed = CREATURE_DEFAULT_MOVE_SPEED;
    private int attackDamage = PREDATOR_DEFAULT_ATTACK_DAMAGE;

    public PredatorGeneration(double percentage, int hp, int moveSpeed, int attackDamage) {
        super(percentage);
        this.hp = hp;
        this.moveSpeed = moveSpeed;
        this.attackDamage = attackDamage;
    }

    public PredatorGeneration(double percentage) {
        super(percentage);
    }

    @Override
    protected Entity createEntity(Coordinates coordinates) {
        return new Predator(hp, moveSpeed, coordinates, attackDamage);
    }

    @Override
    protected String getClassName() {
        return "Predator";
    }
}
