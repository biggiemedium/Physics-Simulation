package dev.px.physics.Objects.Impl;

import dev.px.physics.Objects.Impl.Planets.Planet;
import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.System.ScalingSystem;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * @author James
 */
public class PlaneVisualization implements Renderable {

    private ScalingSystem scalingSystem;
    private int gridDivisions;

    private List<PhysicalObject> objects;

    public PlaneVisualization(ScalingSystem scalingSystem, int gridDivisions, List<PhysicalObject> objects) {
        this.scalingSystem = scalingSystem;
        this.gridDivisions = gridDivisions;
        this.objects = objects;
    }

    public PlaneVisualization(ScalingSystem scalingSystem, List<PhysicalObject> objects) {
        this(scalingSystem, 50, objects);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        if (scalingSystem == null) {
            return; // Can't render without scaling info
        }

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glBegin(GL11.GL_LINES);

        double gridSize = scalingSystem.getWorldSize();
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
        GL11.glVertex3d(0, -gridSize * 2, 0);
        GL11.glVertex3d(0, gridSize * 2, 0);

        GL11.glEnd();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}

}
