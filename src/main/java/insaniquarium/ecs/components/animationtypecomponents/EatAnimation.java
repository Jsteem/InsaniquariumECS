package insaniquarium.ecs.components.animationtypecomponents;

import insaniquarium.utility.ImageInfo;


public class EatAnimation extends AnimationTypeComponent {
    public EatAnimation(ImageInfo.IMAGE_NAME spriteName, AnimationComponent.AnimationType type, int rowNr, double animationSpeed, boolean loop, double scale) {
        super(spriteName, type, rowNr, animationSpeed, loop, scale);
    }
}