package dev.px.physics.Objects.Impl;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.Util.Vec3d;
import org.lwjgl.opengl.GL11;

/**
 * @author James
 */
public class Planet extends PhysicalObject implements Renderable {

    private double radius;

    public Planet(Vec3d position, Vec3d velocity, double mass, double radius) {
        super(position, velocity, mass);
        this.radius = radius;
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        GL11.glPushMatrix();

        // move to planet position
        GL11.glTranslated(position.x, position.y, position.z);

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glColor3d(0.2, 0.6, 1.0); // blue planet
        GL11.glVertex2d(0, 0);

        int segments = 32;
        for (int i = 0; i <= segments; i++) {
            // θ = 2π(r)
            double angle = Math.PI * 2 * i / segments;
            double x = Math.cos(angle) * radius;
            double y = Math.sin(angle) * radius;

            GL11.glVertex2d(x, y);
        }

        GL11.glEnd();


        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}

    public double getRadius() {
        return radius;
    }
}
