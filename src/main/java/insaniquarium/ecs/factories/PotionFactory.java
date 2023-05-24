package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.components.RenderComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

public class PotionFactory extends Factory {
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity potionEntity = FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity(x, y, 3);

        return potionEntity;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        Entity food = new Entity();
        food.addComponent(new RenderComponent(ImageInfo.IMAGE_NAME.FOOD, x, y, 3, 0, 0.75));
        return food;
    }
}
