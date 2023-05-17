package insaniquarium.ecs.components.animationtypecomponents;

import insaniquarium.ecs.Component;
import insaniquarium.ecs.components.animationtypecomponents.AnimationTypeComponent;

import java.util.HashMap;

public class AnimationComponent extends Component {
    public AnimationTypeComponent activeType;

    //represents the column on sprite
    public int frameNr = 0;

    public double timeSinceLastUpdateAnimation = 0;

    public boolean animationComplete;

    public boolean update = true;

    HashMap<AnimationType, AnimationTypeComponent> types;



    //our sprites have typically these animations: IDLE, TURN, EAT, DIE, HUNGRY_IDLE, HUNGRY_TURN, HUNGRY_EAT
    public enum AnimationType{
        IDLE,EAT,TURN,DIE, HUNGRY_IDLE, HUNGRY_TURN, HUNGRY_EAT
    }
    public AnimationComponent(HashMap<AnimationType, AnimationTypeComponent> types){
        this.types = types;
        activeType = types.get(AnimationType.IDLE);
    }
    public void setActiveType(AnimationType animationType){
        if (types.get(animationType) != null) {
            animationComplete = false;
            frameNr = 0;
            update = true;
            activeType = types.get(animationType);
        }

    }
    public void setActiveTypeSmooth(AnimationType animationType) {
        if (types.get(animationType) != null) {
            animationComplete = false;
            update = true;
            activeType = types.get(animationType);
        }

    }


}
