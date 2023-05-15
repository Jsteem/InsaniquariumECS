package insaniquarium.ecs.components.behaviortypecomponents;

import insaniquarium.ecs.Entity;

public abstract class BehaviorTypeComponent {


    public long durationBehaviorMs;

    public BehaviorTypeComponent(long durationBehaviorMs) {
        this.durationBehaviorMs = durationBehaviorMs;

    }

    public abstract void onEnter(Entity entity, BehaviorComponent component);

    public abstract void onUpdate(Entity entity, BehaviorComponent component, double delta);

    public abstract void onExit(Entity entity, BehaviorComponent component);
}
