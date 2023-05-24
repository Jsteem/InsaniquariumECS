package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.IdleRandomTargetPosition;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class UltravoreFactory extends Factory{
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity ultravore = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.TURN, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.EAT, 1, 0.04, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.DIE, 3, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE_HUNGRY, AnimationComponent.AnimationType.HUNGRY_TURN, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE_HUNGRY, AnimationComponent.AnimationType.HUNGRY_IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE_HUNGRY, AnimationComponent.AnimationType.HUNGRY_EAT, 1, 0.04, false, 1));




        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        ultravore.addComponent(animationComponent);

        int boundingCircleRadius = 80;

        ultravore.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));


        ultravore.addComponent(new BoundingRadiusComponent(boundingCircleRadius));
        ultravore.addComponent(new BoundingCollisionComponent(boundingCircleRadius, boundingCircleRadius));
        ultravore.addComponent(new BehaviorComponent(ultravore, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK));

        ultravore.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, FishTypeComponent.FISH_TYPE.CARNIVORE.value));
        ultravore.addComponent(new FallSpeedComponent(100,0));
        ultravore.addComponent(new IdleRandomTargetPosition(0,0, false));

        SpawnComponent spawnComponent = new SpawnComponent(ultravore,
                FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE), 7000, 4, CoinTypeComponent.COIN_TYPE.COLLECTABLE);
        ultravore.addComponent(spawnComponent);
        ultravore.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity entity, Entity target, long mask) {
                BehaviorComponent behaviorComponent = entity.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);

                if (behaviorComponent != null && animationComponent != null) {
                    if (animationComponent.animationComplete ||
                            (animationComponent.activeType.type != AnimationComponent.AnimationType.TURN &&
                                    animationComponent.activeType.type != AnimationComponent.AnimationType.HUNGRY_TURN)
                    ) {
                        if (behaviorComponent.currentBehavior == behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK)) {
                            behaviorComponent.previousBehavior = behaviorComponent.currentBehavior;
                            behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.EAT);
                            behaviorComponent.currentBehavior.onEnter(entity, behaviorComponent);
                            EntityManager.getInstance().removeEntity(target);

                        }
                    }
                }
            }
        });

        EntityManager.getInstance().addEntity(ultravore);
        return ultravore;

    }

    @Override
    public Entity createDisplayEntity(int x, int y) {

        Entity ultravore = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 0.28));
        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        ultravore.addComponent(animationComponent);
        ultravore.addComponent(new MovementComponent(x  , y , -1, 0, 0, 0));
        return ultravore;
    }
}
