package insaniquarium.managers.drawrequest;

import insaniquarium.managers.ResourceManager;
import insaniquarium.utility.ImageInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Font extends DrawRequest {
    int sx;
    int sy;
    int sw;
    int sh;
    int dx;
    int dy;
    int dw;
    int dh;
    Image image;

    public Font(ImageInfo.IMAGE_NAME name, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh) {
        this.sx = sx;
        this.sy = sy;
        this.sw = sw;
        this.sh = sh;
        this.dx = dx;
        this.dy = dy;
        this.dw = dw;
        this.dh = dh;
        this.image = ResourceManager.getInstance().getImage(name).getImage();

    }

    @Override
    public void draw(GraphicsContext graphics) {

        graphics.drawImage(image,
                sx, sy, sw, sh,
                dx, dy, dw, dh
                );
    }
}
