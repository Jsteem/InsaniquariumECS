package insaniquarium.ecs.factories.pets.tank2;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.behaviortypecomponents.IdleRandomTargetPosition;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.PetTypeComponent;
import insaniquarium.ecs.factories.Factory;
import insaniquarium.game.GameCanvas;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class Clyde extends Factory {

    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity clyde = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.CLYDE, AnimationComponent.AnimationType.IDLE, 0, 0.10, true, 1));



        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        clyde.addComponent(animationComponent);

        int boundingCircleRadius = 40;

        clyde.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));
        clyde.addComponent(new BoundingRadiusComponent(boundingCircleRadius));
        clyde.addComponent(new BoundingCollisionComponent(boundingCircleRadius, boundingCircleRadius));

        clyde.addComponent(new TargetComponent(PetTypeComponent.PET_TYPE.PET.value, CoinTypeComponent.COIN_TYPE.COLLECTABLE.value));
        clyde.addComponent(new BehaviorComponent(clyde, BehaviorComponent.BEHAVIOR_TYPE.IDLE, BehaviorComponent.BEHAVIOR_TYPE.SEEK, false));
        clyde.addComponent(new IdleRandomTargetPosition(0,0, true));

        clyde.addComponent(new HandleCollisionComponent() {
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
        EntityManager.getInstance().addEntity(clyde);
        return clyde;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
