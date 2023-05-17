package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.Main;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.BoundingCollisionComponent;
import insaniquarium.ecs.components.MovementComponent;

public class JumpBehavior extends BehaviorTypeComponent {

    public JumpBehavior(long triggerNextBehaviorTime) {
        super(triggerNextBehaviorTime);
    }

    @Override
    public void onEnter(Entity entity, BehaviorComponent component) {
        component.passedTimeMs = 0;
        component.triggerNextBehaviorTimeMs = 0;
        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
        movementComponent.vy = -100;
        movementComponent.ay = -150;
        movementComponent.vx *= 0.1;
    }

    @Override
    public void onUpdate(Entity entity, BehaviorComponent component, double delta) {
        if (component.passedTimeMs == 0) {
            onEnter(entity, component);
        }

        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);


            if (movementComponent.y <= 125 ) {
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
        } else {
            EntityManager.getInstance().removeEntity(entity);
        }

    }

}
