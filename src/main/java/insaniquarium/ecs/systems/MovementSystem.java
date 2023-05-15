package insaniquarium.ecs.systems;

import insaniquarium.Main;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.BoundingCollisionComponent;
import insaniquarium.ecs.components.MovementComponent;
import insaniquarium.ecs.components.TargetComponent;

import java.util.List;

public class MovementSystem extends System {

    public MovementSystem() {
        super();
    }

    @Override
    public void update(double delta) {
        List<Entity> entities = EntityManager.getInstance().getEntities();
        if (entities != null) {
            for (Entity entity : entities) {
                MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
                TargetComponent targetComponent = entity.getComponent(TargetComponent.class);
                if(movementComponent != null && targetComponent != null){
                    movementComponent.x += movementComponent.vx * delta;
                    movementComponent.y += movementComponent.vy * delta;
                    movementComponent.vx += movementComponent.ax * delta;
                    movementComponent.ay += movementComponent.ay * delta;

                    int radius = 0;
                    BoundingCollisionComponent boundingCollisionComponent = entity.getComponent(BoundingCollisionComponent.class);
                    if (boundingCollisionComponent != null) {
                        radius = boundingCollisionComponent.boundingCollisionRadius;
                    }

                    int minWidth = Main.SIDE_OFFSET + radius;
                    int maxWidth = Main.WIDTH - Main.SIDE_OFFSET - radius;
                    int maxHeight = Main.HEIGHT - radius - Main.GROUND_OFFSET_HEIGHT;
                    int minHeight = Main.MENU_OFFSET + radius;

                    if(movementComponent.x <= minWidth){
                        movementComponent.x = minWidth;
                    }
                    if(movementComponent.x >= maxWidth){
                        movementComponent.x = maxWidth;
                    }
                    if(movementComponent.y <= minHeight){
                        movementComponent.y = minHeight;
                    }
                    if(movementComponent.y >= maxHeight){
                        movementComponent.y = maxHeight;
                    }
                }
            }
        }

    }

}
