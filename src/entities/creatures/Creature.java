package entities.creatures;

import entities.Entity;
import exceptions.EntityNotFoundException;
import world_map.Coordinates;
import world_map.WorldMap;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Creature extends Entity {
    public static final int CREATURE_DEFAULT_HP = 5;
    public static final int CREATURE_DEFAULT_MOVE_SPEED = 1;
    private int hp = CREATURE_DEFAULT_HP;
    private int moveSpeed = CREATURE_DEFAULT_MOVE_SPEED;

    public Creature() {
    }

    public Creature(int hp, int moveSpeed, Coordinates coordinates) {
        this.coordinates = coordinates;
        this.hp = hp;
        this.moveSpeed = moveSpeed;
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

    protected SearchResult searchForTarget(WorldMap worldMap) {
        String target = getTargetClassName();
        HashSet<Coordinates> visitedCells = new HashSet<>();
        Queue<SearchResult> bfsQueue = new LinkedList<>();

        visitedCells.add(this.coordinates);

        HashSet<Coordinates> shiftedCoordinates = getShiftedCoordinates(this.coordinates);

        List<Coordinates> coordinatesToAddToVisitedCells = shiftedCoordinates.stream()
                .filter(coordinates -> worldMap.isCorrectCoordinates(coordinates) && !visitedCells.contains(coordinates))
                .toList();
        List<SearchResult> pairsToInsertToQueue = coordinatesToAddToVisitedCells.stream()
                .map(SearchResult::new)
                .toList();

        bfsQueue.addAll(pairsToInsertToQueue);
        visitedCells.addAll(coordinatesToAddToVisitedCells);

        while (!bfsQueue.isEmpty()) {
            SearchResult currentPair = bfsQueue.poll();
            Coordinates nextCoordinates = currentPair.getNextCoordinates();

            try {
                if (worldMap.isOccupied(nextCoordinates)) {
                    Entity entityAtNextCoordinates = worldMap.getEntity(nextCoordinates);

                    if (isTarget(entityAtNextCoordinates)) {
                        return currentPair;
                    }

                    continue;
                }
            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
            }

            shiftedCoordinates = getShiftedCoordinates(nextCoordinates);

            coordinatesToAddToVisitedCells = shiftedCoordinates.stream()
                    .filter(coordinates -> worldMap.isCorrectCoordinates(coordinates)
                            && !visitedCells.contains(coordinates))
                    .toList();
            pairsToInsertToQueue = coordinatesToAddToVisitedCells.stream()
                    .map(coordinates -> new SearchResult(currentPair.getFirstCoordinates(), coordinates))
                    .toList();

            bfsQueue.addAll(pairsToInsertToQueue);
            visitedCells.addAll(coordinatesToAddToVisitedCells);
        }

        return SearchResult.targetNotFoundOrUnreachable();
    }

    protected boolean isTarget(Entity entity) {
        return entity.getClass().getSimpleName().equals(getTargetClassName());
    }

    protected abstract String getTargetClassName();

    private HashSet<Coordinates> getShiftedCoordinates(Coordinates coordinates) {
        HashSet<Coordinates> shiftedCoordinates = new HashSet<>();

        for (int shiftX = -moveSpeed; shiftX <= moveSpeed; shiftX++) {
            for (int shiftY = -moveSpeed; shiftY <= moveSpeed; shiftY++) {
                shiftedCoordinates.add(coordinates.shift(shiftX, shiftY));
            }
        }
        return shiftedCoordinates;
    }

    protected void moveTowardsOrInteractWithTarget(WorldMap worldMap) {
        SearchResult searchResult = searchForTarget(worldMap);

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

    protected static class SearchResult {
        private boolean isTargetNotFoundOrUnreachable = false;
        private final Coordinates firstCoordinates;
        private final Coordinates nextCoordinates;

        public boolean isTargetNotFoundOrUnreachable() {
            return isTargetNotFoundOrUnreachable;
        }

        public SearchResult(Coordinates firstCoordinates) {
            this.firstCoordinates = firstCoordinates;
            this.nextCoordinates = firstCoordinates;
        }

        public SearchResult(Coordinates firstCoordinates, Coordinates nextCoordinates) {
            this.firstCoordinates = firstCoordinates;
            this.nextCoordinates = nextCoordinates;
        }

        private SearchResult() {
            isTargetNotFoundOrUnreachable = true;

            this.nextCoordinates = new Coordinates(-1, -1);
            this.firstCoordinates = new Coordinates(-1, -1);
        }

        public static SearchResult targetNotFoundOrUnreachable() {
            return new SearchResult();
        }

        public Coordinates getNextCoordinates() {
            return nextCoordinates;
        }

        public Coordinates getFirstCoordinates() {
            return firstCoordinates;
        }

        public boolean isTargetOneCellAway() {
            return firstCoordinates.equals(nextCoordinates);
        }
    }
}
