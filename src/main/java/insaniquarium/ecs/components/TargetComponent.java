package insaniquarium.ecs.components;

public class TargetComponent extends Component {

    public long maskEntityTarget;

    public long maskEntity;


    public TargetComponent(long maskEntity, long maskEntityTarget){
        this.maskEntity = maskEntity;
       this.maskEntityTarget = maskEntityTarget;
    }
}
