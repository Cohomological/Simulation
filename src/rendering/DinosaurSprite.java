package rendering;

import entities.Entity;

public class DinosaurSprite implements EntitySprite {
    private final String EMPTY_CELL_SPRITE = "🟫";
    public String getSprite(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Herbivore" -> "🦕";
            case "Predator" -> "🦖";
            case "Grass" -> "🟩";
            case "Rock" -> "🗿";
            case "Tree" -> "🌳";
            default -> throw new IllegalArgumentException("Unknown sprite");
        };
    }

    @Override
    public String getEmptyCellSprite() {
        return EMPTY_CELL_SPRITE;
    }
    public DinosaurSprite() {}
}
