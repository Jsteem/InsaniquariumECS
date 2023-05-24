package insaniquarium.ecs.components;

import insaniquarium.utility.ImageInfo;

public class RenderComponent extends Component {
    public int x;
    public int y;
    public int width;
    public int height;
    public ImageInfo.IMAGE_NAME imageName;

    public int numRow;

    public int numCol;

    public double scale;

    public RenderComponent(ImageInfo.IMAGE_NAME imageName, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageName = imageName;
        this.numRow = -1;
        this.numCol = -1;
    }
    public RenderComponent(ImageInfo.IMAGE_NAME imageName, int x, int y, int numRow, int numCol, double scale){
        this.x = x;
        this.y = y;

        this.imageName = imageName;
        this.numRow = numRow;
        this.numCol = numCol;
        this.width = -1;
        this.height = -1;
        this.scale = scale;
    }


}
