package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;
import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.factories.Factory;

public class SpawnComponent extends Component {
    public long passedTimeMs;
    public long spawnRateMs;
    public Factory factory;
    public Entity entity;
    public int level;


    public SpawnComponent(Entity entity, Factory factory, long spawnRateMs, int level){
        this.spawnRateMs = spawnRateMs;
        this.factory = factory;
        this.entity = entity;
        this.level = level;
    }

    public void update(double delta){
        if(passedTimeMs > spawnRateMs){
            passedTimeMs = 0;
            MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
            Entity coin = factory.createEntity((int)movementComponent.x, (int)movementComponent.y,  level);
            EntityManager.getInstance().addEntity(coin);
        }
        passedTimeMs += delta * 1000;
    }


}

