package insaniquarium.ecs.components.animationtypecomponents;

import insaniquarium.ecs.Component;

public class IdlePosition extends Component {
    public int x;
    public int y;

    public IdlePosition(int x, int y){
        this.x = x;
        this.y = y;
    }
}
