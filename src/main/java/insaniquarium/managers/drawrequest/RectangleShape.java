package insaniquarium.managers.drawrequest;

import javafx.scene.canvas.GraphicsContext;

public class RectangleShape extends DrawRequest{


    private final javafx.scene.paint.Paint color;
    private final int lineWidth;
    private final int width;

    private final int height;
    private final float x;
    private final float y;

    public RectangleShape(int lineWidth, javafx.scene.paint.Paint color, float x, float y, int width, int height){
        this.color = color;
        this.lineWidth = lineWidth;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setStroke(color);
        graphics.setLineWidth(lineWidth);
        graphics.strokeRect(x - width/2, y - height/2, width, height);
    }

}
