package actions;

import entities.Entity;
import entities.creatures.Creature;
import world_map.WorldMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class MakeMoves extends Action {
    @Override
    public void perform(WorldMap worldMap) {
        Collection<Entity> entities = new CopyOnWriteArrayList<>(worldMap.getEntities());

        for (Entity entity : entities) {
            if (entity instanceof Creature creature) {
                if (creature.isDead()) {
                    creature.getErased(worldMap);
                    continue;
                }

                creature.makeMove(worldMap);
            }
        }
    }
}
