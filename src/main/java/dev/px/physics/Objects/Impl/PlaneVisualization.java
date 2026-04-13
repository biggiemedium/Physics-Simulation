package dev.px.physics.Objects.Impl;

import dev.px.physics.Rendering.Scenes.Api.Renderable;
import org.lwjgl.opengl.GL11;

/**
 * @author James
 */
public class PlaneVisualization implements Renderable {

    private double gridSize;
    private int gridDivisions;

    public PlaneVisualization(double gridSize, int gridDivisions) {
        this.gridSize = gridSize;
        this.gridDivisions = gridDivisions;
    }

    public PlaneVisualization() {
        this(500, 50);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glBegin(GL11.GL_LINES);
        double halfSize = gridSize / 2.0;
        double step = gridSize / gridDivisions;

        // Z axis lines
        for (int i = 0; i <= gridDivisions; i++) {
            double z = -halfSize + (i * step);

            if (i == gridDivisions / 2) {
                GL11.glColor3f(1, 1, 1);
            } else {
                GL11.glColor3f(0.2f, 0.2f, 0.2f); // dim grey
            }

            GL11.glVertex3d(-halfSize, 0, z);
            GL11.glVertex3d(halfSize, 0, z);
        }

        // X axis lines
        for (int i = 0; i <= gridDivisions; i++) {
            double x = -halfSize + (i * step);

            if (i == gridDivisions / 2) {
                GL11.glColor3f(1, 1, 1);
            } else {
                GL11.glColor3f(0.2f, 0.2f, 0.2f); // dim grey
            }

            GL11.glVertex3d(x, 0, -halfSize);
            GL11.glVertex3d(x, 0, halfSize);
        }

        // Y-Axis line
        GL11.glColor3f(1, 1, 1);
        GL11.glVertex3d(0, -1000, 0);
        GL11.glVertex3d(0, 1000, 0);

        GL11.glEnd();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {

    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {

    }
}
