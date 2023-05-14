package insaniquarium.game;

import insaniquarium.Main;
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

    public static final int GROUND_OFFSET_HEIGHT = 40;
    public static final int SIDE_OFFSET = 20;

    static MenuOverlay menuOverlay;

    PlayerData playerData = new PlayerData("testPlayer");
    GameData gameData;

    private void init(){


        gameData = new GameData(this, playerData);

    }
    public void onFinishLevel() {
        playerData.increaseLevel();
        EntityManager.getInstance().reset();
        init();
    }

    public GameCanvas() {

        //create the systems
        SystemManager.getInstance().registerSystem(new AnimationSystem());
        SystemManager.getInstance().registerSystem(new CollisionSystem());
        SystemManager.getInstance().registerSystem(new GrowthSystem());
        SystemManager.getInstance().registerSystem(new MovementSystem());
        SystemManager.getInstance().registerRenderSystem(new RenderSystem());

        init();

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
