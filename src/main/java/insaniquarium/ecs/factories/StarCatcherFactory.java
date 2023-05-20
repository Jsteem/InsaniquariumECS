package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.animationtypecomponents.IdlePosition;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class StarCatcherFactory extends Factory{


    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity starCatcher = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.STAR_CATCHER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.STAR_CATCHER, AnimationComponent.AnimationType.HUNGRY_IDLE, 1, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.STAR_CATCHER, AnimationComponent.AnimationType.DIE, 2, 0.07, false, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        starCatcher.addComponent(animationComponent);

        int boundingCircleRadius = 40;

        starCatcher.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));
        starCatcher.addComponent(new BoundingRadiusComponent(boundingCircleRadius));
        starCatcher.addComponent(new BoundingCollisionComponent(boundingCircleRadius, boundingCircleRadius));

        starCatcher.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, CoinTypeComponent.COIN_TYPE.STAR.value));
        starCatcher.addComponent(new BehaviorComponent(starCatcher, BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN, BehaviorComponent.BEHAVIOR_TYPE.IDLE, true));
        starCatcher.addComponent(new IdlePosition(0,0));
        starCatcher.addComponent(new FallSpeedComponent(100,0));

        starCatcher.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity starCatcher, Entity star, long mask) {
                BehaviorComponent behaviorComponent = starCatcher.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = starCatcher.getComponent(AnimationComponent.class);
                MovementComponent movementComponent  = starCatcher.getComponent(MovementComponent.class);
                if (behaviorComponent != null && animationComponent != null) {

                        if (behaviorComponent.currentBehavior == behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK)) {
                            behaviorComponent.previousBehavior = behaviorComponent.currentBehavior;
                            behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.EAT);
                            behaviorComponent.currentBehavior.onEnter(starCatcher, behaviorComponent);


                            GrowthComponent growthComponentFood = star.getComponent(GrowthComponent.class);

                            if(growthComponentFood != null){

                                Entity diamond = FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE).createEntity((int) movementComponent.x, (int) movementComponent.y, 3 );
                                diamond.removeComponent(BehaviorComponent.class);
                                BehaviorComponent behaviorComponentDiamond = new BehaviorComponent(diamond, BehaviorComponent.BEHAVIOR_TYPE.JUMP, BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN);
                                diamond.addComponent(behaviorComponentDiamond);

                                EntityManager.getInstance().addEntity(diamond);

                                star.removeComponent(GrowthComponent.class);
                                EntityManager.getInstance().removeEntity(star);
                            }

                        }
                }
            }
        });
        EntityManager.getInstance().addEntity(starCatcher);
        return starCatcher;

    }

    @Override
    public Entity createDisplayEntity(int x, int y) {

        Entity starCatcher = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.STAR_CATCHER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 0.5));
        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        starCatcher.addComponent(animationComponent);
        starCatcher.addComponent(new MovementComponent(x , y, 0, 0, 0, 0));

        return starCatcher;
    }
}
