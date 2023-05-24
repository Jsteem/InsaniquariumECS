package insaniquarium.ecs.factories.pets.tank1;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;
import insaniquarium.ecs.components.animationtypecomponents.IdleAnimation;
import insaniquarium.ecs.components.typecomponents.PetTypeComponent;
import insaniquarium.ecs.factories.Factory;
import insaniquarium.game.GameCanvas;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class Niko extends Factory {
    @Override
    public Entity createEntity(int x, int y, int level) {
        Entity niko = new Entity();
        HashMap<AnimationComponent.AnimationType, AnimationTypeComponent> animationComponents = new HashMap<>();
        animationComponents.put(
                AnimationComponent.AnimationType.IDLE,
                new IdleAnimation(ImageInfo.IMAGE_NAME.NIKO, AnimationComponent.AnimationType.IDLE, 0, 0.2, true, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.SPECIAL_MOVE_START,
                new IdleAnimation(ImageInfo.IMAGE_NAME.NIKO, AnimationComponent.AnimationType.SPECIAL_MOVE_START, 1, 0.07, false, 1));

        animationComponents.put(
                AnimationComponent.AnimationType.SPECIAL_MOVE_STOP,
                new IdleAnimation(ImageInfo.IMAGE_NAME.NIKO, AnimationComponent.AnimationType.SPECIAL_MOVE_STOP, 2, 0.07, false, 1));

        AnimationComponent animationComponent = new AnimationComponent(animationComponents);
        niko.addComponent(animationComponent);

        int boundingCircleRadius = 60;

        int tankLevel = GameCanvas.getTankNumber();
        int nikoX = 0;
        int nikoY = 0;
        switch(tankLevel){
            case 0 -> {
                nikoX = 133;
                nikoY = 293;
            }
            case 1 ->{
                nikoX = 215;
                nikoY = 200;
            }
            case 2 ->{
                nikoX = 185;
                nikoY = 300;
            }
            case 3 ->{
                nikoX = 105;
                nikoY = 225;
            }
        }

        niko.addComponent(new MovementComponent(nikoX, nikoY, 0, 0, 0, 0));

        niko.addComponent(new TargetComponent(PetTypeComponent.PET_TYPE.PET.value, -1));

        niko.addComponent(new SpecialMoveComponent(niko, 3000) {
            @Override
            public void doSpecialMove() {

                SpecialMoveActionComponent specialMoveActionComponent = entity.getComponent(SpecialMoveActionComponent.class);
                AnimationComponent animComponent = entity.getComponent(AnimationComponent.class);
                if(specialMoveActionComponent == null){
                    niko.addComponent(new BoundingCollisionComponent(boundingCircleRadius, boundingCircleRadius));
                    entity.addComponent(new ConsumedComponent());
                    animComponent.setLeftToRight();
                    animComponent.setActiveType(AnimationComponent.AnimationType.SPECIAL_MOVE_START);
                    entity.addComponent(new SpecialMoveActionComponent() {
                        @Override
                        public void handleSpecialMove() {
                            animationComponent.setActiveTypeSmooth(AnimationComponent.AnimationType.SPECIAL_MOVE_STOP);

                            ConsumedComponent consumedComponent = entity.getComponent(ConsumedComponent.class);
                            if(consumedComponent != null){
                                GameCanvas.increaseMoney( 200);
                                entity.removeComponent(ConsumedComponent.class);
                                entity.removeComponent(BoundingCollisionComponent.class);
                            }

                        }
                    });
                }
                else{
                    animComponent.setRightToLeft();
                    entity.removeComponent(SpecialMoveActionComponent.class);
                    entity.removeComponent(BoundingCollisionComponent.class);
                    animComponent.setNextActiveType(AnimationComponent.AnimationType.IDLE);
                }
            }
        });


        EntityManager.getInstance().addEntity(niko);
        return niko;
    }

    @Override
    public Entity createDisplayEntity(int x, int y) {
        return null;
    }
}
