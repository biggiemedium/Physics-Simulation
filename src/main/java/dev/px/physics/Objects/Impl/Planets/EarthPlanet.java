package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.Util.Math.Vector.Vec3d;

import java.awt.*;

/**
 * @author James
 */
public class EarthPlanet extends Planet implements Renderable {

    private static final Color DEEP_OCEAN = new Color(12, 40, 120);
    private static final Color SHALLOW_OCEAN = new Color(36, 99, 229);
    private static final Color GRASS = new Color(56, 130, 60);
    private static final Color DESERT = new Color(170, 145, 90);
    private static final Color ICE = new Color(235, 240, 250);

    public EarthPlanet(Vec3d position, Vec3d velocity, double mass, double radius) {
        super("Earth", position, velocity, mass, radius);
        this.spinSpeed = 20;
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        spin += spinSpeed * delta;

        renderSphere(48);

        // Atmosphere, a thin blue haze around the edge
        renderGlow(radius * 0.98, radius * 1.25, new Color(110, 170, 255), 0.45, false);

        //GL11.glBegin(GL11.GL_LINES);
        //GL11.glColor3f(1, 1, 1);
        //GL11.glVertex3d(getPosition().x, -500 * 2, getPosition().z);
        //GL11.glVertex3d(getPosition().x, 500 * 2, getPosition().z);
        //GL11.glEnd();
    }

    @Override
    protected Color getSurfaceColor(double nx, double ny, double nz) {
        double n = fakeNoise(nx, ny, nz, 1.7);

        // Ice caps, wobble the edge with the noise so its not a perfect circle
        if (Math.abs(ny) + n * 0.05 > 0.88) {
            return ICE;
        }

        // Above 0.15 is land, below is ocean
        if (n > 0.15) {
            // drier the further from the poles
            double dryness = fakeNoise(nx * 2, ny * 2, nz * 2, 4.2) * 0.5 + (1 - Math.abs(ny)) * 0.5;
            return mix(GRASS, DESERT, dryness);
        }

        // Ocean gets lighter near the coast
        return mix(DEEP_OCEAN, SHALLOW_OCEAN, (n + 1.75) / 1.9);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}
}
