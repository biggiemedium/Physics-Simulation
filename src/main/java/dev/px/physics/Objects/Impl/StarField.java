package dev.px.physics.Objects.Impl;

import dev.px.physics.Rendering.Scenes.Api.Renderable;
import org.lwjgl.opengl.GL11;

import java.util.Random;

/**
 * @author James
 */
public class StarField implements Renderable {

    private Star[] stars;

    public StarField(int numStars, double radius) {
        stars = new Star[numStars];
        Random random = new Random();

        for(int i = 0; i < numStars; i++) {

            // 0 to 2π means a full circle
            double theta = random.nextDouble() * Math.PI * 2;
            double phi = Math.acos(2 * random.nextDouble() - 1);

            double x = radius * Math.sin(phi) * Math.cos(theta);
            double y = radius * Math.sin(phi) * Math.sin(theta);
            double z = radius * Math.cos(phi);

            double brightness = 0.3 + random.nextDouble() * 0.7;

            stars[i] = new Star(x, y, z, brightness);
        }
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        GL11.glDisable(GL11.GL_DEPTH_TEST); // Stars always in background

        GL11.glPointSize(2.0f);
        GL11.glBegin(GL11.GL_POINTS);

        for (Star star : stars) {
            float bright = (float) star.brightness;
            GL11.glColor3f(bright, bright, bright);
            GL11.glVertex3d(star.x, star.y, star.z);
        }

        GL11.glEnd();

        GL11.glEnable(GL11.GL_DEPTH_TEST); // Re-enable for other objects
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}

    private static class Star {
        double x, y, z;
        double brightness;

        Star(double x, double y, double z, double brightness) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.brightness = brightness;
        }
    }
}
