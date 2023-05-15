package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.GrowthComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.RenderComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

public class PotionFactory extends Factory {
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity potionEntity = FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity(x, y, 3);

        potionEntity.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity entity, Entity target, long mask) {
                GrowthComponent growthComponent = target.getComponent(GrowthComponent.class);
                BehaviorComponent behaviorComponent = target.getComponent(BehaviorComponent.class);
                AnimationComponent animationComponent = target.getComponent(AnimationComponent.class);

                if (behaviorComponent != null && animationComponent != null && growthComponent != null) {

                    if (behaviorComponent.currentBehavior != behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN)) {
                        if (growthComponent.growthLevel == 0) {
                            //small guppy dies
                            //todo: kill the guppy


                            behaviorComponent.currentBehavior = behaviorComponent.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.FALL_DOWN);
                            behaviorComponent.nextBehavior = null;
                            behaviorComponent.currentBehavior.onEnter(target, behaviorComponent);
                            animationComponent.setActiveType(AnimationComponent.AnimationType.DIE);

                        }
                        else if (growthComponent.growthLevel < 3) {
                            //medium and large get star type, king gets unaffected
                            //todo: upgrade the med and large guppy
                        }
                        EntityManager.getInstance().removeEntity(entity);

                    }




                }
            }
        });

        return potionEntity;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        Entity food = new Entity();
        food.addComponent(new RenderComponent(ImageInfo.IMAGE_NAME.FOOD, x, y, 3, 0, 0.75));
        return food;
    }
}
