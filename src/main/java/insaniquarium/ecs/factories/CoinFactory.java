package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.BoundingCollisionComponent;
import insaniquarium.ecs.components.GrowthComponent;
import insaniquarium.ecs.components.MovementComponent;
import insaniquarium.ecs.components.TargetComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.ClickTypeComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class CoinFactory extends Factory{
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity coin = new Entity();

        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.MONEY, AnimationComponent.AnimationType.IDLE, level, 0.07, true, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        coin.addComponent(animationComponent);

        int boundingCircleRadius = 15;

        coin.addComponent(new MovementComponent(x, y, 0, 20, 0, 0));

        coin.addComponent(new BoundingCollisionComponent(boundingCircleRadius));

        coin.addComponent(new TargetComponent(CoinTypeComponent.COIN_TYPE.COLLECTABLE.value, ClickTypeComponent.CLICK_TYPE.CLICK.value));

        coin.addComponent(new BehaviorComponent(coin, BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN, null));

        coin.addComponent(new GrowthComponent(level));

        return coin;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
