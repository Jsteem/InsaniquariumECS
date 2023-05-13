package insaniquarium.game;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.SystemManager;
import insaniquarium.ecs.components.RenderComponent;
import insaniquarium.ecs.factories.GuppyFactory;
import insaniquarium.ecs.systems.*;
import insaniquarium.game.menu.MenuOverlay;
import insaniquarium.managers.RenderManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas {

    public static final int MENU_OFFSET_HEIGHT = 80;
    public static final int GROUND_OFFSET_HEIGHT = 40;
    public static final int SIDE_OFFSET = 20;

    MenuOverlay menuOverlay;

    public GameCanvas() {

        //initialize all the data
        PlayerData playerData = new PlayerData("testPlayer");
        LevelData levelData = new LevelData(playerData);
        GameData gameData = new GameData(levelData);

        //create the systems
        SystemManager.getInstance().registerSystem(new AnimationSystem());
        SystemManager.getInstance().registerSystem(new CollisionSystem());
        SystemManager.getInstance().registerSystem(new GrowthSystem());
        SystemManager.getInstance().registerSystem(new MovementSystem());
        SystemManager.getInstance().registerRenderSystem(new RenderSystem());

        //add the background
        Entity background = new Entity();
        RenderComponent backGroundComponent = new RenderComponent(levelData.getBackGroundName(), 0, 0, -1, -1);
        background.addComponent(backGroundComponent);
        EntityManager.getInstance().addEntity(background);

        //add a guppy
        GuppyFactory guppyFactory = new GuppyFactory();
        guppyFactory.createEntity(100,100, 0);

        menuOverlay = new MenuOverlay(gameData);

    }

    public void update(double timeStep) {

        SystemManager.getInstance().update(timeStep);
    }

    public void handleMouseMoved(double x, double y) {
        menuOverlay.handleMouseMoved(x, y);
    }

    public void handleMousePressed(double x, double y, boolean pressed) {
        menuOverlay.handleMousePressed(x, y, pressed);
    }


    public void render() {
        RenderManager.getInstance().draw(getGraphicsContext2D());
    }

}
