package insaniquarium.ecs.components;

public class BoundingCollisionComponent extends Component {

    public int boundingCollisionWidth;
    public int boundingCollisionHeight;

    public BoundingCollisionComponent(int boundingCollisionWidth, int boundingCollisionHeight){
        this.boundingCollisionWidth = boundingCollisionWidth;
        this.boundingCollisionHeight = boundingCollisionHeight;

    }
}
