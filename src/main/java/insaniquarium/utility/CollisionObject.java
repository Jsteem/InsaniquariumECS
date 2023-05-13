package insaniquarium.utility;

import insaniquarium.ecs.Entity;

public class CollisionObject {
    public Entity entity;
    public float x;
    public float y;
    public int radius;
    public CollisionObject(Entity entity, float x, float y, int radius){
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.radius = radius;

    }


}
