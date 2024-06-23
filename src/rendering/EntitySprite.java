package rendering;

import entities.Entity;

public final class EntitySprite {
    public static final String EMPTY_CELL_SPRITE = "🟫";
    public static String getSprite(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Herbivore" -> "🦕";
            case "Predator" -> "🦖";
            case "Grass" -> "🟩";
            case "Rock" -> "🗿";
            case "Tree" -> "🌳";
            default -> EMPTY_CELL_SPRITE;
        };

    }
    private EntitySprite() {}
}
