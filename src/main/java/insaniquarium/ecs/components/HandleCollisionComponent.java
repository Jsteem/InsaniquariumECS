package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;
import insaniquarium.ecs.Entity;

public abstract class HandleCollisionComponent extends Component {

    public abstract  void handleCollision(Entity entity, long mask);
}
