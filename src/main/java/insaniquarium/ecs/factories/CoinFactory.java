package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
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

        int boundingCircleRadius = 20;

        coin.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));

        coin.addComponent(new BoundingCollisionComponent(boundingCircleRadius));

        coin.addComponent(new FallSpeedComponent(100,0));

        if(level == 2){
            coin.addComponent(new TargetComponent(CoinTypeComponent.COIN_TYPE.STAR.value, ClickTypeComponent.CLICK_TYPE.CLICK.value));
        }
        else if(level == 5){
            coin.addComponent(new TargetComponent(CoinTypeComponent.COIN_TYPE.BEETLE.value, ClickTypeComponent.CLICK_TYPE.CLICK.value));
        }
        else{
            coin.addComponent(new TargetComponent(CoinTypeComponent.COIN_TYPE.COLLECTABLE.value, ClickTypeComponent.CLICK_TYPE.CLICK.value));
        }


        coin.addComponent(new BehaviorComponent(coin, BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN, null));

        coin.addComponent(new GrowthComponent(level));

        return coin;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
