package insaniquarium.ecs.systems;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;

import java.util.List;

public class AISystem extends System {
    @Override
    public void update(double delta) {
        List<Entity> entities = EntityManager.getInstance().getEntities();
        if (entities != null) {
            for (Entity entity : entities) {
                BehaviorComponent behaviorComponent = entity.getComponent(BehaviorComponent.class);
                if (behaviorComponent != null) {
                    behaviorComponent.update(delta);
                }
            }
        }
    }
}
