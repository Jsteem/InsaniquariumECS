package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
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

        beetleMuncher.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));


        beetleMuncher.addComponent(new BoundingCollisionComponent(boundingCircleRadius));
        beetleMuncher.addComponent(new EatCollisionComponent(14,0, 0));
        beetleMuncher.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, CoinTypeComponent.COIN_TYPE.BEETLE.value));

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
