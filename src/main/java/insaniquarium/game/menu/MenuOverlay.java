package insaniquarium.game.menu;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.factories.FactoryManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.game.data.GameData;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static insaniquarium.utility.ImageInfo.IMAGE_NAME.*;

public class MenuOverlay {

    private static int[] OFFSET_BUTTON_X = {18, 87, 145, 218, 291, 364, 437};
    private static int OFFSET_BUTTON_Y = 4;

    private List<MenuButton> menuButtons;

    private Entity[] displayEntities = new Entity[7];

    private Entity[] displayLabels = new Entity[7];

    private static int[] DISPLAY_ENTITY_OFFSET_X = {47, 115, 169, 245, 320, 393, 467};

    private static int[] DISPLAY_ENTITY_OFFSET_Y = {25, 25, 13, 25, 25, 25, 25};

    private static int[] DISPLAY_ENTITY_LABEL_OFFSET_X = {48, 117, 175, 248, 323, 395, 468};

    private static int DISPLAY_ENTITY_LABEL_OFFSET_Y = 48;

    private static Entity moneyLabel = new Entity();
    private GameData gameData;

    public MenuOverlay(GameData gameData) {
        this.gameData = gameData;

        this.menuButtons = new ArrayList<>();
        Entity background = new Entity();
        RenderComponent backGroundComponent = new RenderComponent(MENU_BAR, 0, 0, 640, 70);
        background.addComponent(backGroundComponent);
        EntityManager.getInstance().addEntity(background);

        int[] prices = gameData.getPrices();
        for (int i = 0; i < prices.length; i++){
            int offsetX = OFFSET_BUTTON_X[i];
            if(prices[i] > 0){
                menuButtons.add(new MenuButton(this, offsetX, OFFSET_BUTTON_Y, i));
            }

        }

        generateDisplayEntities();
        generateDisplayLabels();
        generateTankInfo();
        generateMoneyLabel();

    }

    private void generateMoneyLabel() {
        TextComponent textComponent = new TextComponent(
                CONTBOLD12, TextComponent.POSITION.LEFT,
                "",
                610,40, 1.1f, Color.ORANGE);
        moneyLabel.addComponent(textComponent);
        EntityManager.getInstance().addEntity(moneyLabel);
    }

    private void generateTankInfo() {
        int[] tankInfo = gameData.getTankInfo();
        Entity tankInfoLabel = new Entity();
        TextComponent textComponent = new TextComponent(
                CONTBOLD12, TextComponent.POSITION.RIGHT,
                "Tank  " + (tankInfo[0] + 1) + " - " + (tankInfo[1] + 1),
                10,450, 1.1f, Color.ORANGE);
        tankInfoLabel.addComponent(textComponent);
        EntityManager.getInstance().addEntity(tankInfoLabel);
    }

    private void generateDisplayLabels() {
        int[] prices = gameData.getPrices();
        for (int i = 0; i < prices.length; i++) {
            int price = prices[i];
            if (price > 0) {
                Entity entity = new Entity();
                TextComponent textComponent = new TextComponent(
                        CONTBOLD12, TextComponent.POSITION.CENTER, "" + price,
                        DISPLAY_ENTITY_LABEL_OFFSET_X[i], DISPLAY_ENTITY_LABEL_OFFSET_Y, 0.75f, Color.ORANGE);
                entity.addComponent(textComponent);

                //BoundingCollisionComponent boundingCollisionComponent = new BoundingCollisionComponent(10);
                //MovementComponent movementComponent = new MovementComponent(28 +OFFSET_BUTTON_X[i], OFFSET_LABEL, 0, 0, 0, 0);
                //entity.addComponent(boundingCollisionComponent);
                //entity.addComponent(movementComponent);
                displayLabels[i] = entity;
                EntityManager.getInstance().addEntity(entity);
            }

        }


    }

