package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;
import insaniquarium.utility.ImageInfo;

public class RenderComponent extends Component {
    public int x;
    public int y;
    public int width;
    public int height;
    public ImageInfo.IMAGE_NAME imageName;

    public RenderComponent(ImageInfo.IMAGE_NAME imageName, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageName = imageName;
    }


}
