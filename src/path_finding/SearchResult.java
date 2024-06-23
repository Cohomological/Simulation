package path_finding;

import world_map.Coordinates;

public class SearchResult {
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
