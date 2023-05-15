package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.GrowthComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.RenderComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

public class PotionFactory extends Factory{
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity potionEntity = FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity(x, y, 3);
        potionEntity.addComponent(new HandleCollisionComponent() {
            @Override
            public void handleCollision(Entity entity, long mask) {
                GrowthComponent growthComponent = entity.getComponent(GrowthComponent.class);
                if(growthComponent != null){
                    if(growthComponent.growthLevel == 0){
                        //small guppy dies
                        //todo: kill the guppy

                    }else if (growthComponent.growthLevel < 3){
                        //medium and large get star type, king gets unaffected
                        //todo: upgrade the med and large guppy
                    }

                }
            }
        });

        return potionEntity;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        Entity food = new Entity();
        food.addComponent(new RenderComponent(ImageInfo.IMAGE_NAME.FOOD, x , y , 3, 0, 0.75));
        return food;
    }
}
