package insaniquarium.ecs.factories;


import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypes.*;
import insaniquarium.ecs.components.typecomponents.FishTypeComponent;
import insaniquarium.ecs.components.typecomponents.FoodTypeComponent;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class GuppyFactory extends Factory{

    public GuppyFactory(){

    }

    @Override
    public void createEntity(int x, int y, int level) {
        Entity guppy = new Entity();

        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_SWIM, AnimationComponent.AnimationType.IDLE, level, 0.07, true));

        animationComponents.put(
                AnimationComponent.AnimationType.TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_TURN, AnimationComponent.AnimationType.TURN, level, 0.07, false));

        animationComponents.put(
                AnimationComponent.AnimationType.EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_EAT, AnimationComponent.AnimationType.EAT, level, 0.07, false));

        animationComponents.put(
                AnimationComponent.AnimationType.DIE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.SMALL_DIE, AnimationComponent.AnimationType.DIE, level, 0.07, false));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_SWIM, AnimationComponent.AnimationType.HUNGRY_IDLE, level, 0.07, true));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_TURN,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_TURN, AnimationComponent.AnimationType.HUNGRY_TURN, level, 0.07, false));

        animationComponents.put(
                AnimationComponent.AnimationType.HUNGRY_EAT,
                new IdleAnimation(ImageInfo.IMAGE_NAME.HUNGRY_EAT, AnimationComponent.AnimationType.HUNGRY_EAT, level, 0.07, false));


        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        guppy.addComponent(animationComponent);


        int boundingCircleRadius = 20;

        guppy.addComponent(new MovementComponent(x, y, 0, 0, 0, 0));
        guppy.addComponent(new GrowthComponent(level));

        guppy.addComponent(new BoundingCollisionComponent(boundingCircleRadius));
        guppy.addComponent(new EatCollisionComponent(8, 10,0 ));
        guppy.addComponent(new TargetComponent(FishTypeComponent.FISH_TYPE.GUPPY_SMALL.value, FoodTypeComponent.FOOD_TYPE.FOOD.value));

        EntityManager.getInstance().addEntity(guppy);
    }
}


     /*   boolean createRandom = true;
        if(createRandom){

            int minWidth = GameCanvas.SIDE_OFFSET + boundingCircleRadius;
            int maxWidth = Main.WIDTH - GameCanvas.SIDE_OFFSET - boundingCircleRadius;
            int maxHeight = Main.HEIGHT - boundingCircleRadius - GameCanvas.GROUND_OFFSET_HEIGHT;
            int minHeight = GameCanvas.MENU_OFFSET_HEIGHT + boundingCircleRadius;

            x = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
            y = (int) (Math.random() * (maxHeight - minHeight)) + minHeight;

        }*/