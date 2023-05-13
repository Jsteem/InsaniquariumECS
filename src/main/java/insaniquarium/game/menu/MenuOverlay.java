package insaniquarium.game.menu;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.RenderComponent;
import insaniquarium.ecs.components.typecomponents.ClickTypeComponent;
import insaniquarium.game.GameData;
import insaniquarium.utility.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import static insaniquarium.utility.ImageInfo.IMAGE_NAME.*;

public class MenuOverlay {

    private static int[] OFFSET_BUTTON_X = {18, 87 , 145, 218, 291, 364, 437};
    private static int OFFSET_BUTTON_Y = 4;

    private List<MenuButton> menuButtons = new ArrayList();
    public MenuOverlay(GameData data){
        Entity background = new Entity();
        RenderComponent backGroundComponent = new RenderComponent(MENU_BAR,0,0, 640, 70);
        background.addComponent(backGroundComponent);
        EntityManager.getInstance().addEntity(background);



        for(int offsetX : OFFSET_BUTTON_X){
            menuButtons.add(new MenuButton(data, offsetX,OFFSET_BUTTON_Y));
        }
    }

    public void handleMouseMoved(double x, double y){
        for(MenuButton button: menuButtons){
            button.handleMouseMoved(x, y);
        }
    }
    public void handleMousePressed(double x, double y, boolean pressed){
        for(MenuButton button: menuButtons){
            button.handleMousePressed(x, y, pressed);
        }
    }

}
