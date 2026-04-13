package dev.px.physics.Objects;

import dev.px.physics.Util.Vec3d;

/**
 * @author James
 */
public class PhysicalObject {

    public Vec3d position;

    private Vec3d velocity;
    private double mass;

    public PhysicalObject(Vec3d position, Vec3d velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

    public void applyForce(Vec3d force, double delta) {
        Vec3d acceleration = force.copy().divide(mass);
        velocity.add(acceleration.multiply(delta));
    }

    public void update(double delta) {
        position.add(velocity.copy().multiply(delta));
    }

    public Vec3d getPosition() {
        return position;
    }

    public double getMass() {
        return mass;
    }
}
