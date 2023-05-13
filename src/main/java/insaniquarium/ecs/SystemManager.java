package insaniquarium.ecs;

import insaniquarium.ecs.systems.RenderSystem;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    private static SystemManager instance;
    private List<System> systems = new ArrayList<>();

    private RenderSystem renderSystem;

    private SystemManager(){

    }
    public static SystemManager getInstance() {
        if(instance == null){
            instance = new SystemManager();
        }
        return instance;
    }

    public void update(double timeStep) {
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
}
