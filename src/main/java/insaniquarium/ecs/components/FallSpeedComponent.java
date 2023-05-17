package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class FallSpeedComponent extends Component {
    public int vy;

    public int ay;

    public FallSpeedComponent( int vy, int ay){
        this.vy = vy;
        this.ay = ay;
    }
}
