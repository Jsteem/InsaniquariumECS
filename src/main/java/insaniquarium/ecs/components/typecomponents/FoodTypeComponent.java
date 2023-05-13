package insaniquarium.ecs.components.typecomponents;

import insaniquarium.ecs.Component;

public class FoodTypeComponent extends Component {
    public enum FOOD_TYPE{
        FOOD(1 << 11),
        PELLET(0),
        CANNED(0),
        PILL(0),
        POTION(0);
        public  final long value;
        FOOD_TYPE(long value) {
            this.value = value;
        }
    }
    public FOOD_TYPE type;

    public FoodTypeComponent(FOOD_TYPE type){
        this.type = type;

    }}
