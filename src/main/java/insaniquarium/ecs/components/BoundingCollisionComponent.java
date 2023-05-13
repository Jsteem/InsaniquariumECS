package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class BoundingCollisionComponent extends Component {

    public int boundingCollisionRadius;

    public BoundingCollisionComponent(int boundingCollisionRadius){
        this.boundingCollisionRadius = boundingCollisionRadius;

    }

}
