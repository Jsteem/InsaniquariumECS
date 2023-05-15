package insaniquarium.ecs.components.animationtypecomponents;

import insaniquarium.utility.ImageInfo;

public class IdleAnimation extends AnimationTypeComponent {
    public IdleAnimation(ImageInfo.IMAGE_NAME spriteName, AnimationComponent.AnimationType type, int rowNr, double animationSpeed, boolean loop, double scale) {
        super(spriteName, type, rowNr, animationSpeed, loop, scale);
    }
}
