package insaniquarium.ecs.components.handlecollisioncomponents;

import insaniquarium.ecs.components.Component;
import insaniquarium.ecs.Entity;

public abstract class HandleCollisionComponent extends Component {

    public abstract  void handleCollision(Entity entity, Entity target, long mask);
}
