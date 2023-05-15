package insaniquarium.ecs;

import insaniquarium.ecs.components.BoundingCollisionComponent;
import insaniquarium.ecs.components.MovementComponent;
import insaniquarium.ecs.components.TargetComponent;
import insaniquarium.ecs.components.behaviortypecomponents.BehaviorComponent;
import insaniquarium.ecs.systems.RenderSystem;
import insaniquarium.ecs.systems.System;
import insaniquarium.utility.CollisionObject;
import insaniquarium.utility.KDTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemManager {
    private static SystemManager instance;
    private List<System> systems = new ArrayList<>();

    private RenderSystem renderSystem;

    private HashMap<Long, KDTree> objectTypeToKDTree;


    private SystemManager(){
        BehaviorComponent.initComponents();
    }

    public static SystemManager getInstance() {
        if(instance == null){
            instance = new SystemManager();
        }
        return instance;
    }

    public void update(double timeStep) {
        createKDTree();
        for(System system: systems){
            system.update(timeStep);
        }
    }

    public void registerSystem(System system){
        this.systems.add(system);
    }

    public void registerRenderSystem(RenderSystem renderSystem){
        this.renderSystem = renderSystem;
    }

    public void updateDrawSystem() {
        if(this.renderSystem!= null){
            this.renderSystem.update();
        }
    }
    public HashMap<Long, KDTree> getKDTree(){
        return this.objectTypeToKDTree;
    }
    public void createKDTree(){
        this.objectTypeToKDTree = new HashMap<>();
        List<Entity> entities = EntityManager.getInstance().getEntities();
        if (entities != null) {


            for (Entity entity : entities) {
                TargetComponent targetComponent = entity.getComponent(TargetComponent.class);
                if (targetComponent != null) {
                    long mask = targetComponent.maskEntityTarget;
                    if (mask > 0) {
                        objectTypeToKDTree.put(mask, new KDTree());
                    }

                }
            }

            for (Entity entity : entities) {
                TargetComponent targetComponent = entity.getComponent(TargetComponent.class);
                if (targetComponent != null) {
                    for (long targetMask : objectTypeToKDTree.keySet()) {
                        if ((targetMask & targetComponent.maskEntity) > 0) {
                            BoundingCollisionComponent boundingCollisionComponent = entity.getComponent(BoundingCollisionComponent.class);
                            MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
                            if (boundingCollisionComponent != null && movementComponent != null) {
                                objectTypeToKDTree.get(targetMask).add(
                                        new CollisionObject(entity, movementComponent.x, movementComponent.y, boundingCollisionComponent.boundingCollisionRadius));
                            }
                        }
                    }

                }
            }
        }
    }
}
