package insaniquarium.ecs.components.typecomponents;

import insaniquarium.ecs.components.Component;

public class AlienTypeComponent extends Component {
    public enum ALIEN_TYPE{
        ALIEN(1),
        SYLVESTER(0),
        BALROG(0),
        GUS(0),
        DESTRUCTOR(0),
        ULYSSES(0),
        PSYCHOSQUID(0),
        BILATERUS(0);
        public  final long value;
        ALIEN_TYPE(long value) {
            this.value = value;
        }
    }
    public ALIEN_TYPE type;

    public AlienTypeComponent(ALIEN_TYPE type){
        this.type = type;

    }
}
