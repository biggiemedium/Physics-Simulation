package dev.px.physics.System;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Util.Vec3d;

import java.util.List;

/**
 * @author James
 */
public class PhysicsSystem {

    private static final double G = 6.67430e-11;

    public void update(List<PhysicalObject> objects, double delta) {
        // currently o(n^2)
        // TODO: Improve computation speed
        for (int i = 0; i < objects.size(); i++) {
            PhysicalObject a = objects.get(i);

            for (int j = 0; j < objects.size(); j++) {
                if (i == j) { // race condition ?
                    continue;
                }

                PhysicalObject b = objects.get(j);

                Vec3d direction = b.getPosition().copy().subtract(a.getPosition());
                double distanceSq = direction.lengthSquared() + 0.01;

                double forceMag = G * a.getMass() * b.getMass() / distanceSq;

                Vec3d force = direction.normalize().multiply(forceMag);

                a.applyForce(force, delta);
            }
        }

        for (PhysicalObject obj : objects) {
            obj.update(delta);
        }
    }
}
