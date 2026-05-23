package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.Util.Math.Vector.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

/**
 * @author James
 *
 * Debris is small irregular rocky fragments scattered in the scene.
 * Each piece is rendered as a tiny jagged polygon built from N vertices
 * distributed around a circle with a small random radius perturbation:
 *
 *     x_i = r_i * cos(2π * i / N)
 *     y_i = r_i * sin(2π * i / N)
 *
 * where r_i = baseRadius + random(-jitter, jitter)
 * This breaks the perfect circle and gives a rough, rocky appearance.
 */
public class Debris extends PhysicalObject implements Renderable {

    private static final int VERTICES = 6;

    // Per-instance vertex offsets baked at construction — shape never changes
    private final double[] vertexOffsetX = new double[VERTICES];
    private final double[] vertexOffsetY = new double[VERTICES];

    private final Color color;

    public Debris(Vec3d position, Vec3d velocity, double mass, double radius, Color color, long seed) {
        super(position, velocity, mass);
        this.color = color;

        Random rng = new Random(seed);
        double jitter = radius * 0.4;

        for (int i = 0; i < VERTICES; i++) {
            // Evenly space vertices around the circle, then perturb radius
            double angle = 2 * Math.PI * i / VERTICES;
            double r = radius + rng.nextDouble() * jitter * 2 - jitter;
            vertexOffsetX[i] = r * Math.cos(angle);
            vertexOffsetY[i] = r * Math.sin(angle);
        }
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        Vec3d pos = getPosition();

        GL11.glPushMatrix();
        GL11.glTranslated(pos.x, pos.y, pos.z);

        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                0.85f
        );

        for (int i = 0; i < VERTICES; i++) {
            GL11.glVertex3d(vertexOffsetX[i], vertexOffsetY[i], 0);
        }

        GL11.glEnd();
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}
}