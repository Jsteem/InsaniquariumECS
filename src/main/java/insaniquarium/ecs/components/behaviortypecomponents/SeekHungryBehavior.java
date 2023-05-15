package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.SystemManager;
import insaniquarium.ecs.components.MovementComponent;
import insaniquarium.ecs.components.TargetComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.utility.CollisionObject;
import insaniquarium.utility.KDTree;

public class SeekHungryBehavior extends BehaviorTypeComponent {
    public SeekHungryBehavior(long triggerNextBehaviorTime) {
        super(triggerNextBehaviorTime);
    }

    @Override
    public void onEnter(Entity entity, BehaviorComponent component) {
        component.triggerNextBehaviorTimeMs = durationBehaviorMs;
        component.nextBehavior = component.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK_HUNGRY);
        if (component.sick) {
            component.nextBehavior = component.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN);
            AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
            if (animationComponent != null) {
                animationComponent.setActiveType(AnimationComponent.AnimationType.HUNGRY_IDLE);
            }

        }

    }

    @Override
    public void onUpdate(Entity entity, BehaviorComponent component, double delta) {
        if (component.passedTimeMs == 0) {
            onEnter(entity, component);
        }

        TargetComponent targetComponent = entity.getComponent(TargetComponent.class);
        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);


        if (targetComponent != null && movementComponent != null) {
            KDTree kdTree = SystemManager.getInstance().getKDTree().get(targetComponent.maskEntityTarget);
            CollisionObject target = kdTree.searchNearest(movementComponent.x, movementComponent.y);

            double finalVelocityX = movementComponent.vx;
            double finalVelocityY = movementComponent.vy;

            if (target != null) {
                final double DESIRED_SPEED = 80;
                final double CURVE_FACTOR = 0.10;
                final double ARRIVAL_RADIUS = 5.0;
                double dx = target.x - movementComponent.x;
                double dy = target.y - movementComponent.y;

                double directionLength = Math.sqrt(dx * dx + dy * dy);
                double directionX = dx / directionLength;
                double directionY = dy / directionLength;

                // Calculate desired velocity
                double distanceFactor = Math.max(0.0, Math.min(1.0, directionLength / ARRIVAL_RADIUS));
                double desiredSpeed = distanceFactor * DESIRED_SPEED;
                double desiredVelocityX = directionX * desiredSpeed;
                double desiredVelocityY = directionY * desiredSpeed;

                // Calculate steering force
                double perpendicularX = -dy;
                double perpendicularY = dx;
                double steeringForceX = perpendicularX * CURVE_FACTOR;
                double steeringForceY = perpendicularY * CURVE_FACTOR;

                // Add steering force to desired velocity
                finalVelocityX = desiredVelocityX + steeringForceX;
                finalVelocityY = desiredVelocityY + steeringForceY;


            } else {
                finalVelocityX *= 0.99;
                finalVelocityY *= 0.99;
            }
            if (movementComponent.vx * finalVelocityX < 0
                    && (animationComponent.activeType.type != AnimationComponent.AnimationType.TURN ||
                    animationComponent.activeType.type != AnimationComponent.AnimationType.HUNGRY_TURN)) {

                if (animationComponent != null) {

                    if (!component.sick) {
                        animationComponent.setActiveType(AnimationComponent.AnimationType.TURN);
                    } else {
                        animationComponent.setActiveType(AnimationComponent.AnimationType.HUNGRY_TURN);
                    }
                }

            }
            if (animationComponent.animationComplete) {

                if (!component.sick) {
                    animationComponent.setActiveType(AnimationComponent.AnimationType.IDLE);
                } else {
                    animationComponent.setActiveType(AnimationComponent.AnimationType.HUNGRY_IDLE);
                }
            }

            movementComponent.vx = (float) finalVelocityX;
            movementComponent.vy = (float) finalVelocityY;
        }


        component.passedTimeMs += delta * 1000;
        if (component.passedTimeMs > component.triggerNextBehaviorTimeMs) {
            onExit(entity, component);
        }

    }

    @Override
    public void onExit(Entity entity, BehaviorComponent component) {
        if (component.nextBehavior != null) {
            component.passedTimeMs = 0;
            component.previousBehavior = this;
            component.currentBehavior = component.nextBehavior;
            if (!component.sick) {
                component.sick = true;
            } else {
                AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
                if (animationComponent != null) {

                    animationComponent.setActiveType(AnimationComponent.AnimationType.DIE);
                }
            }

        }
    }
}
