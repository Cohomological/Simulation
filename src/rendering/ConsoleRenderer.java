package rendering;

import exceptions.EntityNotFoundException;
import world_map.*;

import static rendering.EntitySprite.EMPTY_CELL_SPRITE;

public class ConsoleRenderer extends Renderer {

    @Override
    public void render(WorldMap map) {
        MapInfo mapInfo = map.getMapInfo();
        int height = mapInfo.height();
        int width = mapInfo.width();

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < width; x++) {
                String sprite = EMPTY_CELL_SPRITE;
                Coordinates coordinates = new Coordinates(x, y);
                if (map.isOccupied(coordinates)) {
                    try {
                        sprite = EntitySprite.getSprite(map.getEntity(coordinates));
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
