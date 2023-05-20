package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.Main;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.BoundingRadiusComponent;
import insaniquarium.ecs.components.FallSpeedComponent;
import insaniquarium.ecs.components.MovementComponent;

public class FallBehavior extends BehaviorTypeComponent {
    private static int GROUND_TIME_MS = 1000;

    public FallBehavior(long triggerNextBehaviorTime) {
        super(triggerNextBehaviorTime);
    }

    @Override
    public void onEnter(Entity entity, BehaviorComponent component) {
        component.passedTimeMs = 0;
        component.triggerNextBehaviorTimeMs = 0;
        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
    }

    @Override
    public void onUpdate(Entity entity, BehaviorComponent component, double delta) {

        if(component.passedTimeMs == 0){
            onEnter(entity, component);
        }
        FallSpeedComponent fallSpeedComponent = entity.getComponent(FallSpeedComponent.class);
        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);

        if (movementComponent != null && fallSpeedComponent != null & component.triggerNextBehaviorTimeMs == 0) {
            int radius = 0;
            BoundingRadiusComponent boundingRadiusComponent = entity.getComponent(BoundingRadiusComponent.class);
            if (boundingRadiusComponent != null) {
                radius = boundingRadiusComponent.boundingCollisionRadius;
            }

            if (movementComponent.y >= Main.HEIGHT - Main.GROUND_OFFSET_HEIGHT - radius) {
                component.triggerNextBehaviorTimeMs = component.passedTimeMs + GROUND_TIME_MS;
            }

            movementComponent.vy = fallSpeedComponent.vy;
            movementComponent.ay = fallSpeedComponent.ay;
        }

        if (component.triggerNextBehaviorTimeMs != 0 && component.passedTimeMs > component.triggerNextBehaviorTimeMs) {
            onExit(entity, component);
        }
        component.passedTimeMs += delta * 1000;
    }

    @Override
    public void onExit(Entity entity, BehaviorComponent component) {
        if (component.nextBehavior != null) {
            component.passedTimeMs = 0;
            component.previousBehavior = this;
            component.currentBehavior = component.nextBehavior;
            component.nextBehavior = null;
        }
        else{
            EntityManager.getInstance().removeEntity(entity);
        }

    }
}
