package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.components.animationtypecomponents.AnimationComponent;

public class EatBehavior extends BehaviorTypeComponent{
    public EatBehavior(long durationBehaviorMs) {
        super(durationBehaviorMs);
    }

    @Override
    public void onEnter(Entity entity, BehaviorComponent component) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        if (animationComponent != null) {
            if(!component.sick){
                animationComponent.setActiveType(AnimationComponent.AnimationType.EAT);
            }
            else{
                animationComponent.setActiveType(AnimationComponent.AnimationType.HUNGRY_EAT);
            }
        }
    }

    @Override
    public void onUpdate(Entity entity, BehaviorComponent component, double delta) {
        AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
        if (animationComponent.animationComplete) {
            onExit(entity, component);
        }
    }

    @Override
    public void onExit(Entity entity, BehaviorComponent component) {
        component.currentBehavior = component.getBehaviorTypeComponent(BehaviorComponent.BEHAVIOR_TYPE.IDLE);
        component.passedTimeMs = 0;
        component.nextBehavior = null;


    }
}
