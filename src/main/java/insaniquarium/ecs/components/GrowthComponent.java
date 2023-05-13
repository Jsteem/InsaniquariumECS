package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class GrowthComponent extends Component {

    static int[] boundingCircleRadiusGuppyCarnivore = {20, 25, 30, 30};
    static int[] eatCircleOffsetGuppyCarnivore = {10, 14, 20, 20};
    static int[] eatCircleRadiusGuppyCarnivore = {8, 14, 14, 14};


    public int growthLevel = 0;

    public GrowthComponent(int growthLevel){
        this.growthLevel = growthLevel;
    }
    public void increaseGrowth(){
        growthLevel++;
    }
}
