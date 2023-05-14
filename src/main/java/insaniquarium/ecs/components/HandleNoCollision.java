package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.game.GameData;

public class HandleNoCollision extends Component {

    public HandleNoCollision(){

    }
    public void handleNoCollision(Entity clickEntity, float x, float y){
        PotionComponent potionComponent = clickEntity.getComponent(PotionComponent.class);
        if(potionComponent != null){
            if(potionComponent.hasAvailablePotion()){
                FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity((int)x, (int)y, 4);
                return;
            }
        }
        FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity((int)x, (int)y, GameData.getTierFood());
    }
}
