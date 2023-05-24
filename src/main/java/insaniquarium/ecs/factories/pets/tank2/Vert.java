package insaniquarium.ecs.factories.pets.tank2;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.BoundingRadiusComponent;
import insaniquarium.ecs.components.MovementComponent;
import insaniquarium.ecs.components.SpecialMoveComponent;
import insaniquarium.ecs.components.TargetComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.behaviortypecomponents.IdleRandomTargetPosition;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.ecs.components.typecomponents.PetTypeComponent;
import insaniquarium.ecs.factories.Factory;
import insaniquarium.ecs.factories.FactoryManager;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class Vert extends Factory {
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity vert = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.VERT, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.VERT, AnimationComponent.AnimationType.TURN, 1, 0.07, false, 1));



        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        vert.addComponent(animationComponent);

        BehaviorComponent behaviorComponent = new BehaviorComponent(vert, BehaviorComponent.BEHAVIOR_TYPE.IDLE, null);
        vert.addComponent(behaviorComponent);
        IdleRandomTargetPosition idleRandomTargetPosition = new IdleRandomTargetPosition(0,0, true);
        vert.addComponent(idleRandomTargetPosition);

        vert.addComponent(new MovementComponent(250, 250, 0, 0, 0, 0));
        vert.addComponent(new BoundingRadiusComponent(40));

        vert.addComponent(new TargetComponent(PetTypeComponent.PET_TYPE.PET.value, -1));

        vert.addComponent(new SpecialMoveComponent(vert, 3000) {
            @Override
            public void doSpecialMove() {
                MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
                Entity coin = FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE).createEntity((int)movementComponent.x, (int)movementComponent.y, 1  );
                EntityManager.getInstance().addEntity(coin);
            }
        });


        EntityManager.getInstance().addEntity(vert);
        return vert;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
