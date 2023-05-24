package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.ecs.components.Component;

public class IdleRandomTargetPosition extends Component {
    public int x;
    public int y;
    public boolean onlyIdle;
    public IdleRandomTargetPosition(int x, int y, boolean onlyIdle){
        this.x = x;
        this.y = y;
        this.onlyIdle = onlyIdle;
    }


}
