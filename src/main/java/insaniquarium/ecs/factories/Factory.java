package insaniquarium.ecs.factories;

import insaniquarium.ecs.Entity;

public abstract class Factory {
    public abstract Entity createEntity(int x, int y, int level);
    public abstract Entity createDisplayEntity(int x, int y);

}
