package insaniquarium.ecs.systems;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.managers.ResourceManager;
import insaniquarium.utility.ImageInfo;

import java.util.List;

public class AnimationSystem extends System {
    public AnimationSystem() {
        super();
    }

    @Override
    public void update(double delta) {
        List<Entity> entities = EntityManager.getInstance().getEntities();
        for(Entity entity : entities){
            AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
            if(animationComponent != null){
                if(animationComponent.update){
                    int maxCols = ResourceManager.getInstance().getImageInfo(animationComponent.activeType.spriteName).numCols;
                    if(animationComponent.timeSinceLastUpdateAnimation >= animationComponent.activeType.animationSpeed){
                        if(++animationComponent.frameNr > maxCols - 1){
                            animationComponent.animationComplete = true;
                            if(animationComponent.activeType.loop){
                                animationComponent.frameNr = 0;
                            }
                            else{
                                animationComponent.frameNr = maxCols - 1;
                                animationComponent.update = false;
                            }
                        }
                        animationComponent.timeSinceLastUpdateAnimation -= animationComponent.activeType.animationSpeed;
                    }
                    animationComponent.timeSinceLastUpdateAnimation += delta;
                }
            }
        }
    }
}
