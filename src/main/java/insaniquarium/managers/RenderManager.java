package insaniquarium.managers;

import insaniquarium.Main;
import insaniquarium.ecs.systems.SystemManager;
import insaniquarium.managers.drawrequest.DrawRequest;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class RenderManager {
    private static RenderManager instance;
    private ArrayList<DrawRequest> drawRequests = new ArrayList<>();

    private RenderManager(){}

    public static RenderManager getInstance(){
        if (instance == null) {
            instance = new RenderManager();
        }
        return instance;
    }
    public void submitDrawRequest(DrawRequest request){
        //add to the list of drawRequests
        drawRequests.add(request);
    }

    public void draw(GraphicsContext graphics){
        graphics.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        SystemManager.getInstance().updateDrawSystem();
        //todo: sort the requests on priority

        for(DrawRequest request : drawRequests){

          request.draw(graphics);

        }
        drawRequests.clear();
    }


}
