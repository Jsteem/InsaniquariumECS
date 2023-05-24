package insaniquarium.ecs.factories.pets.tank1;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.IdleRandomTargetPosition;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.ecs.components.typecomponents.PetTypeComponent;
import insaniquarium.ecs.factories.Factory;
import insaniquarium.ecs.factories.FactoryManager;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class Zorf extends Factory {
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity zorf = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ZORF, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ZORF, AnimationComponent.AnimationType.TURN, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.SPECIAL_MOVE_START,
                new IdleAnimation(ImageInfo.IMAGE_NAME.ZORF, AnimationComponent.AnimationType.SPECIAL_MOVE_START, 2, 0.07, false, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        zorf.addComponent(animationComponent);

        BehaviorComponent behaviorComponent = new BehaviorComponent(zorf, BehaviorComponent.BEHAVIOR_TYPE.IDLE, null);
        zorf.addComponent(behaviorComponent);
        IdleRandomTargetPosition idleRandomTargetPosition = new IdleRandomTargetPosition(0,0, true);
        zorf.addComponent(idleRandomTargetPosition);

        zorf.addComponent(new MovementComponent(250, 250, 0, 0, 0, 0));
        zorf.addComponent(new BoundingRadiusComponent(40));

        zorf.addComponent(new TargetComponent(PetTypeComponent.PET_TYPE.PET.value, -1));

        zorf.addComponent(new SpecialMoveComponent(zorf, 3000) {
            @Override
            public void doSpecialMove() {
                MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
                AnimationComponent animComponent = entity.getComponent(AnimationComponent.class);
                animComponent.setActiveType(AnimationComponent.AnimationType.SPECIAL_MOVE_START);
                animComponent.setNextActiveType(AnimationComponent.AnimationType.IDLE);
                Entity food = FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity((int)movementComponent.x, (int)movementComponent.y, 1  );
                EntityManager.getInstance().addEntity(food);
            }
        });


        EntityManager.getInstance().addEntity(zorf);
        return zorf;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
