package insaniquarium.game;

import insaniquarium.ecs.components.typecomponents.AlienTypeComponent;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelData {
    private int tankNumber;
    private int levelNumber;
    private Enum[] variableSlots;
    //variable
    private int[] prices;

    private long[] alienData;

    private PlayerData playerData;

    ImageInfo.IMAGE_NAME[] backGroundImageNames = {
            ImageInfo.IMAGE_NAME.BACKGROUND_TANK0,
            ImageInfo.IMAGE_NAME.BACKGROUND_TANK1,
            ImageInfo.IMAGE_NAME.BACKGROUND_TANK3,
            ImageInfo.IMAGE_NAME.BACKGROUND_TANK4,
            ImageInfo.IMAGE_NAME.BACKGROUND_TANK5,
    };


    //variableSlotsData - what type should be in slot1, slot4 and slot5
    private static Enum[][] variableSlotsData = {
            //tank0
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, null, null},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, null, null},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FishTypeComponent.FISH_TYPE.CARNIVORE, null},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FishTypeComponent.FISH_TYPE.CARNIVORE, null},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FishTypeComponent.FISH_TYPE.CARNIVORE, null},
            //tank1
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FoodTypeComponent.FOOD_TYPE.POTION, null},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FoodTypeComponent.FOOD_TYPE.POTION, FishTypeComponent.FISH_TYPE.STAR_CATCHER},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FoodTypeComponent.FOOD_TYPE.POTION, FishTypeComponent.FISH_TYPE.STAR_CATCHER},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FoodTypeComponent.FOOD_TYPE.POTION, FishTypeComponent.FISH_TYPE.STAR_CATCHER},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FoodTypeComponent.FOOD_TYPE.POTION, FishTypeComponent.FISH_TYPE.STAR_CATCHER},
            //tank2
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FishTypeComponent.FISH_TYPE.GUPPY_CRUNCHER, null},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FishTypeComponent.FISH_TYPE.GUPPY_CRUNCHER, FishTypeComponent.FISH_TYPE.BEETLE_MUNCHER},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FishTypeComponent.FISH_TYPE.GUPPY_CRUNCHER, FishTypeComponent.FISH_TYPE.BEETLE_MUNCHER},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FishTypeComponent.FISH_TYPE.GUPPY_CRUNCHER, FishTypeComponent.FISH_TYPE.BEETLE_MUNCHER},
            {FishTypeComponent.FISH_TYPE.GUPPY_SMALL, FishTypeComponent.FISH_TYPE.GUPPY_CRUNCHER, FishTypeComponent.FISH_TYPE.BEETLE_MUNCHER},
            //tank3
            {FishTypeComponent.FISH_TYPE.BREEDER, FishTypeComponent.FISH_TYPE.CARNIVORE, null},
            {FishTypeComponent.FISH_TYPE.BREEDER, FishTypeComponent.FISH_TYPE.CARNIVORE, FishTypeComponent.FISH_TYPE.ULTRAVORE},
            {FishTypeComponent.FISH_TYPE.BREEDER, FishTypeComponent.FISH_TYPE.CARNIVORE, FishTypeComponent.FISH_TYPE.ULTRAVORE},
            {FishTypeComponent.FISH_TYPE.BREEDER, FishTypeComponent.FISH_TYPE.CARNIVORE, FishTypeComponent.FISH_TYPE.ULTRAVORE},
            {FishTypeComponent.FISH_TYPE.BREEDER, FishTypeComponent.FISH_TYPE.CARNIVORE, FishTypeComponent.FISH_TYPE.ULTRAVORE},

    };
    //Note that we know what slots can be opened this level by looking at the non-zero prices
    private static int[][] pricesData = {
            //tank0
            {100, 0, 0, 0, 0, 0, 150}, //no aliens
            {100, 200, 300, 0, 0, 0, 500}, //sylvester
            {100, 200, 300, 1000, 0, 1000, 2000}, //sylvester
            {100, 200, 300, 1000, 0, 1000, 3000}, //balrog
            {100, 200, 300, 1000, 0, 1000, 5000}, //balrog + sylvester
            //tank1
            {100, 200, 300, 250, 0, 0, 750}, //sylvester
            {100, 200, 300, 250, 750, 1000, 3000}, //balrog
            {100, 200, 300, 250, 750, 1000, 5000}, //gus (type g)
            {100, 200, 300, 250, 750, 1000, 7500}, //destructor (type d)
            {100, 200, 300, 250, 750, 1000, 10000}, //destructor
            //tank2
            {100, 200, 300, 750, 0, 0, 1000}, //balrog
            {100, 200, 300, 750, 2000, 2000, 5000}, //destructor
            {100, 200, 300, 750, 2000, 2000, 7500}, //psychosquid
            {100, 200, 300, 750, 2000, 2000, 10000}, //cyclops
            {100, 200, 300, 750, 2000, 2000, 15000}, //cyclops + psychosquid
            //tank3
            {200, 200, 300, 1000, 0, 0, 3000}, //balrog
            {200, 200, 300, 1000, 10000, 5000, 25000}, //bilaterus (type II)
            {200, 200, 300, 1000, 10000, 5000, 50000}, //gus, psychoquid + balrog
            {200, 200, 300, 1000, 10000, 5000, 75000}, //bilaterus, cyclops + destructor
            {200, 200, 300, 1000, 10000, 5000, 99999}, //bilaterus, cyclops + destructor, psychoquid + balrog
            //boss
    };
    private static long[][] aliensData = {
            //tank1
            {0},
            {AlienTypeComponent.ALIEN_TYPE.SYLVESTER.value},
            {AlienTypeComponent.ALIEN_TYPE.SYLVESTER.value},
            {AlienTypeComponent.ALIEN_TYPE.BALROG.value},
            {AlienTypeComponent.ALIEN_TYPE.BALROG.value, AlienTypeComponent.ALIEN_TYPE.SYLVESTER.value},

            //tank2
            {AlienTypeComponent.ALIEN_TYPE.SYLVESTER.value},
            {AlienTypeComponent.ALIEN_TYPE.BALROG.value},
            {AlienTypeComponent.ALIEN_TYPE.GUS.value},
            {AlienTypeComponent.ALIEN_TYPE.DESTRUCTOR.value},
            {AlienTypeComponent.ALIEN_TYPE.DESTRUCTOR.value},
            
            //tank3
            {AlienTypeComponent.ALIEN_TYPE.BALROG.value},
            {AlienTypeComponent.ALIEN_TYPE.DESTRUCTOR.value},
            {AlienTypeComponent.ALIEN_TYPE.PSYCHOSQUID.value},
            {AlienTypeComponent.ALIEN_TYPE.ULYSSES.value},
            {AlienTypeComponent.ALIEN_TYPE.ULYSSES.value,AlienTypeComponent.ALIEN_TYPE.PSYCHOSQUID.value},
            
            //tank4

            {AlienTypeComponent.ALIEN_TYPE.BALROG.value},
            {AlienTypeComponent.ALIEN_TYPE.BILATERUS.value},
            {AlienTypeComponent.ALIEN_TYPE.GUS.value, AlienTypeComponent.ALIEN_TYPE.ULYSSES.value|AlienTypeComponent.ALIEN_TYPE.DESTRUCTOR.value},
            {AlienTypeComponent.ALIEN_TYPE.BILATERUS.value, AlienTypeComponent.ALIEN_TYPE.ULYSSES.value|AlienTypeComponent.ALIEN_TYPE.DESTRUCTOR.value},
            {AlienTypeComponent.ALIEN_TYPE.BILATERUS.value, AlienTypeComponent.ALIEN_TYPE.ULYSSES.value|AlienTypeComponent.ALIEN_TYPE.DESTRUCTOR.value,
                    AlienTypeComponent.ALIEN_TYPE.PSYCHOSQUID.value|AlienTypeComponent.ALIEN_TYPE.BALROG.value},
            
    };

    public LevelData(PlayerData playerData) {
        this.playerData = playerData;
        this.tankNumber = playerData.getUnlockedTankNumber();
        this.levelNumber = playerData.getUnlockedLevelNumber();
        this.variableSlots = variableSlotsData[(tankNumber * 5) + levelNumber];
        this.prices = pricesData[(tankNumber * 5) + levelNumber];
        this.alienData = aliensData[(tankNumber * 5) + levelNumber];

    }

    public Enum[] getVariableSlots(){
        return this.variableSlots;
    }

    public int[] getPrices(){
        return this.prices;
    }

    public int getTankNumber(){
        return this.tankNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public long[] getAlienData(){return alienData;};

    public ImageInfo.IMAGE_NAME getBackGroundName() {

        return backGroundImageNames[tankNumber];
    }
}
