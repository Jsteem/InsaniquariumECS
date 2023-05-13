package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.game.GameData;

public class HandleNoCollision extends Component {

    public HandleNoCollision(){

    }
    public void handleNoCollision(float x, float y){
        FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity((int)x, (int)y, GameData.getTierFood());
    }
}
