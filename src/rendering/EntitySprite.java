package rendering;

import entities.Entity;

public interface EntitySprite {
    public String getSprite(Entity entity);
    public String getEmptyCellSprite();
}
