package insaniquarium.ecs.systems;

import insaniquarium.Main;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.System;
import insaniquarium.ecs.components.*;
import insaniquarium.ecs.components.animationtypes.TurnAnimation;
import insaniquarium.ecs.components.animationtypes.TurnHungryAnimation;
import insaniquarium.managers.RenderManager;
import insaniquarium.managers.drawrequest.CircleShape;
import insaniquarium.managers.drawrequest.PlainImage;
import insaniquarium.managers.drawrequest.Sprite;
import javafx.scene.paint.Color;

import java.util.List;


public class RenderSystem extends System {


    public RenderSystem() {
        super();
    }

    @Override
    public void update(double delta) {
    }

    public void update() {
        List<Entity> entities = EntityManager.getInstance().getEntities();
        if (entities != null) {

            for (Entity entity : entities) {
                AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
                MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
                BehaviorComponent behaviorComponent = entity.getComponent(BehaviorComponent.class);
                RenderComponent renderComponent = entity.getComponent(RenderComponent.class);

                if (renderComponent != null) {
                    PlainImage image = new PlainImage(renderComponent.imageName, renderComponent.x, renderComponent.y, renderComponent.width, renderComponent.height);
                    RenderManager.getInstance().submitDrawRequest(image);
                }
                if (animationComponent != null) {

                    float sourceX = 0;
                    float sourceY = 0;
                    int rowNr = animationComponent.activeType.rowNr;
                    int columnNr = animationComponent.frameNr;
                    boolean reverse = false;

                    if (movementComponent != null) {
                        sourceX = movementComponent.x;
                        sourceY = movementComponent.y;
                        reverse = movementComponent.vx < 0;
                    }

                    if (animationComponent.activeType.type == AnimationComponent.AnimationType.TURN ||
                            animationComponent.activeType.type == AnimationComponent.AnimationType.HUNGRY_TURN) {
                        reverse = !reverse;
                    }
                    RenderManager.getInstance().submitDrawRequest(new Sprite(animationComponent.activeType.spriteName, (int) sourceX, (int) sourceY, rowNr, columnNr, reverse));

                }
                if (Main.DEBUG) {
                    BoundingCollisionComponent boundingCollisionComponent = entity.getComponent(BoundingCollisionComponent.class);
                    if (boundingCollisionComponent != null && movementComponent != null) {
                        RenderManager.getInstance().submitDrawRequest(
                                new CircleShape(2, Color.YELLOW, boundingCollisionComponent.boundingCollisionRadius, movementComponent.x, movementComponent.y));
                    }

                    EatCollisionComponent eatCollisionComponent = entity.getComponent(EatCollisionComponent.class);
                    if (eatCollisionComponent != null && movementComponent != null) {
                        float offsetY = movementComponent.y + eatCollisionComponent.eatCollisionOffsetY;
                        float offsetX = movementComponent.x + (movementComponent.vx > 0 ? eatCollisionComponent.eatCollisionOffsetX : -eatCollisionComponent.eatCollisionOffsetX);
                        RenderManager.getInstance().submitDrawRequest(
                                new CircleShape(2, Color.GREEN, eatCollisionComponent.eatCollisionRadius, offsetX, offsetY));
                    }

                }

            }
        }
    }

}
