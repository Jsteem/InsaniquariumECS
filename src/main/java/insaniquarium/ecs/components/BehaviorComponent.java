package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class BehaviorComponent extends Component {
    public BEHAVIOR_TYPE behaviorType;
    public enum BEHAVIOR_TYPE{
        IDLE, SEEK_HUNGRY, SEEK_SICK, EAT_HUNGRY, EAT_SICK, FALL_DOWN
    }
    public BehaviorComponent(BEHAVIOR_TYPE behaviorType){
        this.behaviorType = behaviorType;
    }
}
