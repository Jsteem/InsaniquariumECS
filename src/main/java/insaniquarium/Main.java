package insaniquarium;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.typecomponents.AlienTypeComponent;
import insaniquarium.ecs.components.typecomponents.ClickTypeComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.game.GameCanvas;
import insaniquarium.managers.ResourceManager;
import insaniquarium.utility.ImageInfo;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static insaniquarium.utility.ImageInfo.IMAGE_NAME.CONTBOLD12;


public class Main extends Application {
    public static final int SIDE_OFFSET = 20;
    public static final int GROUND_OFFSET_HEIGHT = 30;
    private GameCanvas canvas;
    public static final int WIDTH = 640;
    public static final int MENU_OFFSET = 70;
    public static final int HEIGHT = 480;


    public static final boolean DEBUG = true;

    public static final boolean SOUND_ON = true;

    private final static double TIME_STEP = 1.0 / 60.0;
    private static double accumulatedTime = 0.0;
    private static long lastTime = 0;

    private static long inGameTime = 0;
    private static int frameCount = 0;
    private static double elapsedTime = 0.0;
    private static double fps;


    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);

        canvas = new GameCanvas();
        canvas.setWidth(WIDTH);
        canvas.setHeight(HEIGHT);

        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_LEFT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


        primaryStage.setOnCloseRequest(event -> {
            //SoundManager.getInstance().shutDown();
        });

        root.setOnMousePressed(event -> {
            // Get the X and Y coordinates of the mouse click event
            double x = event.getSceneX();
            double y = event.getSceneY();


            canvas.handleMousePressed(x, y, true);


        });

        root.setOnMouseReleased(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();

            canvas.handleMousePressed(x, y, false);

        });

        root.setOnMouseMoved(event -> {


            // Get the X and Y coordinates of the mouse click event
            double x = event.getSceneX();
            double y = event.getSceneY();
            canvas.handleMouseMoved(x, y);

     /*       Entity click = new Entity();
            MovementComponent movementComponent = new MovementComponent((float) x, (float) y, 0, 0, 0, 0);
            BoundingCollisionComponent collisionComponent = new BoundingCollisionComponent(30);
            TargetComponent targetComponent = new TargetComponent(ClickTypeComponent.CLICK_TYPE.CLICK.value,
                    AlienTypeComponent.ALIEN_TYPE.ALIEN.value | CoinTypeComponent.COIN_TYPE.COLLECTABLE.value);
            click.addComponent(targetComponent);
            click.addComponent(movementComponent);
            click.addComponent(collisionComponent);
            EntityManager.getInstance().addEntity(click);*/
        });

        Entity fpsDisplay = new Entity();
        TextComponent textComponent = new TextComponent(
                CONTBOLD12, TextComponent.POSITION.LEFT, "",
                630,460, 0.8f, Color.ORANGE);
        fpsDisplay.addComponent(textComponent);
        EntityManager.getInstance().addEntity(fpsDisplay);

        new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if (lastTime == 0) {
                    lastTime = currentTime;
                }

                double deltaTime = (currentTime - lastTime) / 1e9;

                accumulatedTime += deltaTime;

                while (accumulatedTime >= TIME_STEP) {
                    //update in-game time
                    inGameTime += TIME_STEP;

                    // code to update the game state
                    canvas.update(TIME_STEP);

                    accumulatedTime -= TIME_STEP;
                }


                // code to render the game
                canvas.render();


                lastTime = currentTime;

                //display fps
                frameCount++;
                elapsedTime += deltaTime;
                if (elapsedTime >= 1.0) {
                    fps = frameCount / elapsedTime;
                    frameCount = 0;
                    elapsedTime = 0.0;
                }
                fpsDisplay.getComponent(TextComponent.class).buildText("FPS: "+String.format("%.1f", fps));
            }
        }.start();
    }
}
