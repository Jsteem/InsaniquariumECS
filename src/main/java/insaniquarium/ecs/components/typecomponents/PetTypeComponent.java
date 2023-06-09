package insaniquarium.ecs.components.typecomponents;

import insaniquarium.ecs.components.Component;

public class PetTypeComponent extends Component {
    public enum PET_TYPE{
        PET(1 << 12),
        STINKY(0),
        NIKO(0),
        ITCHY(0),
        PREGO(0),
        ZORF(0),
        VERT(0),
        CLYDE(0),
        MERYL(0),
        WADSWORTH(0),
        RUFUS(0);

        public  final long value;
        PET_TYPE(long value) {
            this.value = value;
        }
    }
    public PET_TYPE type;

    public PetTypeComponent(PET_TYPE type){
        this.type = type;

    }}
