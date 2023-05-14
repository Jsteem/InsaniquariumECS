package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypes.*;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class CarnivoreFactory extends Factory{

    public CarnivoreFactory(){

    }

    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity carnivore = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap();
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

        carnivore.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));


        carnivore.addComponent(new BoundingCollisionComponent(boundingCircleRadius));
        carnivore.addComponent(new EatCollisionComponent(14,20, 0));
        carnivore.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.CARNIVORE.value, FishTypeComponent.FISH_TYPE.GUPPY_SMALL.value));

        EntityManager.getInstance().addEntity(carnivore);
        return carnivore;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        Entity carnivore = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_SWIM, AnimationComponent.AnimationType.IDLE, 4, 0.07, true, 0.5));
        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        carnivore.addComponent(animationComponent);
        carnivore.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));

        return carnivore;
    }
}
