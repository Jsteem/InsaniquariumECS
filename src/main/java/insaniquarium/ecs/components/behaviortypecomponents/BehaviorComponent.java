package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.ecs.Component;
import insaniquarium.ecs.Entity;

import java.util.HashMap;
import java.util.Map;

public class BehaviorComponent extends Component {

    public BehaviorTypeComponent currentBehavior;

    public BehaviorTypeComponent nextBehavior;

    public BehaviorTypeComponent previousBehavior;

    public static Map<BEHAVIOR_TYPE, BehaviorTypeComponent> behaviorToComponent;
    public long passedTimeMs;
    public long triggerNextBehaviorTimeMs;

    boolean sick = false;


    private Entity entity;
    public enum BEHAVIOR_TYPE{
        IDLE, SEEK_HUNGRY, SEEK_SICK, EAT_HUNGRY, EAT_SICK, FALL_DOWN
    }
    public void update(double delta){
        if(currentBehavior != null){
            currentBehavior.onUpdate(entity, this, delta);
        }
    }
    public BehaviorComponent(Entity entity, BEHAVIOR_TYPE current, BEHAVIOR_TYPE next){
        this.entity = entity;
        this.currentBehavior = getBehaviorTypeComponent(current);
        this.nextBehavior = getBehaviorTypeComponent(next);


    }
    public static void initComponents(){
        behaviorToComponent = new HashMap<>();
        behaviorToComponent.put(BEHAVIOR_TYPE.IDLE, new IdleBehavior(3000));
        behaviorToComponent.put(BEHAVIOR_TYPE.SEEK_HUNGRY, new SeekHungryBehavior(3000));
        behaviorToComponent.put(BEHAVIOR_TYPE.FALL_DOWN, new FallBehavior(0));


    }
    public BehaviorTypeComponent getBehaviorTypeComponent(BEHAVIOR_TYPE behaviorType){
        if(behaviorToComponent != null){
            return behaviorToComponent.get(behaviorType);
        }
        return null;
    }

}
