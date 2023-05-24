package insaniquarium.ecs.components;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;

public abstract class SpecialMoveComponent extends Component{
    public double passedTimeMs;
    public double specialMoveRateMs;

    public Entity entity;

    public SpecialMoveComponent(Entity entity, double specialMoveRateMs){
        this.specialMoveRateMs = specialMoveRateMs;
        this.entity = entity;
    }

    public abstract void doSpecialMove();

    public void update(double delta){

        if(passedTimeMs > specialMoveRateMs){
            passedTimeMs = 0;
            doSpecialMove();
        }
        passedTimeMs += delta * 1000;
    }
}
