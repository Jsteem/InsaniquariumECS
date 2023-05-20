package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class BeetleMuncherFactory extends Factory{


    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity beetleMuncher = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, AnimationComponent.AnimationType.TURN, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, AnimationComponent.AnimationType.EAT, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, AnimationComponent.AnimationType.DIE, 5, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, AnimationComponent.AnimationType.HUNGRY_IDLE, 3, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, AnimationComponent.AnimationType.HUNGRY_TURN, 4, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, AnimationComponent.AnimationType.HUNGRY_EAT, 6, 0.07, false, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        beetleMuncher.addComponent(animationComponent);

        int boundingCircleRadius = 40;

        beetleMuncher.addComponent(new MovementComponent(x, y, -1, 0, 0, 0));


        beetleMuncher.addComponent(new BoundingRadiusComponent(boundingCircleRadius));

        beetleMuncher.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, CoinTypeComponent.COIN_TYPE.BEETLE.value));
        BehaviorComponent behaviorComponent = new BehaviorComponent(beetleMuncher, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK);
        beetleMuncher.addComponent(behaviorComponent);
        beetleMuncher.addComponent(new FallSpeedComponent(100,0));
        beetleMuncher.addComponent(new BoundingCollisionComponent(boundingCircleRadius, boundingCircleRadius));
        beetleMuncher.addComponent(new SpawnComponent(beetleMuncher,
                FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE), 5000, 6));

        beetleMuncher.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity beetleMuncher, Entity beetle, long mask) {
                BehaviorComponent behaviorComponent = beetleMuncher.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = beetleMuncher.getComponent(AnimationComponent.class);

                if (behaviorComponent != null && animationComponent != null) {
                    if (animationComponent.animationComplete ||
                            (animationComponent.activeType.type != AnimationComponent.AnimationType.TURN &&
                                    animationComponent.activeType.type != AnimationComponent.AnimationType.HUNGRY_TURN)
                    )
                        if (behaviorComponent.currentBehavior == behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK)) {
                            behaviorComponent.previousBehavior = behaviorComponent.currentBehavior;
                            behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.EAT);
                            behaviorComponent.currentBehavior.onEnter(beetleMuncher, behaviorComponent);

                            GrowthComponent growthComponentFood = beetle.getComponent(GrowthComponent.class);

                            if(growthComponentFood != null){

                                beetle.removeComponent(GrowthComponent.class);
                                EntityManager.getInstance().removeEntity(beetle);
                            }



                        }
                }
            }
        });


        EntityManager.getInstance().addEntity(beetleMuncher);
        return beetleMuncher;

    }

    @Override
    public Entity createDisplayEntity(int x, int y) {

        Entity beetleMuncher = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 0.45));
        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        beetleMuncher.addComponent(animationComponent);
        beetleMuncher.addComponent(new MovementComponent(x-3, y+3, 0, 0, 0, 0));
        return beetleMuncher;
    }
}
