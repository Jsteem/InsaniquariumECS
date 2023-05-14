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
import insaniquarium.managers.drawrequest.Font;
import insaniquarium.managers.drawrequest.PlainImage;
import insaniquarium.managers.drawrequest.Sprite;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
                RenderComponent renderComponent = entity.getComponent(RenderComponent.class);
                TextComponent textComponent = entity.getComponent(TextComponent.class);

                if (renderComponent != null) {
                    if (renderComponent.numRow >= 0 && renderComponent.numCol >= 0) {
                        Sprite sprite = new Sprite(
                                renderComponent.imageName, renderComponent.x, renderComponent.y,
                                renderComponent.numRow, renderComponent.numCol, false, renderComponent.scale);
                        RenderManager.getInstance().submitDrawRequest(sprite);
                    } else {
                        PlainImage image = new PlainImage(renderComponent.imageName, renderComponent.x, renderComponent.y, renderComponent.width, renderComponent.height);
                        RenderManager.getInstance().submitDrawRequest(image);
                    }

                }
                if (animationComponent != null) {

                    float sourceX = 0;
                    float sourceY = 0;
                    int rowNr = animationComponent.activeType.rowNr;
                    int columnNr = animationComponent.frameNr;
                    double scale = animationComponent.activeType.scale;
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
                    RenderManager.getInstance().submitDrawRequest(new Sprite(animationComponent.activeType.spriteName,
                            (int) sourceX, (int) sourceY, rowNr, columnNr, reverse, scale));

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
                if (textComponent != null) {
                    char[] characters = textComponent.charArray;
                    Map<Character, int[]> characterToData = textComponent.characterToData;
                    if(characters != null && characterToData != null){
                        for (int i = 0; i < characters.length; i++) {
                            char c = characters[i];
                            int[] data = characterToData.get(c);
                            if(data != null){
                                double offsetX = textComponent.offsetsX.get(i);
                                RenderManager.getInstance().submitDrawRequest(
                                        new Font(textComponent.fontImage, data[0], data[1], data[2], data[3],
                                                (int) offsetX, (int) textComponent.y, (int) (data[2] * textComponent.percentage), (int) (data[3] * textComponent.percentage)));

                            }

                        }
                    }

                }
            }
        }
    }
}
