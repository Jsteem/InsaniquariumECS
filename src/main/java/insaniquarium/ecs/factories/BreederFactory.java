package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class BreederFactory extends Factory{

    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity breeder = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER, AnimationComponent.AnimationType.TURN, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER, AnimationComponent.AnimationType.EAT, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, AnimationComponent.AnimationType.DIE, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, AnimationComponent.AnimationType.HUNGRY_IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, AnimationComponent.AnimationType.HUNGRY_TURN, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, AnimationComponent.AnimationType.HUNGRY_EAT, 9, 0.07, false, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        breeder.addComponent(animationComponent);

        int boundingCircleRadius = 40;

        breeder.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));


        breeder.addComponent(new BoundingCollisionComponent(boundingCircleRadius));
        breeder.addComponent(new EatCollisionComponent(14,0, 0));
        breeder.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.FISH.value, FoodTypeComponent.FOOD_TYPE.FOOD.value));

        EntityManager.getInstance().addEntity(breeder);
        return breeder;

    }

    @Override
    public Entity createDisplayEntity(int x, int y) {

        Entity breeder = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.BREEDER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 0.7));
        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        breeder.addComponent(animationComponent);
        breeder.addComponent(new MovementComponent(x-3 , y , 0, 0, 0, 0));
        return breeder;
    }
}
