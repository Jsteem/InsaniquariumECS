package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.*;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleNoCollision;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class FoodFactory extends Factory{
    @Override
    public Entity createEntity(int x, int y, int level) {

        Entity food = new Entity();

        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.FOOD, AnimationComponent.AnimationType.IDLE, level, 0.07, true, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        food.addComponent(animationComponent);

        int boundingCircleRadius = 15;

        food.addComponent(new MovementComponent(x, y, 0, 20, 0, 0));

        food.addComponent(new BoundingCollisionComponent(boundingCircleRadius));

        food.addComponent(new TargetComponent(FoodTypeComponent.FOOD_TYPE.FOOD.value, 0));

        food.addComponent(new BehaviorComponent(food, BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN, null));



        return food;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        Entity food = new Entity();

        food.addComponent(new RenderComponent(ImageInfo.IMAGE_NAME.FOOD, x, y, 1, 0, 1));

        return food;
    }


}
