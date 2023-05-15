package insaniquarium.ecs.components.animationtypecomponents;

import insaniquarium.utility.ImageInfo;

public class DieAnimation extends AnimationTypeComponent {
    public DieAnimation(ImageInfo.IMAGE_NAME spriteName, AnimationComponent.AnimationType type, int rowNr, double animationSpeed, boolean loop, double scale) {
        super(spriteName, type, rowNr, animationSpeed, loop, scale);
    }
}
