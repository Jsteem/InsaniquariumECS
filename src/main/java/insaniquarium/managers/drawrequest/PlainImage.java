package insaniquarium.managers.drawrequest;

import insaniquarium.managers.ResourceManager;
import insaniquarium.utility.ImageInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PlainImage extends DrawRequest {
    private final int x;
    private final int y;

    private int height;

    private int width;

    private final Image image;

    public PlainImage(ImageInfo.IMAGE_NAME name, int x, int y) {
        this.x = x;
        this.y = y;
        this.image = ResourceManager.getInstance().getImage(name).getImage();
    }

    public PlainImage(ImageInfo.IMAGE_NAME name, int x, int y, int width, int height) {
        this(name, x, y);
        this.height = height;
        this.width = width;

    }

    @Override
    public void draw(GraphicsContext graphics) {

        if (width > 0 && height > 0) {
            int dx1 = x;
            int dy1 = y;
            int dx2 = width;
            int dy2 = height;
            int sx1 = 0;
            int sy1 = 0;
            int sx2 = width;
            int sy2 = height;


            graphics.drawImage(image, sx1, sy1, sx2, sy2,dx1, dy1, dx2, dy2);
        } else {
            graphics.drawImage(image, x, y);
        }
    }
}
