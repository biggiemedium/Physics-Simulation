package dev.px.physics.System;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Util.Math.Trajectory;
import dev.px.physics.Util.Math.Vector.Vec3d;

import java.util.*;

/**
 * Tracks the trajectory of every {@link PhysicalObject} in the current scene.
 *
 * <p>Call {@link #record()} once per simulation step (e.g. inside Scene#update)
 * to snapshot all object positions into their respective {@link Trajectory}.
 *
 * @author James
 */
public class TrajectoriesSystem {

    private final int maxPoints;

    /** Maps each tracked object to its Trajectory. */
    private final Map<PhysicalObject, Trajectory> trajectories = new LinkedHashMap<>();

    public TrajectoriesSystem(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public TrajectoriesSystem() {
        this(500);
    }

    /** Registers a single object for trajectory tracking. No-op if already registered. */
    public void addObject(PhysicalObject obj) {
        trajectories.putIfAbsent(obj, new Trajectory(obj.getPosition(), obj.getVelocity(), maxPoints));
    }

    /** Registers all objects in the list. */
    public void addObjects(List<PhysicalObject> objects) {
        for (PhysicalObject obj : objects) {
            addObject(obj);
        }
    }

    /** Unregisters an object and discards its trajectory. */
    public void removeObject(PhysicalObject obj) {
        trajectories.remove(obj);
    }

    /** Clears all registered objects and their trajectories. */
    public void clear() {
        trajectories.clear();
    }

    /**
     * Snapshots the current position + velocity of every registered object.
     * Call this once per update/simulation step.
     */
    public void record() {
        for (Map.Entry<PhysicalObject, Trajectory> entry : trajectories.entrySet()) {
            PhysicalObject obj = entry.getKey();
            entry.getValue().record(obj.getPosition(), obj.getVelocity());
        }
    }

    /** Returns the {@link Trajectory} for the given object, or null if not tracked. */
    public Trajectory getTrajectory(PhysicalObject obj) {
        return trajectories.get(obj);
    }

    /** Returns the recorded path (list of positions) for the given object. */
    public List<Vec3d> getPath(PhysicalObject obj) {
        Trajectory t = trajectories.get(obj);
        return t != null ? t.getPath() : Collections.emptyList();
    }

    /** Returns an unmodifiable view of all trajectories keyed by object. */
    public Map<PhysicalObject, Trajectory> getAllTrajectories() {
        return Collections.unmodifiableMap(trajectories);
    }

    /** Returns all currently tracked objects. */
    public Iterable<PhysicalObject> getTrackedObjects() {
        return Collections.unmodifiableSet(trajectories.keySet());
    }

}
