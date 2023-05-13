package insaniquarium.ecs.components.animationtypes;

import insaniquarium.ecs.components.AnimationComponent;
import insaniquarium.ecs.components.AnimationTypeComponent;
import insaniquarium.utility.ImageInfo;

public class DieAnimation extends AnimationTypeComponent {
    public DieAnimation(ImageInfo.IMAGE_NAME spriteName, AnimationComponent.AnimationType type, int rowNr, double animationSpeed, boolean loop) {
        super(spriteName, type, rowNr, animationSpeed, loop);
    }
}
