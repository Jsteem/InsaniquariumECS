package insaniquarium.game;

import insaniquarium.Main;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.FactoryManager;
import insaniquarium.ecs.SystemManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleNoCollision;
import insaniquarium.ecs.components.typecomponents.AlienTypeComponent;
import insaniquarium.ecs.components.typecomponents.ClickTypeComponent;
import insaniquarium.ecs.components.typecomponents.CoinTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.ecs.systems.*;
import insaniquarium.game.data.GameData;
import insaniquarium.game.data.PlayerData;
import insaniquarium.managers.RenderManager;
import javafx.scene.canvas.Canvas;

import java.util.List;

public class GameCanvas extends Canvas {

    public static final int GROUND_OFFSET_HEIGHT = 40;
    public static final int SIDE_OFFSET = 20;

    PlayerData playerData = new PlayerData("testPlayer");
    GameData gameData;

    private void init() {


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
        SystemManager.getInstance().registerSystem(new MovementSystem());
        SystemManager.getInstance().registerSystem(new AISystem());
        SystemManager.getInstance().registerRenderSystem(new RenderSystem());

        init();

    }


    public void update(double timeStep) {

        SystemManager.getInstance().update(timeStep);
    }

    public void handleMouseMoved(double x, double y) {
        gameData.handleMouseMoved(x, y);


    }

    public void handleMousePressed(double x, double y, boolean pressed) {
        gameData.handleMousePressed(x, y, pressed);

        if (y > Main.MENU_OFFSET && pressed) {
            Entity click = new Entity();
            MovementComponent movementComponent = new MovementComponent((float) x, (float) y, 0, 0, 0, 0);
            TargetComponent targetComponent = new TargetComponent(ClickTypeComponent.CLICK_TYPE.CLICK.value,
                    AlienTypeComponent.ALIEN_TYPE.ALIEN.value | CoinTypeComponent.COIN_TYPE.COLLECTABLE.value);
            HandleNoCollision handleNoCollision = new HandleNoCollision() {
                @Override
                public void handleNoCollision(Entity clickEntity, float x, float y) {
                    List<Entity> specialFoodEntities = gameData.getSpecialFoodEntities();
                    Entity foodEntity = null;
                    if (!specialFoodEntities.isEmpty()) {
                        foodEntity = specialFoodEntities.get(0);
                        MovementComponent movementComponent = foodEntity.getComponent(MovementComponent.class);
                        movementComponent.x = x;
                        movementComponent.y = y;
                        specialFoodEntities.remove(0);
                    } else if (gameData.subtractFromTotalAmountOfMoney(5)) {
                        foodEntity = FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity((int) x, (int) y, gameData.getTierFood());

                        //DEBUG - spawn a star:
                        //foodEntity = FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE).createEntity((int) x, (int) y,2);

                        //DEBUG - spawn a beetle:
                        //foodEntity = FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE).createEntity((int) x, (int) y,5);


                    }
                    EntityManager.getInstance().addEntity(foodEntity);
                }
            };
            click.addComponent(new HandleCollisionComponent() {
                @Override
                public void handleCollision(Entity entity, Entity target, long mask) {

                    GrowthComponent growthComponent = target.getComponent(GrowthComponent.class);
                    if(growthComponent != null){
                        int money = 0;
                        int level = growthComponent.growthLevel;
                        switch (level) {
                            case 0 -> {
                                money = 15;
                                //SoundManager.getInstance().playSound("POINTS.ogg");
                            }
                            case 1 -> {
                                money = 25;
                                //SoundManager.getInstance().playSound("POINTS.ogg");
                            }
                            case 2 -> {
                                money = 40;
                                //SoundManager.getInstance().playSound("POINTS.ogg");
                            }
                            case 3 -> {
                                money = 200;
                                //SoundManager.getInstance().playSound("diamond.ogg");
                            }
                            case 4 -> {
                                money = 2000;
                                //SoundManager.getInstance().playSound("TREASURE.ogg");
                            }
                            case 5 -> {
                                money = 30;
                                //SoundManager.getInstance().playSound("POINTS.ogg");
                            }
                        }
                        gameData.addToTotalAmountOfMoney(money);
                    }
                    EntityManager.getInstance().removeEntity(target);
                }
            });
            click.addComponent(handleNoCollision);
            click.addComponent(targetComponent);
            click.addComponent(movementComponent);
            EntityManager.getInstance().addEntity(click);
        }
    }


    public void render() {
        RenderManager.getInstance().draw(getGraphicsContext2D());
    }

}
