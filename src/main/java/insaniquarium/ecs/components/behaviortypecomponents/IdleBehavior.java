package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.Main;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.components.MovementComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleRandomTargetPosition;

import java.util.Random;

public class IdleBehavior extends BehaviorTypeComponent {

    public IdleBehavior(long triggerNextBehaviorTime) {
        super(triggerNextBehaviorTime);
    }

    @Override
    public void onEnter(Entity entity, BehaviorComponent component) {

        component.triggerNextBehaviorTimeMs = durationBehaviorMs;
        component.passedTimeMs = 0;

        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        IdleRandomTargetPosition idlePosition = entity.getComponent(IdleRandomTargetPosition.class);
        if (animationComponent != null) {
            animationComponent.setActiveTypeSmooth(AnimationComponent.AnimationType.IDLE);
        }
        if(idlePosition != null){
            idlePosition.x = (int) (Math.random() * Main.WIDTH);
            idlePosition.y = (int) (Math.random() * Main.HEIGHT);
        }
        component.sick = false;
    }

    @Override
    public void onUpdate(Entity entity, BehaviorComponent component, double delta) {

        if (component.passedTimeMs == 0) {
            onEnter(entity, component);
        }
        component.passedTimeMs += delta * 1000;


        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
        IdleRandomTargetPosition idlePosition = entity.getComponent(IdleRandomTargetPosition.class);
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);

        if (movementComponent != null && idlePosition != null) {
            Random r = new Random();
            if (r.nextDouble() < 0.001) {
                idlePosition.x = (int) (Math.random() * Main.WIDTH);
                idlePosition.y = (int) (Math.random() * Main.HEIGHT);

            }
            if(idlePosition.x > 0 && idlePosition.y > 0){
                final double DESIRED_SPEED = 60;

                final double CURVE_FACTOR = 0.1;
                final double ARRIVAL_RADIUS = 5.0; // Increase the arrival radius for smoother arrival

                double dx = idlePosition.x - movementComponent.x;
                double dy = idlePosition.y - movementComponent.y;

                double directionLength = Math.sqrt(dx * dx + dy * dy);

                if(directionLength > ARRIVAL_RADIUS){
                    double directionX = dx / directionLength;
                    double directionY = dy / directionLength;


                    double distanceFactor = Math.max(0.0, Math.min(1.0, directionLength / ARRIVAL_RADIUS));
                    double desiredSpeed = distanceFactor * DESIRED_SPEED;
                    double desiredVelocityX = directionX * desiredSpeed;
                    double desiredVelocityY = directionY * desiredSpeed;

                    double perpendicularX = -dy;
                    double perpendicularY = dx;
                    double steeringForceX = perpendicularX * CURVE_FACTOR;
                    double steeringForceY = perpendicularY * CURVE_FACTOR;


                    double finalVelocityX = desiredVelocityX + steeringForceX;
                    double finalVelocityY = desiredVelocityY + steeringForceY;


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
                            animationComponent.setActiveTypeSmooth(AnimationComponent.AnimationType.IDLE);
                        } else {
                            animationComponent.setActiveTypeSmooth(AnimationComponent.AnimationType.HUNGRY_IDLE);
                        }
                    }

                    movementComponent.vx = (float) finalVelocityX;
                    if (!component.boundToGround) {
                        movementComponent.vy = (float) finalVelocityY;
                    }

                }
                else{
                    idlePosition.x = -1;
                    idlePosition.y = -1;
                }

            }

            else{
                movementComponent.vx *= 0.99;
                movementComponent.vy *= 0.99;
            }


        }
        if (component.passedTimeMs > component.triggerNextBehaviorTimeMs) {
            onExit(entity, component);
        }


    }

    @Override
    public void onExit(Entity entity, BehaviorComponent component) {

        component.passedTimeMs = 0;
        component.previousBehavior = this;
        if (component.nextBehavior != null) {

            component.currentBehavior = component.nextBehavior;
        } else {
            component.nextBehavior = component.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK);
        }
    }
}
