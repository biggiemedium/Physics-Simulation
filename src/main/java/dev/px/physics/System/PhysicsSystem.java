package dev.px.physics.System;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Util.Math.MathConstants;
import dev.px.physics.Util.Math.Vector.Vec3d;

import java.util.List;

/**
 * @author James
 */
public class PhysicsSystem {

   // private static final double G = 6.67430e-11;

    public void update(List<PhysicalObject> objects, double delta) {
        // currently o(n^2)
        // TODO: Improve computation speed
        for (int i = 0; i < objects.size(); i++) {
            PhysicalObject a = objects.get(i);

            for (int j = 0; j < objects.size(); j++) {
                if (i == j) {
                    continue;
                }

                PhysicalObject b = objects.get(j);

                Vec3d direction = b.getPosition().copy().subtract(a.getPosition());
                double distanceSq = direction.lengthSquared() + 0.01;
                double forceMag = MathConstants.GRAVITY * a.getMass() * b.getMass() / distanceSq;
                Vec3d force = direction.normalize().multiply(forceMag);

                a.applyForce(force, delta);
            }
        }

        for (PhysicalObject obj : objects) {
            obj.update(delta);
        }
    }



    public void orbitObjects(List<PhysicalObject> objects, double delta) {
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {

                PhysicalObject a = objects.get(i);
                PhysicalObject b = objects.get(j);

                Vec3d r = a.position.copy().subtract(b.position);
                double distance = r.length();
                Vec3d direction = r.normalize();

                // F = GMm/r2
                // where G is gravity constant, M is the mass of the object doing the pulling, and m is the mass of the
                // object being pulled, and r is the distance between the objects.
                double forceMag = (MathConstants.GRAVITY * a.getMass() * b.getMass()) / (distance * distance);
                Vec3d force = direction.multiply(forceMag);

                a.applyForce(force);
                b.applyForce(force.copy().multiply(-1));
            }



        }
    }

}
