package insaniquarium.ecs.factories.pets.tank2;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.behaviortypecomponents.IdleRandomTargetPosition;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.PetTypeComponent;
import insaniquarium.ecs.factories.Factory;
import insaniquarium.ecs.factories.FactoryManager;
import insaniquarium.utility.ImageInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class Meryl extends Factory {
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity meryl = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.MERYL, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.MERYL, AnimationComponent.AnimationType.TURN, 2, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.SPECIAL_MOVE_START,
                new IdleAnimation(ImageInfo.IMAGE_NAME.MERYL, AnimationComponent.AnimationType.SPECIAL_MOVE_START, 1, 0.07, false, 1));


        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        meryl.addComponent(animationComponent);

        BehaviorComponent behaviorComponent = new BehaviorComponent(meryl, BehaviorComponent.BEHAVIOR_TYPE.IDLE, null);
        meryl.addComponent(behaviorComponent);
        IdleRandomTargetPosition idleRandomTargetPosition = new IdleRandomTargetPosition(0,0, true);
        meryl.addComponent(idleRandomTargetPosition);

        meryl.addComponent(new MovementComponent(250, 250, 0, 0, 0, 0));
        meryl.addComponent(new BoundingRadiusComponent(40));

        meryl.addComponent(new TargetComponent(PetTypeComponent.PET_TYPE.PET.value, -1));

        meryl.addComponent(new SpecialMoveComponent(meryl, 3000) {
            @Override
            public void doSpecialMove() {
                AnimationComponent animComponent = entity.getComponent(AnimationComponent.class);
                animComponent.setActiveType(AnimationComponent.AnimationType.SPECIAL_MOVE_START);


                for(Entity e : EntityManager.getInstance().getEntities()){
                    SpawnComponent spawnComponent = e.getComponent(SpawnComponent.class);
                    MovementComponent movementComponent = e.getComponent(MovementComponent.class);
                    if (spawnComponent != null && spawnComponent.level < 2 && spawnComponent.type == CoinTypeComponent.COIN_TYPE.COLLECTABLE) {
                        spawnComponent.passedTimeMs = 0;

                        //if factory is of type silver or gold coin then:


                        Entity coin1 = new Entity();
                        Entity coin2 = new Entity();
                        Entity coin3 = new Entity();

                        coin1.addComponent(new MovementComponent(movementComponent.x, movementComponent.y + 40, 0,0,0,0));
                        coin2.addComponent(new MovementComponent(movementComponent.x, movementComponent.y + 40, 0,0,0,0));
                        coin3.addComponent(new MovementComponent(movementComponent.x, movementComponent.y + 40, 0,0,0,0));

                        SpawnComponent coin1Spawn = new SpawnComponent(coin1,
                                FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE), 0, spawnComponent.level, CoinTypeComponent.COIN_TYPE.COLLECTABLE);
                        coin1Spawn.deleteEntityAfterSpawn = true;

                        SpawnComponent coin2Spawn = new SpawnComponent(coin2,
                                FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE), 200, spawnComponent.level, CoinTypeComponent.COIN_TYPE.COLLECTABLE);
                        coin2Spawn.deleteEntityAfterSpawn = true;

                        SpawnComponent coin3Spawn = new SpawnComponent(coin3,
                                FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE), 400, spawnComponent.level, CoinTypeComponent.COIN_TYPE.COLLECTABLE);
                        coin3Spawn.deleteEntityAfterSpawn = true;


                        coin1.addComponent(coin1Spawn);
                        coin2.addComponent(coin2Spawn);
                        coin3.addComponent(coin3Spawn);

                        EntityManager.getInstance().addEntity(coin1);
                        EntityManager.getInstance().addEntity(coin2);
                        EntityManager.getInstance().addEntity(coin3);

                    }
                }

            }
        });


        EntityManager.getInstance().addEntity(meryl);
        return meryl;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
