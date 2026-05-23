package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.Util.Math.Vector.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author James
 */
public class Meteor extends PhysicalObject implements Renderable {

    // How long the visual tail appears relative to velocity magnitude
    private static final double TAIL_SCALE = 0.4;

    private final Color color;
    private Vec3d prevPosition;

    public Meteor(Vec3d position, Vec3d velocity, double mass, Color color) {
        super(position, velocity, mass);
        this.color = color;
        this.prevPosition = position.copy();
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        Vec3d pos = getPosition();

        // tail tip = current position offset backwards along velocity direction
        // tail = pos - normalize(velocity) * speed * TAIL_SCALE
        Vec3d vel = getVelocity();
        double speed = vel.length();
        Vec3d tail = pos.copy().subtract(vel.copy().normalize().multiply(speed * TAIL_SCALE));

        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_LINES);

        GL11.glColor4f(
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                1.0f
        );
        GL11.glVertex3d(pos.x, pos.y, pos.z);

        // Tail -> fully transparent
        GL11.glColor4f(
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                0.0f
        );
        GL11.glVertex3d(tail.x, tail.y, tail.z);

        GL11.glEnd();

        GL11.glPointSize(3.0f);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glColor4f(
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                1.0f
        );
        GL11.glVertex3d(pos.x, pos.y, pos.z);
        GL11.glEnd();

        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}
}
