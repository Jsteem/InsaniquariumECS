package insaniquarium.ecs.components.typecomponents;


import insaniquarium.ecs.Component;

public class FishTypeComponent extends Component {
    public enum FISH_TYPE{
        FISH(1 << 7),
        GUPPY_SMALL(1 << 8),
        GUPPY_MEDIUM(0),
        GUPPY_LARGE(0),
        GUPPY_KING(0),
        GUPPY_STAR(0),
        CARNIVORE(1 << 9),
        ULTRAVORE(0),
        STAR_CATCHER(0),
        GUPPY_CRUNCHER(0),
        BEETLE_MUNCHER(0),
        BREEDER(0),
        FISH_DEAD(1 << 10);

        public  final long value;
        FISH_TYPE(long value) {
            this.value = value;
        }
    }
    public FISH_TYPE type;

    public FishTypeComponent(FISH_TYPE type){
        this.type = type;

    }
}
