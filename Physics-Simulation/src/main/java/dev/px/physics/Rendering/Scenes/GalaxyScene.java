package dev.px.physics.Rendering.Scenes;

import dev.px.physics.Objects.Impl.Planets.EarthPlanet;
import dev.px.physics.Objects.Impl.Planets.SunPlanet;
import dev.px.physics.Rendering.Camera;
import dev.px.physics.Objects.Impl.PlaneVisualization;
import dev.px.physics.Rendering.Scenes.Api.Scene;
import dev.px.physics.Objects.Impl.StarField;
import dev.px.physics.Util.Math.Vector.Vec2d;
import dev.px.physics.Util.Math.Vector.Vec3d;

/**
 * @author James
 */
public class GalaxyScene extends Scene {

    @Override
    public void onLoad() {

        camera = new Camera(new Vec3d(0, 0, 50), new Vec2d(0, 0));

        double worldSize = scalingSystem != null ? scalingSystem.getWorldSize() : 500;

        StarField starField = new StarField(1000, worldSize);
        addRenderable(starField);

        PlaneVisualization planeVisualization = new PlaneVisualization(scalingSystem, objects);
        addRenderable(planeVisualization);


        SunPlanet sun = new SunPlanet(
                new Vec3d(0, 0, 0),
                new Vec3d(0, 0, 0),
                10,
                15
        );

        // EARTH
        EarthPlanet earth = new EarthPlanet(
                new Vec3d(100, 0, 0), // Position along x-axis
                new Vec3d(0, 0, 0), // Velocity along z-axis for circular orbit
                5,
                10
        );

        addObject(sun);
        addRenderable(sun);

        addObject(earth);
        addRenderable(earth);
    }

    @Override
    public void update(double delta) {
        super.update(delta);
    }
}
