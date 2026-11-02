package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Util.Math.Vector.Vec3d;

import java.awt.*;

/**
 * @author James
 */
public class MarsPlanet extends Planet {

    private static final Color DARK_RUST = new Color(120, 50, 30);
    private static final Color RUST = new Color(190, 90, 50);
    private static final Color DUST = new Color(220, 150, 100);
    private static final Color ICE = new Color(240, 230, 225);

    public MarsPlanet(Vec3d position, Vec3d velocity, double mass, double radius) {
        super("Mars", position, velocity, mass, radius);
        this.spinSpeed = 18;
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        spin += spinSpeed * delta;

        renderSphere(32);

        // Mars barely has an atmosphere, just a faint dusty haze
        renderGlow(radius * 0.98, radius * 1.15, new Color(230, 160, 120), 0.2, false);
    }

    @Override
    protected Color getSurfaceColor(double nx, double ny, double nz) {
        double n = fakeNoise(nx * 1.5, ny * 1.5, nz * 1.5, 3.3);

        // small polar caps
        if (Math.abs(ny) > 0.93) {
            return ICE;
        }

        // map noise (-1.75 to 1.75) to 0 to 1
        double t = (n + 1.75) / 3.5;

        if (t < 0.45) {
            return mix(DARK_RUST, RUST, t / 0.45);
        }
        return mix(RUST, DUST, (t - 0.45) / 0.55);
    }
}
