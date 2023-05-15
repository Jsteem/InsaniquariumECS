package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
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


        starCatcher.addComponent(new BoundingCollisionComponent(boundingCircleRadius));
        starCatcher.addComponent(new EatCollisionComponent(14,0, 0));
        starCatcher.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, CoinTypeComponent.COIN_TYPE.STAR.value));
        starCatcher.addComponent(new BehaviorComponent(starCatcher, BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN, BehaviorComponent.BEHAVIOR_TYPE.IDLE, true));
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
