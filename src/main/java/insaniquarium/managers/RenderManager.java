package insaniquarium.managers;

import insaniquarium.ecs.SystemManager;
import insaniquarium.managers.drawrequest.DrawRequest;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class RenderManager {
    private static RenderManager instance;
    private ArrayList<DrawRequest> drawRequests = new ArrayList();

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
        SystemManager.getInstance().updateDrawSystem();
        //todo: sort the requests on priority

        for(DrawRequest request : drawRequests){

          request.draw(graphics);

        }
        drawRequests.clear();
    }

}
