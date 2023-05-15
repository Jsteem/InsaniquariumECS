package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
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


        guppyCruncher.addComponent(new BoundingCollisionComponent(boundingCircleRadius));
        guppyCruncher.addComponent(new EatCollisionComponent(14,0, 0));
        guppyCruncher.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, FishTypeComponent.FISH_TYPE.GUPPY_SMALL.value));

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
