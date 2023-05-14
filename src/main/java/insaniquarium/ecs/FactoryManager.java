package insaniquarium.ecs;

import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.ecs.factories.*;

import java.util.HashMap;

public class FactoryManager {
    private static FactoryManager instance;
    private HashMap<Enum, Factory> typeToFactory = new HashMap();
    private FactoryManager(){
        typeToFactory.put(FishTypeComponent.FISH_TYPE.GUPPY_SMALL, new GuppyFactory());
        typeToFactory.put(FishTypeComponent.FISH_TYPE.CARNIVORE, new CarnivoreFactory());
        typeToFactory.put(FoodTypeComponent.FOOD_TYPE.FOOD, new FoodFactory());
        typeToFactory.put(FoodTypeComponent.FOOD_TYPE.POTION, new PotionFactory());
        typeToFactory.put(FishTypeComponent.FISH_TYPE.STAR_CATCHER, new StarCatcherFactory());
        typeToFactory.put(FishTypeComponent.FISH_TYPE.BEETLE_MUNCHER, new BeetleMuncherFactory());
        typeToFactory.put(FishTypeComponent.FISH_TYPE.GUPPY_CRUNCHER, new GuppyCruncherFactory());
        typeToFactory.put(FishTypeComponent.FISH_TYPE.BREEDER, new BreederFactory());
        typeToFactory.put(FishTypeComponent.FISH_TYPE.ULTRAVORE, new UltravoreFactory());
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
