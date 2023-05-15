package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class GrowthComponent extends Component {

    public int growthLevel = 0;

    public GrowthComponent(int growthLevel){
        this.growthLevel = growthLevel;
    }

}
