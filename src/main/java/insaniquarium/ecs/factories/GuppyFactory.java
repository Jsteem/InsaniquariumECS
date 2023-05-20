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
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_TURN, AnimationComponent.AnimationType.TURN, level, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_EAT, AnimationComponent.AnimationType.EAT, level, 0.04, false, 1));

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
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_EAT, AnimationComponent.AnimationType.HUNGRY_EAT, level, 0.04, false, 1));


        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        guppy.addComponent(animationComponent);

        BehaviorComponent behaviorComponent = new BehaviorComponent(guppy, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK);
        guppy.addComponent(behaviorComponent);


        guppy.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));
        guppy.addComponent(new GrowthComponent(level));

        guppy.addComponent(new BoundingRadiusComponent(boundingCircleRadius[0]));

        guppy.addComponent(new BoundingCollisionComponent(boundingCircleRadius[0] * 2, boundingCircleRadius[0] * 2));

        guppy.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.GUPPY_SMALL.value, FoodTypeComponent.FOOD_TYPE.FOOD.value));

        guppy.addComponent(new FallSpeedComponent(150,0));

        guppy.addComponent(new IdlePosition(0 , 0));

        guppy.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity guppy, Entity food, long mask) {
                BehaviorComponent behaviorComponent = guppy.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = guppy.getComponent(AnimationComponent.class);
                GrowthComponent growthComponent = guppy.getComponent(GrowthComponent.class);

                if (behaviorComponent != null && animationComponent != null && growthComponent != null) {
                    if (animationComponent.animationComplete ||
                            (animationComponent.activeType.type != AnimationComponent.AnimationType.TURN &&
                                    animationComponent.activeType.type != AnimationComponent.AnimationType.HUNGRY_TURN)
                    )
                        if (behaviorComponent.currentBehavior == behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK)) {

                            GrowthComponent growthComponentFood = food.getComponent(GrowthComponent.class);

                            if(growthComponentFood != null){
                                //growth levels of food: pellet 0, canned 1, pill 2, potion 3
                                if (growthComponentFood.growthLevel == 3) {
                                    handlePotion(guppy, behaviorComponent);
                                } else {
                                    handleFood(guppy, behaviorComponent);
                                }
                                food.removeComponent(GrowthComponent.class);
                                EntityManager.getInstance().removeEntity(food);
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


    public void handleFood(Entity guppy, BehaviorComponent behaviorComponent) {
        behaviorComponent.previousBehavior = behaviorComponent.currentBehavior;
        behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.EAT);
        behaviorComponent.currentBehavior.onEnter(guppy, behaviorComponent);
        GrowthComponent growthComponent = guppy.getComponent(GrowthComponent.class);
        if (growthComponent.growthLevel < 3) {
            SpawnComponent spawnComponent = guppy.getComponent(SpawnComponent.class);
            if (spawnComponent != null && spawnComponent.level == 2) {
                //star dropping guppies get unaffected
                return;
            }
            growthComponent.growthLevel++;
            BoundingRadiusComponent boundingRadiusComponent = guppy.getComponent(BoundingRadiusComponent.class);
            BoundingCollisionComponent boundingCollisionComponent = guppy.getComponent(BoundingCollisionComponent.class);
            boundingCollisionComponent.boundingCollisionHeight = boundingCircleRadius[growthComponent.growthLevel] * 2;
            boundingCollisionComponent.boundingCollisionWidth = boundingCircleRadius[growthComponent.growthLevel] * 2;

            boundingRadiusComponent.boundingCollisionRadius = boundingCircleRadius[growthComponent.growthLevel];

            TargetComponent targetComponent = guppy.getComponent(TargetComponent.class);
            targetComponent.maskEntity = FishTypeComponent.FISH_TYPE.FISH.value;

            if (spawnComponent == null) {
                spawnComponent = new SpawnComponent(guppy,
                        FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE), 8000, 0);
                guppy.addComponent(spawnComponent);
            }
            spawnComponent.level = growthComponent.growthLevel < 3 ? growthComponent.growthLevel - 1 : 3;

        }
    }
    public void handlePotion(Entity guppy, BehaviorComponent behaviorComponent) {
        if (behaviorComponent.currentBehavior != behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN)) {
            GrowthComponent growthComponent = guppy.getComponent(GrowthComponent.class);
            AnimationComponent animationComponent = guppy.getComponent(AnimationComponent.class);
            if (growthComponent.growthLevel < 2) {
                //small and medium guppy die

                behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN);
                behaviorComponent.nextBehavior = null;
                behaviorComponent.currentBehavior.onEnter(guppy, behaviorComponent);
                animationComponent.setActiveType(AnimationComponent.AnimationType.DIE);

            } else if (growthComponent.growthLevel < 3) {
                //large gets star type, king gets unaffected
                if( guppy.getComponent(SpawnComponent.class).level == 2) {
                    return;
                }
                guppy.getComponent(SpawnComponent.class).level = 2;


                guppy.removeComponent(AnimationComponent.class);
                HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
                animationComponents.put(
                        AnimationComponent.AnimationType.IDLE,
                        new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_SWIM_STAR, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

                animationComponents.put(
                        AnimationComponent.AnimationType.TURN,
                        new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_TURN_STAR, AnimationComponent.AnimationType.TURN, 0, 0.04, false, 1));

                animationComponents.put(
                        AnimationComponent.AnimationType.EAT,
                        new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_EAT_STAR, AnimationComponent.AnimationType.EAT, 0, 0.04, false, 1));

                animationComponents.put(
                        AnimationComponent.AnimationType.DIE,
                        new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_DIE_STAR, AnimationComponent.AnimationType.DIE, 0, 0.07, false, 1));

                animationComponents.put(
                        AnimationComponent.AnimationType.HUNGRY_IDLE,
                        new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_SWIM_STAR, AnimationComponent.AnimationType.HUNGRY_IDLE, 0, 0.07, true, 1));

                animationComponents.put(
                        AnimationComponent.AnimationType.HUNGRY_TURN,
                        new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_TURN_STAR, AnimationComponent.AnimationType.HUNGRY_TURN, 0, 0.04, false, 1));

                animationComponents.put(
                        AnimationComponent.AnimationType.HUNGRY_EAT,
                        new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_EAT_STAR, AnimationComponent.AnimationType.HUNGRY_EAT, 0, 0.04, false, 1));
                guppy.addComponent(new AnimationComponent(animationComponents));




            }
        }
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