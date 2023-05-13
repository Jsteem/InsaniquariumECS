package insaniquarium.managers.drawrequest;

import insaniquarium.managers.ResourceManager;
import insaniquarium.utility.ImageInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Font extends DrawRequest {
    ImageInfo.IMAGE_NAME imageName;
    int sx;
    int sy;
    int sw;
    int sh;
    int dx;
    int dy;
    int dw;
    int dh;
    float percentage;

    Image image;

    public Font(ImageInfo.IMAGE_NAME name, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, float percentage) {
        this.sx = sx;
        this.sy = sy;
        this.sw = sw;
        this.sh = sh;
        this.dx = dx;
        this.dy = dy;
        this.dw = dw;
        this.dh = dh;
        this.percentage = percentage;
        this.image = ResourceManager.getInstance().getImage(name).getImage();

    }

    @Override
    public void draw(GraphicsContext graphics) {
        int dx1 = dx;
        int dy1 = dy;
        int dx2 = dx1 + (int) (dw * percentage);
        int dy2 = dy1 + (int) (dh * percentage);
        int sx1 = sx;
        int sy1 = sy;
        int sx2 = sx1 + sw;
        int sy2 = sy1 + sh;


        graphics.drawImage(image,
                sx1, sy1, sx2, sy2,
                dx1, dy1, dx2, dy2
                );
    }
}
