package rendering;

import exceptions.EntityNotFoundException;
import world_map.*;

public class ConsoleRenderer implements Renderer {
    EntitySprite sprite;

    public ConsoleRenderer() {
        sprite = new DinosaurSprite();
    }

    public ConsoleRenderer(EntitySprite sprite) {
        this.sprite = sprite;
    }
    @Override
    public void render(WorldMap map) {
        MapInfo mapInfo = map.getMapInfo();
        int height = mapInfo.height();
        int width = mapInfo.width();

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < width; x++) {
                String sprite = this.sprite.getEmptyCellSprite();
                Coordinates coordinates = new Coordinates(x, y);
                if (map.isOccupied(coordinates)) {
                    try {
                        sprite = this.sprite.getSprite(map.getEntity(coordinates));
                    } catch (EntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

                stringBuilder.append(sprite);
                stringBuilder.append(' ');
            }

            System.out.println(stringBuilder);
        }
    }
}
