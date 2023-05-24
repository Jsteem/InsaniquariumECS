package insaniquarium.ecs.components;

public class MovementComponent extends Component {
    public float x;
    public float y;
    public float vx;
    public float vy;
    public float ax;
    public float ay;
    public boolean overWrite = false;


    public MovementComponent(float x, float y, float vx, float vy, float ax, float ay){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;

    }


}
