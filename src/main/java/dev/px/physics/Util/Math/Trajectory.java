package dev.px.physics.Util.Math;

import dev.px.physics.Util.Math.Vector.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author James
 */
public class Trajectory {

    private Vec3d position;
    private Vec3d velocity;

    private final List<Vec3d> path;
    private final int maxPoints;

    public Trajectory(Vec3d position, Vec3d velocity, int maxPoints) {
        this.position = position.copy();
        this.velocity = velocity.copy();
        this.maxPoints = maxPoints;
        this.path = new ArrayList<>();
    }

    public Trajectory(Vec3d position, Vec3d velocity) {
        this(position, velocity, 500);
    }

    /**
     * Records the current position into the path history.
     * Drops the oldest point once the cap is reached.
     */
    public void record(Vec3d currentPosition, Vec3d currentVelocity) {
        this.position = currentPosition.copy();
        this.velocity = currentVelocity.copy();

        if (path.size() >= maxPoints) {
            path.remove(0);
        }
        path.add(currentPosition.copy());
    }

    public void clearPath() {
        path.clear();
    }

    public List<Vec3d> getPath() {
        return Collections.unmodifiableList(path);
    }

    public Vec3d getLastPosition() {
        if (path.isEmpty()) return null;
        return path.get(path.size() - 1);
    }

    public Vec3d getPosition() { return position; }
    public Vec3d getVelocity() { return velocity; }
    public int getMaxPoints() { return maxPoints; }

}
