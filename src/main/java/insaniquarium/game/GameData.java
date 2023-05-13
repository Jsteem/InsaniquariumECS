package insaniquarium.game;

import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.typecomponents.AlienTypeComponent;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.factories.Factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameData {
    private static int tierFood = 0;
    private int maxFood = 100;
    private int numFood = 0;
    private int foodPrice = 5;
    private int laserUpgrade = 0;
    private int eggPiece = 0;
    private int totalAmountOfMoney = 20000;
    private int currentButtonUnlocked = 0;
    private LevelData levelData;

    FishTypeComponent.FISH_TYPE[] fishData;
    int[] priceData;

    long[] alienData;

    public GameData(LevelData levelData){
        this.levelData = levelData;

        fishData = levelData.getVariableSlots();
        alienData = levelData.getAlienData();
        priceData = levelData.getPrices();

    }

    public void increaseMoney(int money){
        this.totalAmountOfMoney += money;

    }


    public void handleButtonPress(int id) {
        int buyPrice = priceData[id];
        if(totalAmountOfMoney >= buyPrice){
            switch (id){
                case 0 -> {
                    FactoryManager.getInstance().getFactory(fishData[0]).createEntity(100,200,0);
                    buy(buyPrice);
                }
                case 1 -> {
                    if(this.tierFood < 2){
                        this.tierFood++;
                        buy(buyPrice);
                    }
                }
                case 2 -> {
                    if(this.maxFood < 10) {
                        this.maxFood++;
                        buy(buyPrice);
                    }
                }
                case 3 -> {
                    FactoryManager.getInstance().getFactory(fishData[1]).createEntity(100,400,0);
                    buy(buyPrice);
                    if(currentButtonUnlocked < 4){
                        unlockNextButton();
                    }
                }
                case 4 -> {
                    FactoryManager.getInstance().getFactory(fishData[2]).createEntity(100,200,0);
                    buy(buyPrice);
                    if(currentButtonUnlocked < 5){
                        unlockNextButton();
                    }
                }
                case 5 -> {
                    this.laserUpgrade++;
                    buy(buyPrice);
                }

                case 6 -> {
                    if(this.eggPiece < 2) {
                        this.eggPiece++;
                        buy(buyPrice);
                    }
                    else{
                        System.out.println("Level Completed!");
                    }
                }
            }

        }
        else{
            //todo: not enough money flash + sound
        }
    }

    public void buy(int price){
        this.totalAmountOfMoney -= price;
    }
    public void unlockNextButton() {
        int[] prices = this.levelData.getPrices();
        if (currentButtonUnlocked < 6) {
            //last button not unlocked, check the next slot that should be unlocked.
            //note that the buttons that should be unlocked are the buttons with prices != 0
            int nextButton = currentButtonUnlocked + 1;
            while (nextButton < 7 && prices[nextButton] == 0) {
                nextButton++;
            }

            currentButtonUnlocked = nextButton;

            //todo: overlay unlocks the button and animation

            //slot2 unlocks slot3 and 4, same for slot6 and 7
            if (currentButtonUnlocked == 1 || currentButtonUnlocked == 2
                    || currentButtonUnlocked == 5) {
                unlockNextButton();
            }
        }
    }
    public static int getTierFood(){
        return tierFood;
    }
}
