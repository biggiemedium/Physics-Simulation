package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.Util.Math.Vector.Vec3d;

/**
 * @author James
 */
public class Planet extends PhysicalObject implements Renderable {

    protected double radius;

    public Planet(Vec3d position, Vec3d velocity, double mass, double radius) {
        super(position, velocity, mass);
        this.radius = radius;
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {

    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}

    public double getRadius() {
        return radius;
    }
}
