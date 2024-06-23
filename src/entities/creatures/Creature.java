package entities.creatures;

import entities.Entity;
import exceptions.EntityNotFoundException;
import path_finding.BfsPathFinder;
import path_finding.PathFinder;
import path_finding.SearchResult;
import world_map.Coordinates;
import world_map.WorldMap;

public abstract class Creature extends Entity {
    public static final int CREATURE_DEFAULT_HP = 5;
    public static final int CREATURE_DEFAULT_MOVE_SPEED = 1;
    private int hp = CREATURE_DEFAULT_HP;
    private int moveSpeed = CREATURE_DEFAULT_MOVE_SPEED;
    private final PathFinder pathFinder;

    public Creature() {
        pathFinder = new BfsPathFinder(CREATURE_DEFAULT_MOVE_SPEED, getTargetClassName());
    }

    public Creature(int hp, int moveSpeed, Coordinates coordinates) {
        this.coordinates = coordinates;
        this.hp = hp;
        this.moveSpeed = moveSpeed;

        pathFinder = new BfsPathFinder(moveSpeed, getTargetClassName());
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    protected boolean isDead() {
        return hp <= 0;
    }

    protected boolean isTarget(Entity entity) {
        return entity.getClass().getSimpleName().equals(getTargetClassName());
    }

    protected abstract String getTargetClassName();

    protected void moveTowardsOrInteractWithTarget(WorldMap worldMap) {
        SearchResult searchResult = pathFinder.searchForTarget(worldMap, coordinates);

        if (searchResult.isTargetNotFoundOrUnreachable()) {
            return;
        }

        Coordinates resultCoordinates = searchResult.getFirstCoordinates();
        if (searchResult.isTargetOneCellAway()) {
            try {
                Entity entity = worldMap.getEntity(resultCoordinates);

                if (!isTarget(entity)) {
                    throw new IllegalArgumentException("Wrong target");
                }

                interactWithTarget(entity, worldMap);
            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            moveTo(worldMap, resultCoordinates);
        }
    }

    protected abstract void interactWithTarget(Entity target, WorldMap worldMap);

    private void moveTo(WorldMap worldMap, Coordinates coordinates) {
        if (worldMap.isCorrectCoordinates((coordinates))) {
            worldMap.removeEntity(this.coordinates);
            worldMap.setEntity(coordinates, this);
            this.coordinates = coordinates;

            return;
        }

        throw new IndexOutOfBoundsException("Coordinates are out of bound");
    }

    public abstract void makeMove(WorldMap worldMap);
}
