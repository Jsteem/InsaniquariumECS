package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class PotionComponent extends Component {
    public int numberOfAvailablePotions = 0;


    public boolean hasAvailablePotion(){
        return numberOfAvailablePotions-- > 0;
    }
}
