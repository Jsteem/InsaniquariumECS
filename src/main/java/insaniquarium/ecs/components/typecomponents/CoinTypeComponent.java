package insaniquarium.ecs.components.typecomponents;

import insaniquarium.ecs.Component;

public class CoinTypeComponent extends Component {
    public enum COIN_TYPE{
        COLLECTABLE(1 << 3),
        COIN_SILVER(0),
        COIN_GOLD(0),
        DIAMOND(0),
        STAR(1 << 4),
        PEARL( 0),
        CHEST(0),
        BEETLE(1 << 6);
        public  final long value;
        COIN_TYPE(long value) {
            this.value = value;
        }
    }
    public COIN_TYPE type;

    public CoinTypeComponent(COIN_TYPE type){
        this.type = type;

    }

}
