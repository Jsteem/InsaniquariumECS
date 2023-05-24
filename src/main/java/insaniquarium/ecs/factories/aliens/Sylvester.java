package insaniquarium.ecs.factories.aliens;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorTypeComponent;
import insaniquarium.ecs.components.behaviortypecomponents.SeekBehavior;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.typecomponents.AlienTypeComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.factories.Factory;
import insaniquarium.ecs.factories.FactoryManager;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class Sylvester extends Factory {
    @Override
    public Entity createEntity(int x, int y, int level) {
        long maskAllFish = FishTypeComponent.FISH_TYPE.FISH.value |
                FishTypeComponent.FISH_TYPE.CARNIVORE.value |
                FishTypeComponent.FISH_TYPE.GUPPY_SMALL.value |
                CoinTypeComponent.COIN_TYPE.STAR.value;

        Entity sylvester = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SYLVESTER, AnimationComponent.AnimationType.IDLE, 0, 0.07, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SYLVESTER, AnimationComponent.AnimationType.TURN, 1, 0.07, false, 1));

        sylvester.addComponent(new AnimationComponent(animationComponents));
        sylvester.addComponent(new BoundingRadiusComponent(65));
        sylvester.addComponent(new BoundingCollisionComponent(100,180));
        sylvester.addComponent(new MovementComponent(x,y,0,0,0,0));
        sylvester.addComponent(new TargetComponent(AlienTypeComponent.ALIEN_TYPE.ALIEN.value, maskAllFish));
        sylvester.addComponent(new BehaviorComponent(sylvester, BehaviorComponent.BEHAVIOR_TYPE.SEEK, null, false));
        sylvester.addComponent(new HealthComponent(15));

        sylvester.addComponent(new AttackComponent(sylvester,-1, -1, -1) {
            @Override
            public void handleAttack( int x, int y, int weaponTier) {
                this.x = x;
                this.y = y;
                this.weaponTier = weaponTier;
                HealthComponent healthComponent = this.entity.getComponent(HealthComponent.class);
                healthComponent.health -= (1 + (weaponTier * 2));
                if(healthComponent.health <= 0){
                    MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
                    EntityManager.getInstance().removeEntity(entity);

                    Entity diamond = FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE).createEntity((int)movementComponent.x, (int)movementComponent.y, 3);
                    EntityManager.getInstance().addEntity(diamond);
                }

            }
        });

        sylvester.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity entity, Entity target, long mask) {
                EntityManager.getInstance().removeEntity(target);
            }
        });


        EntityManager.getInstance().addEntity(sylvester);
        return sylvester;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
