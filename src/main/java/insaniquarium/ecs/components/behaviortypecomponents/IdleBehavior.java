package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;

public class IdleBehavior extends BehaviorTypeComponent {

    public IdleBehavior(long triggerNextBehaviorTime) {
        super(triggerNextBehaviorTime);
    }

    @Override
    public void onEnter(Entity entity, BehaviorComponent component) {
        component.triggerNextBehaviorTimeMs = durationBehaviorMs;
        component.passedTimeMs = 0;

        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        if (animationComponent != null) {
            animationComponent.setActiveTypeSmooth(AnimationComponent.AnimationType.IDLE);
        }
        component.sick = false;
    }

    @Override
    public void onUpdate(Entity entity, BehaviorComponent component, double delta) {

        if(component.passedTimeMs == 0){
            onEnter(entity, component);
        }
        component.passedTimeMs += delta * 1000;

        if(component.passedTimeMs > component.triggerNextBehaviorTimeMs){
            onExit(entity, component);
        }


    }

    @Override
    public void onExit(Entity entity, BehaviorComponent component) {
        component.passedTimeMs = 0;
        component.previousBehavior = this;
        if(component.nextBehavior != null){

            component.currentBehavior = component.nextBehavior;
        }
        else{
            component.nextBehavior = component.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.SEEK);
        }
    }
}
