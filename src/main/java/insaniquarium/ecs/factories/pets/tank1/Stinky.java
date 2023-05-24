package insaniquarium.ecs.factories.pets.tank1;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.IdleRandomTargetPosition;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.PetTypeComponent;
import insaniquarium.ecs.factories.Factory;
import insaniquarium.game.GameCanvas;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class Stinky extends Factory {
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity stinky = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.STINKY, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.ON_ATTACK,
                new IdleAnimation(ImageInfo.IMAGE_NAME.STINKY, AnimationComponent.AnimationType.ON_ATTACK, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.STINKY, AnimationComponent.AnimationType.TURN, 1, 0.07, false, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        stinky.addComponent(animationComponent);

        int boundingCircleRadius = 30;

        stinky.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));
        stinky.addComponent(new BoundingRadiusComponent(boundingCircleRadius));
        stinky.addComponent(new BoundingCollisionComponent(boundingCircleRadius, boundingCircleRadius));

        stinky.addComponent(new TargetComponent(PetTypeComponent.PET_TYPE.PET.value, CoinTypeComponent.COIN_TYPE.COLLECTABLE.value));
        stinky.addComponent(new BehaviorComponent(stinky, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK, true));
        stinky.addComponent(new IdleRandomTargetPosition(0,0, true));

        stinky.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity stinky, Entity coin, long mask) {
                BehaviorComponent behaviorComponent = stinky.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = stinky.getComponent(AnimationComponent.class);
                if (behaviorComponent != null && animationComponent != null) {
                    MoneyValueComponent moneyValueComponent = coin.getComponent(MoneyValueComponent.class);
                    EntityManager.getInstance().removeEntity(coin);
                    GameCanvas.increaseMoney(moneyValueComponent.moneyValue);

                }
            }
        });
        EntityManager.getInstance().addEntity(stinky);
        return stinky;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
