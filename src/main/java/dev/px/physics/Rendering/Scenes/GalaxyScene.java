package dev.px.physics.Rendering.Scenes;

import dev.px.physics.Objects.Impl.Planet;
import dev.px.physics.Rendering.Camera;
import dev.px.physics.Objects.Impl.PlaneVisualization;
import dev.px.physics.Rendering.Scenes.Api.Scene;
import dev.px.physics.Objects.Impl.StarField;
import dev.px.physics.Util.Vec2d;
import dev.px.physics.Util.Vec3d;

/**
 * @author James
 */
public class GalaxyScene extends Scene {

    @Override
    public void onLoad() {

        camera = new Camera(new Vec3d(0, 0, 50), new Vec2d(0, 0));

        StarField starField = new StarField(1000, 500);
        addRenderable(starField);

        PlaneVisualization planeVisualization = new PlaneVisualization();
        addRenderable(planeVisualization);

        Planet sun = new Planet(
                new Vec3d(0, 0, 0),
                new Vec3d(0, 0, 0),
                1.989e30,
                10
        );

        Planet earth = new Planet(
                new Vec3d(150, 0, 0),
                new Vec3d(0, 0, 30),
                5.972e24,
                5
        );

        addObject(earth);
        addRenderable(earth);

        addObject(sun);
        addRenderable(sun);
    }

    @Override
    public void update(double delta) {
        super.update(delta);
    }
}
