package dev.px.physics.Objects;

import dev.px.physics.Util.Math.Vector.Vec3d;

/**
 * @author James
 */
public class PhysicalObject {

    public Vec3d position;

    private Vec3d velocity;
    private double mass;

    // For debug and not moving objects
    // everything in our actual galaxy is rotating around something but we can't scale the entire galaxy
    private boolean pinned = false;

    public PhysicalObject(Vec3d position, Vec3d velocity, double mass) {
        this.position = position.copy();
        this.velocity = velocity.copy();
        this.mass = mass;
    }

    public void applyForce(Vec3d force) {
        if (pinned) return;
        Vec3d acceleration = force.copy().divide(mass);
        velocity.add(acceleration.multiply(1.0));
    }

    public void applyForce(Vec3d force, double delta) {
        if (pinned) return;
        Vec3d acceleration = force.copy().divide(mass);
        velocity.add(acceleration.multiply(delta));
    }

    public void update(double delta) {
        if (pinned) return;
        position.add(velocity.copy().multiply(delta));
    }

    public Vec3d getPosition() {
        return position;
    }

    public double getMass() {
        return mass;
    }

    public Vec3d getVelocity() {
        return velocity;
    }

    public boolean isPinned() { return pinned; }
    public void setPinned(boolean pinned) { this.pinned = pinned; }
}
