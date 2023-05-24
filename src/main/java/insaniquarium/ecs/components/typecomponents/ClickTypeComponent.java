package insaniquarium.ecs.components.typecomponents;

import insaniquarium.ecs.components.Component;

public class ClickTypeComponent extends Component {
    public enum CLICK_TYPE {
        CLICK(1 << 1),
        HOVER(1 << 2);

        public  final long value;
        CLICK_TYPE(long value) {
            this.value = value;
        }
    }
    public CLICK_TYPE type;

    public ClickTypeComponent(CLICK_TYPE type){
        this.type = type;

    }
}
