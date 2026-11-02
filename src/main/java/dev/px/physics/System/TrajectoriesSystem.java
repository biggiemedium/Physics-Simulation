package dev.px.physics.System;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Util.Math.Trajectory;
import dev.px.physics.Util.Math.Vector.Vec3d;

import java.util.*;

/**
 * @author James
 */
public class TrajectoriesSystem {

    private final int maxPoints;

    // each object gets its own trajectory
    // LinkedHashMap so they stay in the order they were added
    private final Map<PhysicalObject, Trajectory> trajectories = new LinkedHashMap<>();

    public TrajectoriesSystem(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public TrajectoriesSystem() {
        this(500);
    }

    // putIfAbsent so adding the same object twice doesnt wipe its path
    public void addObject(PhysicalObject obj) {
        trajectories.putIfAbsent(obj, new Trajectory(obj.getPosition(), obj.getVelocity(), maxPoints));
    }

    // same as above but lets one object have a shorter or longer trail than the rest
    public void addObject(PhysicalObject obj, int maxPoints) {
        trajectories.putIfAbsent(obj, new Trajectory(obj.getPosition(), obj.getVelocity(), maxPoints));
    }

    public void addObjects(List<PhysicalObject> objects) {
        for (PhysicalObject obj : objects) {
            addObject(obj);
        }
    }

    public void removeObject(PhysicalObject obj) {
        trajectories.remove(obj);
    }

    public void clear() {
        trajectories.clear();
    }

    // Saves where every object is right now
    // call this once per update (Scene#update does it)
    public void record() {
        for (Map.Entry<PhysicalObject, Trajectory> entry : trajectories.entrySet()) {
            PhysicalObject obj = entry.getKey();
            entry.getValue().record(obj.getPosition(), obj.getVelocity());
        }
    }

    // null if the object isnt being tracked
    public Trajectory getTrajectory(PhysicalObject obj) {
        return trajectories.get(obj);
    }

    public List<Vec3d> getPath(PhysicalObject obj) {
        Trajectory t = trajectories.get(obj);
        return t != null ? t.getPath() : Collections.emptyList();
    }

    // read only so nothing outside can mess with the map
    public Map<PhysicalObject, Trajectory> getAllTrajectories() {
        return Collections.unmodifiableMap(trajectories);
    }

    public Iterable<PhysicalObject> getTrackedObjects() {
        return Collections.unmodifiableSet(trajectories.keySet());
    }

}
