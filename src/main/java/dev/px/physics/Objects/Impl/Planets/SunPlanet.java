package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Util.Math.Vector.Vec3d;

import java.awt.*;

/**
 * @author James
 */
public class SunPlanet extends Planet {

    private static final Color DEEP_ORANGE = new Color(230, 90, 0);
    private static final Color ORANGE = new Color(255, 160, 20);
    private static final Color YELLOW = new Color(255, 230, 120);

    // used to make the surface boil over time
    private double time = 0;

    public SunPlanet(Vec3d position, Vec3d velocity, double mass, double radius) {
        super("Sun", position, velocity, mass, radius);
        this.spinSpeed = 4;
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        super.render(mouseX, mouseY, delta);

        time += delta;
        spin += spinSpeed * delta;

        renderSphere(48);

        // Two layers of glow, a tight bright one and a big faint one
        renderGlow(radius * 0.9, radius * 1.8, new Color(255, 200, 80), 0.55, true);
        renderGlow(radius * 0.9, radius * 4.0, new Color(255, 120, 20), 0.25, true);
    }

    @Override
    protected Color getSurfaceColor(double nx, double ny, double nz) {
        // granulation, the noise slowly moves with time so the surface looks alive
        double n = fakeNoise(nx * 2, ny * 2, nz * 2, time * 0.3);

        // map noise (-1.75 to 1.75) to 0 to 1
        double t = (n + 1.75) / 3.5;

        if (t < 0.5) {
            return mix(DEEP_ORANGE, ORANGE, t * 2);
        }
        return mix(ORANGE, YELLOW, (t - 0.5) * 2);
    }

    @Override
    protected double getBrightness(double nx, double ny, double nz) {
        // The sun makes its own light so no lambert shading
        // instead we do limb darkening, the edges of the sun look darker than the middle
        // because we see the cooler outer layers at a shallow angle
        double facing = Math.max(0, nx * toCamera.x + ny * toCamera.y + nz * toCamera.z);
        return 0.55 + 0.6 * facing;
    }
}
