package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.animationtypecomponents.IdleRandomTargetPosition;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class BreederFactory extends Factory{

    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity breeder = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER, AnimationComponent.AnimationType.TURN, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER, AnimationComponent.AnimationType.EAT, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, AnimationComponent.AnimationType.DIE, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, AnimationComponent.AnimationType.HUNGRY_IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, AnimationComponent.AnimationType.HUNGRY_TURN, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, AnimationComponent.AnimationType.HUNGRY_EAT, 9, 0.07, false, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        breeder.addComponent(animationComponent);

        int boundingCircleRadius = 40;

        breeder.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));


        breeder.addComponent(new BoundingRadiusComponent(boundingCircleRadius));
        breeder.addComponent(new BoundingCollisionComponent(boundingCircleRadius, boundingCircleRadius));
        breeder.addComponent(new BehaviorComponent(breeder, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK));
        breeder.addComponent(new SpawnComponent(breeder, FactoryManager.getInstance().getFactory(FishTypeComponent.FISH_TYPE.GUPPY_SMALL), 5000, 0));
        breeder.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, FoodTypeComponent.FOOD_TYPE.FOOD.value));


        breeder.addComponent(new FallSpeedComponent(150,0));

        breeder.addComponent(new IdleRandomTargetPosition(0 , 0));

        breeder.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity breeder, Entity food, long mask) {
                BehaviorComponent behaviorComponent = breeder.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = breeder.getComponent(AnimationComponent.class);

                if (behaviorComponent != null && animationComponent != null) {
                    if (animationComponent.animationComplete ||
                            (animationComponent.activeType.type != AnimationComponent.AnimationType.TURN &&
                                    animationComponent.activeType.type != AnimationComponent.AnimationType.HUNGRY_TURN)
                    )
                        if (behaviorComponent.currentBehavior == behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK)) {


                                handleFood(breeder, behaviorComponent);
                                EntityManager.getInstance().removeEntity(food);

                        }
                }
            }
        });


        return breeder;

    }
    public void handleFood(Entity breeder, BehaviorComponent behaviorComponent) {
        behaviorComponent.previousBehavior = behaviorComponent.currentBehavior;
        behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.EAT);
        behaviorComponent.currentBehavior.onEnter(breeder, behaviorComponent);
        AnimationComponent animationComponent = breeder.getComponent(AnimationComponent.class);
        AnimationTypeComponent idle = animationComponent.types.get(AnimationComponent.AnimationType.IDLE);
        if (idle.rowNr < 4) {
            animationComponent.types.get(AnimationComponent.AnimationType.IDLE).rowNr+=3;
            animationComponent.types.get(AnimationComponent.AnimationType.EAT).rowNr+=3;
            animationComponent.types.get(AnimationComponent.AnimationType.TURN).rowNr+=3;

            animationComponent.types.get(AnimationComponent.AnimationType.HUNGRY_EAT).rowNr++;

            animationComponent.types.get(AnimationComponent.AnimationType.DIE).rowNr+=3;
            animationComponent.types.get(AnimationComponent.AnimationType.HUNGRY_IDLE).rowNr+=3;
            animationComponent.types.get(AnimationComponent.AnimationType.HUNGRY_TURN).rowNr+=3;

            //BoundingRadiusComponent boundingRadiusComponent = breeder.getComponent(BoundingRadiusComponent.class);
            //BoundingCollisionComponent boundingCollisionComponent = breeder.getComponent(BoundingCollisionComponent.class);
            //boundingCollisionComponent.boundingCollisionHeight = boundingCircleRadius[growthComponent.growthLevel] * 2;
            //boundingCollisionComponent.boundingCollisionWidth = boundingCircleRadius[growthComponent.growthLevel] * 2;
            //boundingRadiusComponent.boundingCollisionRadius = boundingCircleRadius[growthComponent.growthLevel];

        }
    }
    @Override
    public Entity createDisplayEntity(int x, int y) {

        Entity breeder = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 0.7));
        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        breeder.addComponent(animationComponent);
        breeder.addComponent(new MovementComponent(x-3 , y , 0, 0, 0, 0));
        return breeder;
    }
}
