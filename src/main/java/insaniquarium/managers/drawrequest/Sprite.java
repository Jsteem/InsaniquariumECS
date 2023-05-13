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


    private final GameImage image;

    private final ImageInfo imageInfo;

    public Sprite(ImageInfo.IMAGE_NAME name, int x, int y, int row, int column, boolean reversed) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
        this.image = ResourceManager.getInstance().getImage(name);
        this.imageInfo = this.image.getImageInfo();
        this.reversed = reversed;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        int dx1 = x - imageInfo.cellWidth / 2;
        int dy1 = y - imageInfo.cellHeight / 2;
        int dx2 = imageInfo.cellWidth;
        int dy2 = imageInfo.cellHeight;
        int sx1 = column * imageInfo.cellWidth;
        int sy1 = row * imageInfo.cellHeight;
        int sx2 = imageInfo.cellWidth;
        int sy2 = imageInfo.cellHeight;

        if (reversed) {
            graphics.scale(-1, 1);
            dx1 = -x - imageInfo.cellWidth / 2;

            graphics.drawImage(image.getImage(), sx1, sy1, sx2, sy2,
                    dx1, dy1, dx2, dy2);

            graphics.scale(-1, 1);

        } else {
            graphics.drawImage(image.getImage(), sx1, sy1, sx2, sy2,
                    dx1, dy1, dx2, dy2
            );

        }
    }
}
