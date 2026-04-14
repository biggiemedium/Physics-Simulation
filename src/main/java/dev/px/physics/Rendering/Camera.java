package dev.px.physics.Rendering;

import dev.px.physics.Util.Math.Vector.Vec2d;
import dev.px.physics.Util.Math.Vector.Vec3d;

/**
 * @author James
 */
public class Camera {

    private Vec2d rotation;
    private Vec3d position;

    public Camera(Vec3d position, Vec2d rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Camera(Vec3d position) {
        this(position, new Vec2d(0, 0));
    }

    public void move(Vec3d delta) {
        position.add(delta);
    }

    public Vec3d getForward() {
        double pitchRad = Math.toRadians(rotation.x);
        double yawRad = Math.toRadians(rotation.y);

        return new Vec3d(
                Math.cos(pitchRad) * Math.sin(yawRad),
                -Math.sin(pitchRad),
                -Math.cos(pitchRad) * Math.cos(yawRad)
        );
    }

    public Vec3d getRight() {
        double yawRad = Math.toRadians(rotation.y);

        return new Vec3d(
                Math.cos(yawRad),
                0,
                Math.sin(yawRad)
        );
    }

    public Vec3d getUp() {
        return new Vec3d(0, 1, 0);
    }

    public void rotate(double pitchDelta, double yawDelta) {
        rotation.add(pitchDelta, yawDelta);

        // Clamp pitch to prevent gimbal lock
        if (rotation.x > 89.0) {
            rotation.x = 89.0;
        }
        if (rotation.x < -89.0) {
            rotation.x = -89.0;
        }
    }

    public Vec2d getRotation() {
        return rotation;
    }

    public void setRotation(Vec2d rotation) {
        this.rotation = rotation;
    }

    public Vec3d getPosition() {
        return position;
    }

    public void setPosition(Vec3d position) {
        this.position = position;
    }
}
