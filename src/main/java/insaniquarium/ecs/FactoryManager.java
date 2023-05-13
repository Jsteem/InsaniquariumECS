package insaniquarium.ecs;

import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.ecs.factories.CarnivoreFactory;
import insaniquarium.ecs.factories.Factory;
import insaniquarium.ecs.factories.FoodFactory;
import insaniquarium.ecs.factories.GuppyFactory;

import java.util.HashMap;

public class FactoryManager {
    private static FactoryManager instance;
    private HashMap<Enum, Factory> typeToFactory = new HashMap();
    private FactoryManager(){
        typeToFactory.put(FishTypeComponent.FISH_TYPE.GUPPY_SMALL, new GuppyFactory());
        typeToFactory.put(FishTypeComponent.FISH_TYPE.CARNIVORE, new CarnivoreFactory());
        typeToFactory.put(FoodTypeComponent.FOOD_TYPE.FOOD, new FoodFactory());
    }
    public static FactoryManager getInstance(){
        if(instance == null){
            instance = new FactoryManager();
        }
        return instance;
    }

    public Factory getFactory(Enum type){
        return typeToFactory.get(type);
    }

}
