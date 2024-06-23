package world_map;

public class Coordinates {
    public final int x;
    public final int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (y != that.y) return false;
        return x == that.x;
    }

    @Override
    public int hashCode() {
        int result = y;
        result = 31 * result + x;
        return result;
    }

    public Coordinates(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public Coordinates shift(int shiftX, int shiftY) {
        return new Coordinates(this.x + shiftX, this.y + shiftY);
    }
}
