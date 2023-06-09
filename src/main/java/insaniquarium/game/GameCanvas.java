package insaniquarium.game;

import insaniquarium.Main;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.typecomponents.*;
import insaniquarium.ecs.factories.FactoryManager;
import insaniquarium.ecs.systems.SystemManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleNoCollision;
import insaniquarium.ecs.systems.*;
import insaniquarium.game.data.GameData;
import insaniquarium.game.data.PlayerData;
import insaniquarium.managers.RenderManager;
import javafx.scene.canvas.Canvas;

import java.util.List;

public class GameCanvas extends Canvas {

    public static final int GROUND_OFFSET_HEIGHT = 40;
    public static final int SIDE_OFFSET = 20;

    static PlayerData playerData = new PlayerData("testPlayer");
    static GameData gameData;

    private void init() {

        gameData = new GameData(this, playerData);
        initPets();
    }

    public void initPets(){
        FactoryManager.getInstance().getFactory(PetTypeComponent.PET_TYPE.STINKY).createEntity(500,500,0);
        FactoryManager.getInstance().getFactory(PetTypeComponent.PET_TYPE.NIKO).createEntity(0,0,0);
        FactoryManager.getInstance().getFactory(PetTypeComponent.PET_TYPE.ZORF).createEntity(0,0,0);
        FactoryManager.getInstance().getFactory(PetTypeComponent.PET_TYPE.VERT).createEntity(0,0,0);
        //FactoryManager.getInstance().getFactory(PetTypeComponent.PET_TYPE.CLYDE).createEntity(0,0,0);
        //FactoryManager.getInstance().getFactory(PetTypeComponent.PET_TYPE.MERYL).createEntity(0,0,0);

        FactoryManager.getInstance().getFactory(AlienTypeComponent.ALIEN_TYPE.SYLVESTER).createEntity(500,500,0);
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
                    AlienTypeComponent.ALIEN_TYPE.ALIEN.value | CoinTypeComponent.COIN_TYPE.COLLECTABLE.value |
                            CoinTypeComponent.COIN_TYPE.BEETLE.value | CoinTypeComponent.COIN_TYPE.STAR.value | PetTypeComponent.PET_TYPE.PET.value);


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
                    } else if (gameData.subtractFromTotalAmountOfMoney(gameData.getFoodPrice())) {
                        //foodEntity = FactoryManager.getInstance().getFactory(FoodTypeComponent.FOOD_TYPE.FOOD).createEntity((int) x, (int) y, gameData.getTierFood());

                        //DEBUG - spawn a star:
                        foodEntity = FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE).createEntity((int) x, (int) y,2);

                        //DEBUG - spawn a beetle:
                        //foodEntity = FactoryManager.getInstance().getFactory(CoinTypeComponent.COIN_TYPE.COLLECTABLE).createEntity((int) x, (int) y,5);


                    }
                    EntityManager.getInstance().addEntity(foodEntity);
                }
            };
            click.addComponent(new HandleCollisionComponent() {
                @Override
                public void handleCollision(Entity entity, Entity target, long mask) {

                    MoneyValueComponent moneyValueComponent = target.getComponent(MoneyValueComponent.class);
                    if(moneyValueComponent != null){
                        gameData.addToTotalAmountOfMoney(moneyValueComponent.moneyValue);
                        EntityManager.getInstance().removeEntity(target);
                    }
                    SpecialMoveActionComponent specialMoveActionComponent = target.getComponent(SpecialMoveActionComponent.class);
                    if(specialMoveActionComponent != null){
                        specialMoveActionComponent.handleSpecialMove();
                    }
                    AttackComponent attackComponent = target.getComponent(AttackComponent.class);
                    if (attackComponent != null) {
                        attackComponent.handleAttack((int)x,(int)y, gameData.getWeaponTier());

                    }

                }
            });
            click.addComponent(handleNoCollision);
            click.addComponent(targetComponent);
            click.addComponent(movementComponent);
            click.addComponent(new BoundingCollisionComponent(3, 3));
            EntityManager.getInstance().addEntity(click);
        }
    }


    public void render() {
        RenderManager.getInstance().draw(getGraphicsContext2D());
    }

    public static void increaseMoney(int amount){
        gameData.addToTotalAmountOfMoney(amount);
    }

    public static int getTankNumber(){
        return playerData.getUnlockedTankNumber();
    }

}
