package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypes.IdleAnimation;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class UltravoreFactory extends Factory{
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity ultravore = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.TURN, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.EAT, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.DIE, 3, 0.07, false, 1));

        //todo: ultravore has no hungry sprite? could this be a color mask?

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        ultravore.addComponent(animationComponent);

        int boundingCircleRadius = 40;

        ultravore.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));


        ultravore.addComponent(new BoundingCollisionComponent(boundingCircleRadius));
        ultravore.addComponent(new EatCollisionComponent(14,0, 0));
        ultravore.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, FishTypeComponent.FISH_TYPE.CARNIVORE.value));

        EntityManager.getInstance().addEntity(ultravore);
        return ultravore;

    }

    @Override
    public Entity createDisplayEntity(int x, int y) {

        Entity ultravore = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ULTRAVORE, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 0.28));
        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        ultravore.addComponent(animationComponent);
        ultravore.addComponent(new MovementComponent(x + 78 , y + 60, 0, 0, 0, 0));
        return ultravore;
    }
}
