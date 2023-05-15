package insaniquarium.game.data;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.components.RenderComponent;
import insaniquarium.ecs.components.TargetComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.game.GameCanvas;
import insaniquarium.game.menu.MenuOverlay;
import insaniquarium.utility.ImageInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameData {
    private int tierFood = 0;
    private int maxFood = 1;

    private int numFood = 0;

    private int foodPrice = 5;
    private int laserUpgrade = 0;
    private int eggPiece = 0;
    private int totalAmountOfMoney = 2000000;
    private int currentButtonUnlocked = 0;
    Enum[] slotData;
    int[] priceData;

    long[] alienData;

    private LevelData levelData;

    private GameCanvas gameCanvas;

    private List<Entity> specialFoodEntities = new ArrayList<>();

    private MenuOverlay menuOverlay;

    public GameData(GameCanvas canvas, PlayerData playerData){

        this.gameCanvas = canvas;
        levelData = new LevelData(playerData);

        //add the background
        Entity background = new Entity();
        RenderComponent backGroundComponent = new RenderComponent(levelData.getBackGroundName(), 0, 0, -1, -1);
        background.addComponent(backGroundComponent);
        EntityManager.getInstance().addEntity(background);


        priceData = levelData.getPrices();
        slotData = levelData.getVariableSlots();
        alienData = levelData.getAlienData();
        menuOverlay = new MenuOverlay(this);
        menuOverlay.updateTotalMoneyAmount(totalAmountOfMoney);
    }
    public List<Entity> getSpecialFoodEntities(){
        return this.specialFoodEntities;
    }

    public int getMaxAmountOfFood() {
        return maxFood;
    }

    public void increaseMoney(int money){
        this.totalAmountOfMoney += money;

    }

    public void handleButtonPress(int id) {
        int buyPrice = priceData[id];
        if(totalAmountOfMoney >= buyPrice){
            switch (id){
                case 0 -> {
                    FactoryManager.getInstance().getFactory(slotData[0]).createEntity(100,200,0);
                    buy(buyPrice);
                }
                case 1 -> {
                    if(this.tierFood < 2){
                        menuOverlay.upgradeDisplayEntity(1);
                        this.tierFood++;
                        buy(buyPrice);
                    }
                }
                case 2 -> {
                    if(this.maxFood < 9) {
                        this.maxFood++;
                        menuOverlay.upgradeDisplayEntity(2);
                        buy(buyPrice);
                    }
                }
                case 3 -> {
                    Entity entity = FactoryManager.getInstance().getFactory(slotData[1]).createEntity(100,400,0);
                    if(entity != null){
                        TargetComponent targetComponent = entity.getComponent(TargetComponent.class);
                        if((targetComponent.maskEntity & FoodTypeComponent.FOOD_TYPE.FOOD.value) > 0){
                            specialFoodEntities.add(entity);
                        }
                    }
                    buy(buyPrice);
                    if(currentButtonUnlocked < 4){
                        unlockNextButton();
                    }
                }
                case 4 -> {
                    FactoryManager.getInstance().getFactory(slotData[2]).createEntity(100,200,0);
                    buy(buyPrice);
                    if(currentButtonUnlocked < 5){
                        unlockNextButton();
                    }
                }
                case 5 -> {
                    if(this.laserUpgrade < 9){
                        this.laserUpgrade++;
                        buy(buyPrice);
                        menuOverlay.upgradeDisplayEntity(5);
                    }

                }

                case 6 -> {
                    if(this.eggPiece < 2) {
                        this.eggPiece++;
                        buy(buyPrice);
                        menuOverlay.upgradeDisplayEntity(6);
                    }
                    else{
                        gameCanvas.onFinishLevel();
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
        menuOverlay.updateTotalMoneyAmount(this.totalAmountOfMoney);
    }
    public void unlockNextButton() {
        int[] prices = levelData.getPrices();
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
    public int getTierFood(){
        return tierFood;
    }
    public Enum[] getSlotData(){
        return this.slotData;
    }

    public int[] getPrices() {
        return this.priceData;
    }

    public int getTotalAmountMoney() {
        return this.totalAmountOfMoney;
    }

    public int[] getTankInfo() {
        return new int[]{levelData.getTankNumber(), levelData.getLevelNumber()};
    }

    public ImageInfo.IMAGE_NAME getBackGroundName() {
        return levelData.getBackGroundName();
    }

    public void handleMouseMoved(double x, double y) {
        menuOverlay.handleMouseMoved(x, y);
    }

    public void handleMousePressed(double x, double y, boolean pressed) {
        menuOverlay.handleMousePressed(x, y, pressed);
    }
}
