package insaniquarium.ecs.factories;

import insaniquarium.ecs.components.typecomponents.*;
import insaniquarium.ecs.factories.aliens.Sylvester;
import insaniquarium.ecs.factories.pets.tank1.*;
import insaniquarium.ecs.factories.pets.tank2.*;

import java.util.HashMap;

public class FactoryManager {
    private static FactoryManager instance;
    private HashMap<Enum, Factory> typeToFactory = new HashMap<>();
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
        typeToFactory.put(CoinTypeComponent.COIN_TYPE.COLLECTABLE, new CoinFactory());
        //PETS TANK1
        typeToFactory.put(PetTypeComponent.PET_TYPE.STINKY, new Stinky());
        typeToFactory.put(PetTypeComponent.PET_TYPE.NIKO, new Niko());
        typeToFactory.put(PetTypeComponent.PET_TYPE.ITCHY, new Itchy());
        typeToFactory.put(PetTypeComponent.PET_TYPE.PREGO, new Prego());
        typeToFactory.put(PetTypeComponent.PET_TYPE.ZORF, new Zorf());
        //PETS TANK2
        typeToFactory.put(PetTypeComponent.PET_TYPE.VERT, new Vert());
        typeToFactory.put(PetTypeComponent.PET_TYPE.CLYDE, new Clyde());
        typeToFactory.put(PetTypeComponent.PET_TYPE.MERYL, new Meryl());
        typeToFactory.put(PetTypeComponent.PET_TYPE.WADSWORTH, new Wadsworth());
        typeToFactory.put(PetTypeComponent.PET_TYPE.RUFUS, new Rufus());


        //ALIENS
        typeToFactory.put(AlienTypeComponent.ALIEN_TYPE.SYLVESTER, new Sylvester());
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
