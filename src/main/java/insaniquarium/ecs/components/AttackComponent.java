package insaniquarium.ecs.components;

import insaniquarium.ecs.Entity;

public abstract class AttackComponent extends Component{
    public int x;
    public int y;
    public int weaponTier;
    public Entity entity;

    public AttackComponent(Entity entity, int x, int y, int weaponTier){
        this.x = x;
        this.y = y;
        this.weaponTier = weaponTier;
        this.entity = entity;

    }
    public abstract void handleAttack(int x, int y, int weaponTier);
}
