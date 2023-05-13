package insaniquarium.game.menu;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.game.GameData;

import static insaniquarium.utility.ImageInfo.IMAGE_NAME.*;

public class MenuButton {
    Entity slot;

    boolean mouseEntered = false;

    int x;

    int y;

    int radius = 26;

    GameData gameData;

    private static int totalIds;

    private int id;

    public MenuButton(GameData gameData, int x, int y) {
        id = totalIds++;
        this.x = x + 29;
        this.y = y + 22;
        slot = new Entity();
        RenderComponent renderComponent = new RenderComponent(SLOT_BUTTON_UNPRESSED, x, y, 57, 60);
        MovementComponent movementComponent = new MovementComponent(this.x, this.y, 0, 0, 0, 0);
        BoundingCollisionComponent collisionComponent = new BoundingCollisionComponent(radius);
        slot.addComponent(renderComponent);
        slot.addComponent(collisionComponent);
        slot.addComponent(movementComponent);
        EntityManager.getInstance().addEntity(slot);
        this.gameData = gameData;

    }


    public void handleMousePressed(double x, double y, boolean pressed) {
        boolean collided = checkCollision(x, y);
        if (pressed) {
            if (collided) {
                slot.getComponent(RenderComponent.class).imageName = SLOT_BUTTON_PRESSED;
                gameData.handleButtonPress(id);

            } else {

                slot.getComponent(RenderComponent.class).imageName = SLOT_BUTTON_UNPRESSED;
            }
        } else {
            if (collided) {
                slot.getComponent(RenderComponent.class).imageName = SLOT_BUTTON_HOVERED;

            } else {

                slot.getComponent(RenderComponent.class).imageName = SLOT_BUTTON_UNPRESSED;
            }
        }

    }


    public void handleMouseMoved(double x, double y) {
        if (checkCollision(x, y)) {
            mouseEntered = true;

        } else {
            mouseEntered = false;

        }

        if (mouseEntered) {
            slot.getComponent(RenderComponent.class).imageName = SLOT_BUTTON_HOVERED;
        } else {
            slot.getComponent(RenderComponent.class).imageName = SLOT_BUTTON_UNPRESSED;
        }
    }

    public boolean checkCollision(double x, double y) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return (dist < radius);
    }
}
