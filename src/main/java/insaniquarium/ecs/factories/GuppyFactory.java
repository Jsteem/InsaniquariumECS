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
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class GuppyFactory extends Factory {
    int[] boundingCircleRadius = {20, 25, 30, 30};
    int[] eatCircleOffset = {12, 16, 25, 25};
    int[] eatCircleRadius = {10, 16, 18, 18};
    public GuppyFactory() {

    }

    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity guppy = new Entity();

        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_SWIM, AnimationComponent.AnimationType.IDLE, level, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_TURN, AnimationComponent.AnimationType.TURN, level, 0.05, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_EAT, AnimationComponent.AnimationType.EAT, level, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_DIE, AnimationComponent.AnimationType.DIE, level, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_SWIM, AnimationComponent.AnimationType.HUNGRY_IDLE, level, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_TURN, AnimationComponent.AnimationType.HUNGRY_TURN, level, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_EAT, AnimationComponent.AnimationType.HUNGRY_EAT, level, 0.07, false, 1));


        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        guppy.addComponent(animationComponent);

        BehaviorComponent behaviorComponent = new BehaviorComponent(guppy, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK);
        guppy.addComponent(behaviorComponent);



        guppy.addComponent(new MovementComponent(x, y, 30, 0, 0, 0));
        guppy.addComponent(new GrowthComponent(level));

        guppy.addComponent(new BoundingCollisionComponent(boundingCircleRadius[0]));
        guppy.addComponent(new EatCollisionComponent(eatCircleRadius[0], eatCircleOffset[0], 0));
        guppy.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.GUPPY_SMALL.value, FoodTypeComponent.FOOD_TYPE.FOOD.value));

        guppy.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity entity, Entity target, long mask) {
                BehaviorComponent behaviorComponent = entity.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
                GrowthComponent growthComponent = entity.getComponent(GrowthComponent.class);
                MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
                if (behaviorComponent != null && animationComponent != null && growthComponent != null) {
                    if (animationComponent.animationComplete ||
                            (animationComponent.activeType.type != AnimationComponent.AnimationType.TURN &&
                                    animationComponent.activeType.type != AnimationComponent.AnimationType.HUNGRY_TURN)
                    )
                        if (behaviorComponent.currentBehavior == behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK)) {
                            behaviorComponent.previousBehavior = behaviorComponent.currentBehavior;
                            behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.EAT);
                            behaviorComponent.currentBehavior.onEnter(entity, behaviorComponent);
                            EntityManager.getInstance().removeEntity(target);

                            if(growthComponent.growthLevel < 3){

                                growthComponent.growthLevel++;
                                EatCollisionComponent eatCollisionComponent = entity.getComponent(EatCollisionComponent.class);
                                eatCollisionComponent.eatCollisionOffsetX = eatCircleOffset[growthComponent.growthLevel];
                                eatCollisionComponent.eatCollisionRadius = eatCircleRadius[growthComponent.growthLevel];
                                BoundingCollisionComponent boundingCollisionComponent = entity.getComponent(BoundingCollisionComponent.class);
                                boundingCollisionComponent.boundingCollisionRadius = boundingCircleRadius[growthComponent.growthLevel];
                                TargetComponent targetComponent = entity.getComponent(TargetComponent.class);
                                targetComponent.maskEntity = FishTypeComponent.FISH_TYPE.FISH.value;
                                SpawnComponent spawnComponent = entity.getComponent(SpawnComponent.class);
                                if(spawnComponent == null){
                                    spawnComponent = new SpawnComponent(entity,
                                            FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE), 1000, 0);
                                    entity.addComponent(spawnComponent);
                                }
                                spawnComponent.level = growthComponent.growthLevel < 3 ?  growthComponent.growthLevel -1 : 3;

                            }
                        }
                }
            }
        });

        EntityManager.getInstance().addEntity(guppy);
        return guppy;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        Entity guppy = new Entity();

        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_SWIM, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        guppy.addComponent(new AnimationComponent(animationComponents));
        guppy.addComponent(new MovementComponent(x, y, -1, 0, 0, 0));
        return guppy;
    }


}


     /*   boolean createRandom = true;
        if(createRandom){

            int minWidth = GameCanvas.SIDE_OFFSET + boundingCircleRadius;
            int maxWidth = Main.WIDTH - GameCanvas.SIDE_OFFSET - boundingCircleRadius;
            int maxHeight = Main.HEIGHT - boundingCircleRadius - GameCanvas.GROUND_OFFSET_HEIGHT;
            int minHeight = GameCanvas.MENU_OFFSET_HEIGHT + boundingCircleRadius;

            x = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
            y = (int) (Math.random() * (maxHeight - minHeight)) + minHeight;

        }*/