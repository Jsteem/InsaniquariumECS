package insaniquarium.ecs.components.animationtypecomponents;

import insaniquarium.utility.ImageInfo;

public class AnimationTypeComponent {

    public ImageInfo.IMAGE_NAME spriteName;

    public AnimationComponent.AnimationType type;

    public int rowNr;

    public double animationSpeed;

    public boolean loop;

    public double scale;


    public AnimationTypeComponent(ImageInfo.IMAGE_NAME spriteName, AnimationComponent.AnimationType type, int rowNr, double animationSpeed, boolean loop, double scale){
        this.spriteName = spriteName;
        this.type = type;
        this.rowNr = rowNr;
        this.animationSpeed = animationSpeed;
        this.loop = loop;
        this.scale = scale;
    }


}
