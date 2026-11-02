package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Util.Math.Vector.Vec3d;

import java.awt.*;

/**
 * @author James
 */
public class MoonPlanet extends Planet {

    private static final Color MOON_GREY = new Color(180, 175, 170);
    private static final Color MARIA = new Color(110, 108, 105); // the dark patches on the moon

    public MoonPlanet(Vec3d position, Vec3d velocity, double mass, double radius) {
        super("Moon", position, velocity, mass, radius);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        renderSphere(24);
    }

    @Override
    protected Color getSurfaceColor(double nx, double ny, double nz) {
        double n = fakeNoise(nx * 2, ny * 2, nz * 2, 5.1);
        return n > 0.6 ? MARIA : MOON_GREY;
    }
}
