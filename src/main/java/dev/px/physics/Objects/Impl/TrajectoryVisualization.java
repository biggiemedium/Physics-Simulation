package dev.px.physics.Objects.Impl;

import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.System.TrajectoriesSystem;
import dev.px.physics.Util.Math.Vector.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * @author James
 */
public class TrajectoryVisualization implements Renderable {

    private TrajectoriesSystem trajectories;

    public TrajectoryVisualization(TrajectoriesSystem trajectories) {
        this.trajectories = trajectories;
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        if(this.trajectories == null) return;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        this.trajectories.getAllTrajectories().forEach((object, trajectory) -> {
            List<Vec3d> path = trajectory.getPath();
            if (path.size() < 2) return;

            GL11.glBegin(GL11.GL_LINE_STRIP);

            for (int i = 0; i < path.size(); i++) {
                // Fade alpha from 0 (oldest) to 1 (newest) to show direction of travel
                float t = (float) i / (float) (path.size() - 1);

                GL11.glColor4f(1.0f, 1.0f, 1.0f, t);

                Vec3d point = path.get(i);
                GL11.glVertex3d(point.x, point.y, point.z);
            }

            GL11.glEnd();
        });

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
