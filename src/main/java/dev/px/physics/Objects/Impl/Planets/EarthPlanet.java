package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.Util.Math.Vector.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author James
 */
public class EarthPlanet extends Planet implements Renderable {

    public EarthPlanet(Vec3d position, Vec3d velocity, double mass, double radius) {
        super(position, velocity, mass, radius);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        GL11.glPushMatrix();

        // Translate to Earth's position
        GL11.glTranslated(getPosition().x, getPosition().y, getPosition().z);

        int segments = 32;
        int latitude = segments;
        int longitude = segments;

        for(int i = 0; i <= latitude; i++) {
            double lat0 = Math.PI * (-0.5 + (double) (i - 1) / latitude);
            double z0  = Math.sin(lat0);
            double zr0 =  Math.cos(lat0);

            double lat1 = Math.PI * (-0.5 + (double) i / latitude);
            double z1 = Math.sin(lat1);
            double zr1 = Math.cos(lat1);

            GL11.glBegin(GL11.GL_QUAD_STRIP);
            Color c = new Color(36, 99, 229, 255);
            GL11.glColor3d((double) c.getRed() / 255, (double) c.getGreen() / 255, (double) c.getBlue() / 255);

            for(int j = 0; j <= longitude; j++) {
                double lng = 2 * Math.PI * (double) (j - 1) / longitude;
                double x = Math.cos(lng);
                double y = Math.sin(lng);

                GL11.glNormal3d(x * zr0, y * zr0, z0);
                GL11.glVertex3d(radius * x * zr0, radius * y * zr0, radius * z0);
                GL11.glNormal3d(x * zr1, y * zr1, z1);
                GL11.glVertex3d(radius * x * zr1, radius * y * zr1, radius * z1);
            }
            GL11.glEnd();
        }

        GL11.glPopMatrix();

        //GL11.glBegin(GL11.GL_LINES);
        //GL11.glColor3f(1, 1, 1);
        //GL11.glVertex3d(getPosition().x, -500 * 2, getPosition().z);
        //GL11.glVertex3d(getPosition().x, 500 * 2, getPosition().z);
        //GL11.glEnd();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}
}
