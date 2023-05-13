package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class EatCollisionComponent extends Component {
    public int eatCollisionRadius;
    public float eatCollisionOffsetX;
    public float eatCollisionOffsetY;

    public EatCollisionComponent(int eatCollisionRadius, float eatCollisionOffsetX, float eatCollisionOffsetY){
        this.eatCollisionRadius = eatCollisionRadius;
        this.eatCollisionOffsetX = eatCollisionOffsetX;
        this.eatCollisionOffsetY = eatCollisionOffsetY;
    }

}
