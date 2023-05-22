package insaniquarium.ecs.components.animationtypecomponents;

import insaniquarium.ecs.Component;

public class IdleRandomTargetPosition extends Component {
    public int x;
    public int y;

    public IdleRandomTargetPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
}
