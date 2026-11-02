package dev.px.physics.Objects.Impl;

import dev.px.physics.Objects.Impl.Planets.Planet;
import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Graphics.NanoVGRenderer;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.Util.Math.Vector.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * @author James
 *
 * Draws the planet names above each planet
 * Has to be the last renderable added so the text goes over everything
 */
public class NameTagVisualization implements Renderable {

    private List<PhysicalObject> objects;
    private NanoVGRenderer nvgRenderer;

    public NameTagVisualization(List<PhysicalObject> objects) {
        this.objects = objects;
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        // NanoVG needs the GL context so we wait till the first frame to make it
        if (nvgRenderer == null) {
            nvgRenderer = new NanoVGRenderer(0, 0);
            nvgRenderer.init();
        }

        // Grab the matrices while the camera transform is still applied
        double[] modelView = new double[16];
        double[] projection = new double[16];
        int[] viewport = new int[4];
        GL11.glGetDoublev(GL11.GL_MODELVIEW_MATRIX, modelView);
        GL11.glGetDoublev(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);

        // Camera up vector in world space, row 1 of the modelview rotation
        // using this instead of world Y so the tag stays above the planet when looking down at it
        Vec3d up = new Vec3d(modelView[1], modelView[5], modelView[9]);

        nvgRenderer.setSize(viewport[2], viewport[3]);
        nvgRenderer.beginFrame();

        for (PhysicalObject obj : objects) {
            if (!(obj instanceof Planet)) {
                continue;
            }

            Planet planet = (Planet) obj;
            if (planet.getName() == null) {
                continue;
            }

            // point at the top of the planet
            Vec3d top = planet.getPosition().copy().add(up.copy().multiply(planet.getRadius()));

            double[] screen = worldToScreen(top, modelView, projection, viewport);
            if (screen == null) {
                continue; // behind the camera
            }

            // little gap so the text isnt touching the planet
            nvgRenderer.renderCenteredTextWithShadow(planet.getName(), (float) screen[0], (float) screen[1] - 6, 16);
        }

        nvgRenderer.endFrame();
    }

    // clip = projection * modelView * (x, y, z, 1)
    // then divide by w to get normalized device coords (-1 to 1) and stretch that over the viewport
    private double[] worldToScreen(Vec3d point, double[] modelView, double[] projection, int[] viewport) {
        double[] eye = multiply(modelView, point.x, point.y, point.z, 1);
        double[] clip = multiply(projection, eye[0], eye[1], eye[2], eye[3]);

        // w is the distance in front of the camera, negative means its behind us
        if (clip[3] <= 0) {
            return null;
        }

        double ndcX = clip[0] / clip[3];
        double ndcY = clip[1] / clip[3];

        double screenX = viewport[0] + (ndcX + 1) / 2.0 * viewport[2];
        // NanoVG has y going down and OpenGL has it going up so flip it
        double screenY = viewport[1] + (1 - ndcY) / 2.0 * viewport[3];

        return new double[] { screenX, screenY };
    }

    // OpenGL matrices are column major so the element at (row, col) is m[col * 4 + row]
    private double[] multiply(double[] m, double x, double y, double z, double w) {
        return new double[] {
                m[0] * x + m[4] * y + m[8] * z + m[12] * w,
                m[1] * x + m[5] * y + m[9] * z + m[13] * w,
                m[2] * x + m[6] * y + m[10] * z + m[14] * w,
                m[3] * x + m[7] * y + m[11] * z + m[15] * w
        };
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}
}
