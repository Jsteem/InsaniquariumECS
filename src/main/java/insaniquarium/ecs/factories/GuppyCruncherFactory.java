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

public class GuppyCruncherFactory extends Factory{
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity guppyCruncher = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.GUPPY_CRUNCHER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.GUPPY_CRUNCHER, AnimationComponent.AnimationType.EAT, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.GUPPY_CRUNCHER, AnimationComponent.AnimationType.DIE, 3, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.GUPPY_CRUNCHER, AnimationComponent.AnimationType.HUNGRY_IDLE, 2, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.GUPPY_CRUNCHER, AnimationComponent.AnimationType.HUNGRY_EAT, 4, 0.07, false, 1));


        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        guppyCruncher.addComponent(animationComponent);

        int boundingCircleRadius = 40;

        guppyCruncher.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));


        guppyCruncher.addComponent(new BoundingRadiusComponent(boundingCircleRadius));

        guppyCruncher.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, FishTypeComponent.FISH_TYPE.GUPPY_SMALL.value));
        guppyCruncher.addComponent(new BoundingCollisionComponent(boundingCircleRadius, 600));
        guppyCruncher.addComponent(new BehaviorComponent(guppyCruncher, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK, true));
        guppyCruncher.addComponent(new FallSpeedComponent(100,0));
        guppyCruncher.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity guppyCruncher, Entity guppy, long mask) {
                BehaviorComponent behaviorComponent = guppyCruncher.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = guppyCruncher.getComponent(AnimationComponent.class);
                MovementComponent movementComponent  = guppyCruncher.getComponent(MovementComponent.class);
                if (behaviorComponent != null && animationComponent != null) {

                    if (behaviorComponent.currentBehavior == behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK)) {


                        behaviorComponent.previousBehavior = behaviorComponent.currentBehavior;
                        behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.EAT);
                        behaviorComponent.currentBehavior.onEnter(guppyCruncher, behaviorComponent);

                        GrowthComponent growthComponentFood = guppy.getComponent(GrowthComponent.class);

                        if(growthComponentFood != null){

                            Entity beetle = FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE).createEntity((int) movementComponent.x, (int) movementComponent.y, 5 );
                            beetle.removeComponent(BehaviorComponent.class);
                            BehaviorComponent behaviorComponentDiamond = new BehaviorComponent(beetle, BehaviorComponent.BEHAVIOR_TYPE.JUMP, BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN);
                            beetle.addComponent(behaviorComponentDiamond);
                            EntityManager.getInstance().addEntity(beetle);

                            guppy.removeComponent(GrowthComponent.class);
                            EntityManager.getInstance().removeEntity(guppy);
                        }


                    }
                }
            }
        });
        EntityManager.getInstance().addEntity(guppyCruncher);
        return guppyCruncher;

    }

    @Override
    public Entity createDisplayEntity(int x, int y) {

        Entity guppyCruncher = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.GUPPY_CRUNCHER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 0.45));



        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        guppyCruncher.addComponent(animationComponent);
        guppyCruncher.addComponent(new MovementComponent(x , y, 0, 0, 0, 0));
        return guppyCruncher;
    }
}
