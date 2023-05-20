package insaniquarium.utility;

import insaniquarium.ecs.Entity;

public class CollisionObject {
    public Entity entity;
    public float x;
    public float y;
    public int width;
    public int height;
    public CollisionObject(Entity entity, float x, float y, int width, int height){
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }


}
