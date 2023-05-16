package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.*;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class CarnivoreFactory extends Factory{

    public CarnivoreFactory(){

    }

    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity carnivore = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_SWIM, AnimationComponent.AnimationType.IDLE, 4, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_TURN, AnimationComponent.AnimationType.TURN, 4, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_EAT, AnimationComponent.AnimationType.EAT, 4, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_DIE, AnimationComponent.AnimationType.DIE, 4, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_SWIM, AnimationComponent.AnimationType.HUNGRY_IDLE, 4, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_TURN, AnimationComponent.AnimationType.HUNGRY_TURN, 4, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_EAT, AnimationComponent.AnimationType.HUNGRY_EAT, 4, 0.07, false, 1));


        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        carnivore.addComponent(animationComponent);

        int boundingCircleRadius = 40;

        carnivore.addComponent(new MovementComponent(x, y, 10, 0, 0, 0));


        carnivore.addComponent(new BoundingCollisionComponent(boundingCircleRadius));
        carnivore.addComponent(new EatCollisionComponent(14,20, 0));
        carnivore.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.CARNIVORE.value, FishTypeComponent.FISH_TYPE.GUPPY_SMALL.value));
        carnivore.addComponent(new BehaviorComponent(carnivore, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK));
        carnivore.addComponent(new HandleCollisionComponent() {
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

        SpawnComponent spawnComponent = new SpawnComponent(carnivore,
                FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE), 1000, 3);
        carnivore.addComponent(spawnComponent);

        EntityManager.getInstance().addEntity(carnivore);
        return carnivore;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        Entity carnivore = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_SWIM, AnimationComponent.AnimationType.IDLE, 4, 0.07, true, 0.5));
        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        carnivore.addComponent(animationComponent);
        carnivore.addComponent(new MovementComponent(x, y, -1, 0, 0, 0));


        return carnivore;
    }
}
