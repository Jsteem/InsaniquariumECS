package insaniquarium.ecs.systems;

import insaniquarium.ecs.Entity;
import insaniquarium.ecs.EntityManager;
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
                        BoundingCollisionComponent boundingCollisionComponent = entity.getComponent(BoundingCollisionComponent.class);
                        if (match != null && boundingCollisionComponent != null) {

                            int left1 = (int) movementComponent.x - boundingCollisionComponent.boundingCollisionWidth / 2;
                            int right1 = (int) movementComponent.x + boundingCollisionComponent.boundingCollisionWidth / 2;
                            int top1 = (int) movementComponent.y - boundingCollisionComponent.boundingCollisionHeight / 2;
                            int bottom1 = (int) movementComponent.y + boundingCollisionComponent.boundingCollisionHeight / 2;

                            int left2 = (int) match.x - match.width / 2;
                            int right2 =(int) match.x + match.width / 2;
                            int top2 =(int) match.y - match.height / 2;
                            int bottom2 =(int) match.y + match.height / 2;


                            if (right1 >= left2 && left1 <= right2 && bottom1 >= top2 && top1 <= bottom2) {
                                collisionFound = true;
                                //java.lang.System.out.println("Collision found between entity : " + entity.id + "and target: " + match.entity.id);

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
