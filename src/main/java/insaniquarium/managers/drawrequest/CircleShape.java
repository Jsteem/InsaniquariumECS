package insaniquarium.managers.drawrequest;

import javafx.scene.canvas.GraphicsContext;

public class CircleShape extends DrawRequest{


    private final javafx.scene.paint.Paint color;
    private final int lineWidth;
    private final int radius;
    private final float x;
    private final float y;

    public CircleShape(int lineWidth, javafx.scene.paint.Paint color, int radius, float x, float y){
        this.color = color;
        this.lineWidth = lineWidth;
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setStroke(color);
        graphics.setLineWidth(lineWidth);
        graphics.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

}
