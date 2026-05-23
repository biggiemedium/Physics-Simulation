package dev.px.physics.Rendering.Scenes;

import dev.px.physics.Objects.Impl.Planets.*;
import dev.px.physics.Objects.Impl.TrajectoryVisualization;
import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Camera;
import dev.px.physics.Objects.Impl.PlaneVisualization;
import dev.px.physics.Rendering.Scenes.Api.Scene;
import dev.px.physics.Objects.Impl.StarField;
import dev.px.physics.System.TrajectoriesSystem;
import dev.px.physics.Util.Math.MathConstants;
import dev.px.physics.Util.Math.MathUtil;
import dev.px.physics.Util.Math.Vector.Vec2d;
import dev.px.physics.Util.Math.Vector.Vec3d;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

/**
 * @author James
 */
public class GalaxyScene extends Scene {

    @Override
    public void init(long windowHandle) {
        super.init(windowHandle);
        addRenderable(new TrajectoryVisualization(trajectoriesSystem));
    }

    @Override
    public void onLoad() {

        camera = new Camera(new Vec3d(0, 0, 50), new Vec2d(0, 0));

        double worldSize = scalingSystem != null ? scalingSystem.getWorldSize() : 500;

        StarField starField = new StarField(1000, worldSize);
        addRenderable(starField);

        PlaneVisualization planeVisualization = new PlaneVisualization(scalingSystem, objects);
        addRenderable(planeVisualization);

        double sunMass = 10;
        double orbitRadius = 100;

        SunPlanet sun = new SunPlanet(
                new Vec3d(0, 0, 0),
                new Vec3d(0, 0, 0),
                sunMass,
                15
        );
        sun.setPinned(true);

        // https://physics.stackexchange.com/questions/78591/orbital-speed-for-a-circular-orbit
        // Circular orbit velocity: v = sqrt(G * M / r)
        double orbitalVelocity = Math.sqrt(MathConstants.GRAVITY * sunMass / orbitRadius);

        // EARTH
        EarthPlanet earth = new EarthPlanet(
                new Vec3d(orbitRadius, 0, 0),
                new Vec3d(0, 0, orbitalVelocity),
                5,
                10
        );

        double earthMass = 5;
        double moonOrbitRadius = 18;

        // Circular orbit velocity around Earth: v = sqrt(G * M_earth / r)
        // This is relative to Earth -> so we add Earth's own velocity on top,
        // otherwise the moon orbits the sun instead of Earth
        double moonOrbitalVelocity = Math.sqrt(MathConstants.GRAVITY * earthMass / moonOrbitRadius);

        MoonPlanet moon = new MoonPlanet(
                new Vec3d(orbitRadius + moonOrbitRadius, 0, 0),  // Earth pos + offset
                new Vec3d(0, 0, orbitalVelocity + moonOrbitalVelocity), // Earth vel + moon's own orbit vel
                0.5,
                3
        );

        addObject(moon);
        addRenderable(moon);

        addObject(sun);
        addRenderable(sun);

        addObject(earth);
        addRenderable(earth);

        TrajectoryVisualization trajectoryVisualization = new TrajectoryVisualization(trajectoriesSystem);
        addRenderable(trajectoryVisualization);

       // spawnMeteors(sunMass);
       // spawnDebris();

        // I genuinely have no idea how this breaks rotation alignment but it does
       // removeIfBoundsViolated();
    }

    private void removeIfBoundsViolated() {
        Iterator<PhysicalObject> it = this.objects.iterator();

        Vec3d origin = new Vec3d(0, 0, 0);

        double maxRadius = (scalingSystem != null) ? scalingSystem.getWorldSize() : 1000.0;

        while (it.hasNext()) {
            PhysicalObject p = it.next();


            double dist = MathUtil.distanceBetween(p.getPosition(), origin);

            if (dist > maxRadius) {
                it.remove();
            }
        }
    }


    private void spawnMeteors(double sunMass) {
        Random rng = new Random(42);
        int count = 6;

        Color[] meteorColors = {
                new Color(255, 240, 200),
                new Color(200, 220, 255),
                new Color(255, 200, 150),
        };

        for (int i = 0; i < count; i++) {
            double spawnDist = 180 + rng.nextDouble() * 80;
            double angle = rng.nextDouble() * 2 * Math.PI;

            Vec3d pos = new Vec3d(
                    spawnDist * Math.cos(angle),
                    (rng.nextDouble() - 0.5) * 20, // vertical spread, it looks weird if its all on same Y
                    spawnDist * Math.sin(angle)
            );

            // v_escape at this distance -> multiply by 1.1–1.4 so it's unbound
            double escapeSpeed = Math.sqrt(2 * MathConstants.GRAVITY * sunMass / spawnDist);
            double speed = escapeSpeed * (1.1 + rng.nextDouble() * 0.3);

            // Velocity aimed roughly toward origin (with small offset)
            Vec3d toward = pos.copy().normalize().multiply(-1);
            Vec3d vel = new Vec3d(
                    toward.x * speed + (rng.nextDouble() - 0.5) * speed * 0.3,
                    (rng.nextDouble() - 0.5) * speed * 0.1,
                    toward.z * speed + (rng.nextDouble() - 0.5) * speed * 0.3
            );

            Color color = meteorColors[i % meteorColors.length];
            Meteor meteor = new Meteor(pos, vel, 0.1, color);

            addObject(meteor);
            addRenderable(meteor);
        }
    }

    private void spawnDebris() {
        Random rng = new Random(7);
        int count = 40;

        double sunMass = 10;
        double beltInner = 130;
        double beltOuter = 170;

        Color[] debrisColors = {
                new Color(160, 140, 120),
                new Color(130, 120, 110),
                new Color(180, 160, 130),
        };

        for (int i = 0; i < count; i++) {
            double r = beltInner + rng.nextDouble() * (beltOuter - beltInner);
            double angle = rng.nextDouble() * 2 * Math.PI;

            Vec3d pos = new Vec3d(
                    r * Math.cos(angle),
                    (rng.nextDouble() - 0.5) * 8, // vertical spread mulptipler
                    r * Math.sin(angle)
            );

            // Base circular speed + small perturbation (±8%) for elliptical variety
            double circularSpeed = Math.sqrt(MathConstants.GRAVITY * sunMass / r);
            double speed = circularSpeed * (0.92 + rng.nextDouble() * 0.16);

            // Tangent in XZ plane: perpendicular to radial direction is (-sin, 0, cos)
            Vec3d vel = new Vec3d(
                    -Math.sin(angle) * speed,
                    (rng.nextDouble() - 0.5) * speed * 0.05,
                    Math.cos(angle) * speed
            );

            double radius = 0.8 + rng.nextDouble() * 1.4;
            Color color = debrisColors[i % debrisColors.length];

            Debris debris = new Debris(pos, vel, 0.05, radius, color, rng.nextLong());

            addObject(debris);
            addRenderable(debris);
        }
    }

    @Override
    public void update(double delta) {
        super.update(delta);
    }
}
