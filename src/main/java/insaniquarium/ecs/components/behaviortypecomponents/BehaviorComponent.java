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
    boolean boundToGround = false;


    private Entity entity;
    public enum BEHAVIOR_TYPE{
        IDLE, SEEK, EAT, FALL_DOWN
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
        this.boundToGround = false;

    }
    public BehaviorComponent(Entity entity, BEHAVIOR_TYPE current, BEHAVIOR_TYPE next, boolean boundToGround){
        this.entity = entity;
        this.currentBehavior = getBehaviorTypeComponent(current);
        this.nextBehavior = getBehaviorTypeComponent(next);
        this.boundToGround = boundToGround;

    }
    public static void initComponents(){
        behaviorToComponent = new HashMap<>();
        behaviorToComponent.put(BEHAVIOR_TYPE.IDLE, new IdleBehavior(3000));
        behaviorToComponent.put(BEHAVIOR_TYPE.SEEK, new SeekBehavior(5000));
        behaviorToComponent.put(BEHAVIOR_TYPE.FALL_DOWN, new FallBehavior(0));
        behaviorToComponent.put(BEHAVIOR_TYPE.EAT, new EatBehavior(0));

    }
    public BehaviorTypeComponent getBehaviorTypeComponent(BEHAVIOR_TYPE behaviorType){
        if(behaviorToComponent != null){
            return behaviorToComponent.get(behaviorType);
        }
        return null;
    }

}
