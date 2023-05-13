package insaniquarium.ecs.components.animationtypes;

import insaniquarium.ecs.components.AnimationComponent;
import insaniquarium.ecs.components.AnimationTypeComponent;
import insaniquarium.utility.ImageInfo;

public class TurnHungryAnimation extends AnimationTypeComponent {
    public TurnHungryAnimation(ImageInfo.IMAGE_NAME spriteName, AnimationComponent.AnimationType type, int rowNr, double animationSpeed, boolean loop, double scale) {
        super(spriteName, type, rowNr, animationSpeed, loop, scale);
    }
}
