package insaniquarium.ecs;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager {
    private static EntityManager instance;
    private List<Entity> entities = new CopyOnWriteArrayList<>();


    private EntityManager(){

    }

    public static EntityManager getInstance(){
        if(instance == null){
            instance = new EntityManager();

        }
        return instance;
    }

    public void addEntity(Entity entity){
        this.entities.add(entity);
    }
    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }
    public List<Entity> getEntities(){
        return this.entities;
    }

    public void reset(){this.entities.clear();}



}
