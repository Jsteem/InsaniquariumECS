package insaniquarium.managers.drawrequest;

import insaniquarium.managers.ResourceManager;
import insaniquarium.utility.GameImage;
import insaniquarium.utility.ImageInfo;
import javafx.scene.canvas.GraphicsContext;

public class Sprite extends DrawRequest {

    private final int x;
    private final int y;

    private final int row;

    private final int column;

    boolean reversed;

    double scale;


    private final GameImage image;

    private final ImageInfo imageInfo;

    public Sprite(ImageInfo.IMAGE_NAME name, int x, int y, int row, int column, boolean reversed, double scale) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
        this.image = ResourceManager.getInstance().getImage(name);
        this.imageInfo = this.image.getImageInfo();
        this.reversed = reversed;
        this.scale = scale;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        int sx = column * imageInfo.cellWidth;
        int sy = row * imageInfo.cellHeight;
        int sw = imageInfo.cellWidth;
        int sh = imageInfo.cellHeight;

        int dx = (int) (x - (imageInfo.cellWidth / 2) * scale);
        int dy = (int) (y - (imageInfo.cellHeight / 2) * scale);
        int dw = (int) (imageInfo.cellWidth * scale);
        int dh = (int) (imageInfo.cellHeight * scale);


        if (reversed) {
            graphics.scale(-1, 1);
            dx = (int) (-x - (imageInfo.cellWidth / 2) * scale);

            graphics.drawImage(image.getImage(), sx, sy, sw, sh,
                    dx, dy, dw, dh);

            graphics.scale(-1, 1);

        } else {
            graphics.drawImage(image.getImage(), sx, sy, sw, sh,
                    dx, dy, dw, dh
            );

        }
    }
}
