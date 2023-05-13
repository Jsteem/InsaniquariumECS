package insaniquarium.ecs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Entity {
    private List<Component> components = new CopyOnWriteArrayList();
    private static long totalIds = 0;
    public long id;

    public Entity(){
        this.id = ++totalIds;

    }
    public <T extends Component> T getComponent(Class<T> componentClass){
        for(Component c: components){
            if(componentClass.isAssignableFrom(c.getClass())){
                return componentClass.cast(c);
            }
        }
        return null;
    }
    public <T extends Component> void removeComponent(Class<T> componentClass){
        for(Component c: components){
            if(componentClass.isAssignableFrom(c.getClass())){
                components.remove(c);
            }
        }
    }

    public <T extends Component> void addComponent(Component component){
        this.components.add(component);

    }

}
