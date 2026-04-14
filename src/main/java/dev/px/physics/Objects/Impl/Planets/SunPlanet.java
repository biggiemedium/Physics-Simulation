package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Util.Math.MathConstants;
import dev.px.physics.Util.Math.Vector.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_QUAD_STRIP;

/**
 * @author James
 */
public class SunPlanet extends Planet {

    public SunPlanet(Vec3d position, Vec3d velocity, double mass, double radius) {
        super(position, velocity, mass, radius);
    }

    // https://stackoverflow.com/questions/7687148/drawing-sphere-in-opengl-without-using-glusphere
    /*
    We approximate a continuous sphere using a discrete parametric grid.
    Let:
        N = number of latitude segments
        M = number of longitude segments
        r = radius of sphere

    We define two angles:
        φ_i = -π/2 + (i * π / N)          for i = 0, 1, ..., N
        θ_j = (2π * j / M)                for j = 0, 1, ..., M

    These correspond to:
        φ (phi)  = latitude  (bottom → top)
        θ (theta)= longitude (around Y-axis)

   Each vertex on the sphere is defined by the parametric mapping:

        x(i, j) = r * cos(φ_i) * cos(θ_j)
        y(i, j) = r * cos(φ_i) * sin(θ_j)
        z(i, j) = r * sin(φ_i)
     */
    @Override
    public void render(double mouseX, double mouseY, double delta) {
        super.render(mouseX, mouseY, delta);


        GL11.glPushMatrix();

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        // Recall: (x - a)² + (y - b)² + (z - c)² = r² (sphere)
        // where (a, b, c) is the center and r is the radius

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
            Color c = new Color(255, 175, 1, 255);
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
    }


}
