package insaniquarium.ecs.systems;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
import insaniquarium.ecs.SystemManager;
import insaniquarium.ecs.components.*;

import insaniquarium.ecs.components.handlecollisioncomponents.HandleCollisionComponent;
import insaniquarium.ecs.components.handlecollisioncomponents.HandleNoCollision;
import insaniquarium.ecs.components.typecomponents.ClickTypeComponent;
import insaniquarium.utility.CollisionObject;
import insaniquarium.utility.KDTree;

import java.util.HashMap;
import java.util.List;

public class CollisionSystem extends System {
    public CollisionSystem() {
        super();
    }

    @Override
    public void update(double delta) {
        HashMap<Long, KDTree> objectTypeToKDTree = SystemManager.getInstance().getKDTree();
        List<Entity> entities = EntityManager.getInstance().getEntities();

        if (objectTypeToKDTree != null && entities != null) {
            for (Entity entity : entities) {
                boolean collisionFound = false;
                TargetComponent targetComponent = entity.getComponent(TargetComponent.class);
                MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
                if (targetComponent != null) {
                    KDTree target = objectTypeToKDTree.get(targetComponent.maskEntityTarget);
                    if (target != null && movementComponent != null) {
                        CollisionObject match = target.searchNearest(movementComponent.x, movementComponent.y);
                        if (match != null) {
                            //check if we have an eat radius else treat it as a point
                            EatCollisionComponent eatCollisionComponent = entity.getComponent(EatCollisionComponent.class);
                            int radius = 0;
                            float offsetX = movementComponent.x;
                            float offsetY = movementComponent.y;

                            if (eatCollisionComponent != null) {
                                //calculate the eat radius based on the velocity direction
                                offsetX += (movementComponent.vx > 0 ? eatCollisionComponent.eatCollisionOffsetX : -eatCollisionComponent.eatCollisionOffsetX);
                                offsetY += eatCollisionComponent.eatCollisionOffsetY;
                                radius = eatCollisionComponent.eatCollisionRadius;


                            }
                            float dx = offsetX - match.x;
                            float dy = offsetY - match.y;
                            double distance = Math.sqrt(dx * dx + dy * dy);
                            double sumRadius = radius + match.radius;
                            if (distance < sumRadius) {
                                collisionFound = true;
                                //java.lang.System.out.println("Collision found between entity : " + entity.id + "and target: " + match.entity.id);

                                //handle the collision for the entity itself
                                HandleCollisionComponent handleCollisionComponent = entity.getComponent(HandleCollisionComponent.class);
                                if (handleCollisionComponent != null) {
                                    handleCollisionComponent.handleCollision(entity, match.entity, targetComponent.maskEntityTarget);
                                }
                            }

                        }
                    }

                }
                //delete click entities
                if (targetComponent != null && (targetComponent.maskEntity & ClickTypeComponent.CLICK_TYPE.CLICK.value) > 0) {
                    HandleNoCollision handleNoCollision = entity.getComponent(HandleNoCollision.class);
                    if (handleNoCollision != null && !collisionFound) {
                        handleNoCollision.handleNoCollision(entity, movementComponent.x, movementComponent.y);
                    }
                    entities.remove(entity);
                }
            }
        }
    }
}
