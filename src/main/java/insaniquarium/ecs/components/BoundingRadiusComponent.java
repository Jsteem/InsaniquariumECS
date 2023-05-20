package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class BoundingRadiusComponent extends Component {

    public int boundingCollisionRadius;


    public BoundingRadiusComponent(int boundingCollisionRadius){
        this.boundingCollisionRadius = boundingCollisionRadius;


    }

}
