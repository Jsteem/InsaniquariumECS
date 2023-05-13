package insaniquarium.managers.drawrequest;

import javafx.scene.canvas.GraphicsContext;

public abstract class DrawRequest {
    public int priority = 0;
    public abstract void  draw(GraphicsContext graphics);

}
