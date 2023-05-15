package insaniquarium.ecs.components.handlecollisioncomponents;

import insaniquarium.ecs.Component;
import insaniquarium.ecs.Entity;

public abstract class HandleNoCollision extends Component {

    public HandleNoCollision(){

    }
    public abstract void handleNoCollision(Entity clickEntity, float x, float y);
}
