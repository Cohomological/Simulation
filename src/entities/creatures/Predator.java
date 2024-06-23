package entities.creatures;

import entities.Entity;
import world_map.Coordinates;
import world_map.WorldMap;

public class Predator extends Creature {
    public static final int PREDATOR_DEFAULT_ATTACK_DAMAGE = 2;
    private int attackDamage = PREDATOR_DEFAULT_ATTACK_DAMAGE;

    public Predator(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    private void attack(Creature target) {
        target.setHp(target.getHp() - attackDamage);
    }

    public Predator(int hp, int moveSpeed, Coordinates coordinates, int attackDamage) {
        super(hp, moveSpeed, coordinates);
        this.attackDamage = attackDamage;
    }

    @Override
    protected void interactWithTarget(Entity target, WorldMap worldMap) {
        Creature creatureTarget = (Creature) target;

        attack(creatureTarget);
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        moveTowardsOrInteractWithTarget(worldMap);
    }
    @Override
    protected String getTargetClassName() {
        return "Herbivore";
    }
}
