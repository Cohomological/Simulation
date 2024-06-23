package path_finding;

import entities.Entity;
import exceptions.EntityNotFoundException;
import world_map.Coordinates;
import world_map.WorldMap;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsPathFinder extends PathFinder {
    public BfsPathFinder(int moveSpeed, String targetName) {
        super(moveSpeed, targetName);
    }

    private HashSet<Coordinates> getShiftedCoordinates(Coordinates coordinates) {
        HashSet<Coordinates> shiftedCoordinates = new HashSet<>();

        for (int shiftX = -moveSpeed; shiftX <= moveSpeed; shiftX++) {
            for (int shiftY = -moveSpeed; shiftY <= moveSpeed; shiftY++) {
                shiftedCoordinates.add(coordinates.shift(shiftX, shiftY));
            }
        }
        return shiftedCoordinates;
    }

    @Override
    public SearchResult searchForTarget(WorldMap worldMap, Coordinates currentCoordinates) {
        HashSet<Coordinates> visitedCells = new HashSet<>();
        Queue<SearchResult> bfsQueue = new LinkedList<>();

        visitedCells.add(currentCoordinates);

        HashSet<Coordinates> shiftedCoordinates = getShiftedCoordinates(currentCoordinates);

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

    private boolean isTarget(Entity entity) {
        return entity.getClass().getSimpleName().equals(targetName);
    }
}
