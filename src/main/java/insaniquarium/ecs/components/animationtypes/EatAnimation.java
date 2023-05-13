package insaniquarium.ecs.components.animationtypes;

import insaniquarium.ecs.components.AnimationComponent;
import insaniquarium.ecs.components.AnimationTypeComponent;
import insaniquarium.utility.ImageInfo;


public class EatAnimation extends AnimationTypeComponent {
    public EatAnimation(ImageInfo.IMAGE_NAME spriteName, AnimationComponent.AnimationType type, int rowNr, double animationSpeed, boolean loop) {
        super(spriteName, type, rowNr, animationSpeed, loop);
    }
}