    private void generateDisplayEntities() {
        Enum[] variableSlots = gameData.getSlotData();
        int[] prices = gameData.getPrices();
        int slotNr = -1;
        Entity displayEntity;

        //SLOT0 (variable)
        if (prices[++slotNr] != 0) {
            displayEntity = FactoryManager.getInstance().getFactory(variableSlots[slotNr])
                    .createDisplayEntity(DISPLAY_ENTITY_OFFSET_X[slotNr], DISPLAY_ENTITY_OFFSET_Y[slotNr]);
            displayEntities[slotNr] = displayEntity;
        }
        //SLOT1 (food type)
        if (prices[++slotNr] != 0) {
            displayEntity = FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD)
                    .createDisplayEntity(DISPLAY_ENTITY_OFFSET_X[slotNr], DISPLAY_ENTITY_OFFSET_Y[slotNr]);
            displayEntities[slotNr] = displayEntity;
        }
        //SLOT2 (food max amount)
        if (prices[++slotNr] != 0) {
            displayEntity = new Entity();
            TextComponent textComponent = new TextComponent(
                    CONTBOLD12, TextComponent.POSITION.CENTER, "" + 2,
                    DISPLAY_ENTITY_OFFSET_X[slotNr], DISPLAY_ENTITY_OFFSET_Y[slotNr], 1.3f, Color.ORANGE);
            displayEntity.addComponent(textComponent);
            displayEntities[slotNr] = displayEntity;
        }
        //SLOT3 (variable)
        if (prices[++slotNr] != 0) {
            displayEntity = FactoryManager.getInstance().getFactory(variableSlots[1])
                    .createDisplayEntity(DISPLAY_ENTITY_OFFSET_X[slotNr], DISPLAY_ENTITY_OFFSET_Y[slotNr]);
            displayEntities[slotNr] = displayEntity;
        }
        //SLOT4 (variable)
        if (prices[++slotNr] != 0) {
            displayEntity = FactoryManager.getInstance().getFactory(variableSlots[2])
                    .createDisplayEntity(DISPLAY_ENTITY_OFFSET_X[slotNr], DISPLAY_ENTITY_OFFSET_Y[slotNr]);
            displayEntities[slotNr] = displayEntity;
        }
        //SLOT5 (weapons)
        if (prices[++slotNr] != 0) {
            displayEntity = new Entity();
            RenderComponent renderComponent = new RenderComponent(
                    WEAPONS, DISPLAY_ENTITY_OFFSET_X[slotNr], DISPLAY_ENTITY_OFFSET_Y[slotNr], 0, 0, 1);
            displayEntity.addComponent(renderComponent);
            displayEntities[slotNr] = displayEntity;
        }
        //SLOT6 (egg)
        if (prices[++slotNr] != 0) {
            displayEntity = new Entity();
            RenderComponent renderComponent = new RenderComponent(
                    EGG, DISPLAY_ENTITY_OFFSET_X[slotNr], DISPLAY_ENTITY_OFFSET_Y[slotNr], 0, 0, 1);
            displayEntity.addComponent(renderComponent);
            displayEntities[slotNr] = displayEntity;
        }

        for (Entity entity : displayEntities) {
            if (entity != null) {
                EntityManager.getInstance().addEntity(entity);
            }
        }

    }

    public void handleMouseMoved(double x, double y) {
        for (MenuButton button : menuButtons) {
            if(button != null){
                button.handleMouseMoved(x, y);
            }
        }
    }

    public void handleMousePressed(double x, double y, boolean pressed) {
        for (MenuButton button : menuButtons) {
            if(button != null){
                button.handleMousePressed(x, y, pressed);
            }
        }
    }
    public void updateTotalMoneyAmount(int moneyAmount){
        TextComponent textComponent = moneyLabel.getComponent(TextComponent.class);
        if(textComponent != null){
            textComponent.buildText(""+moneyAmount);
        }
    }
    public void upgradeDisplayEntity(int slotNr) {
        Entity entity = displayEntities[slotNr];
        if (entity != null) {
            RenderComponent component = entity.getComponent(RenderComponent.class);
            TextComponent textComponent = entity.getComponent(TextComponent.class);
            if (component != null) {
                if (slotNr == 1) {
                    component.numRow++;
                    if (gameData.getTierFood() == 1) {


                        entity.removeComponent(component.getClass());
                        textComponent = displayLabels[slotNr].getComponent(TextComponent.class);
                        textComponent.x += 5;
                        textComponent.y += 1;
                        textComponent.percentage = 0.6f;
                        textComponent.buildText("MAX");
                    }
                } else {
                    component.numCol++;
                }
            }
            if (textComponent != null && slotNr == 2) {
                int number = gameData.getMaxAmountOfFood() + 1;
                textComponent.buildText("" + number);
                if (number == 10) {
                    entity.removeComponent(textComponent.getClass());
                    textComponent = displayLabels[slotNr].getComponent(TextComponent.class);
                    textComponent.x += 5;
                    textComponent.y += 1;
                    textComponent.percentage = 0.6f;
                    textComponent.buildText("MAX");

                }
            }
        }
    }

    public void handleButtonPress(int id) {
        gameData.handleButtonPress(id);
    }
}
