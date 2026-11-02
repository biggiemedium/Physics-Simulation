package dev.px.physics.Objects.Impl.Planets;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.Util.Math.Vector.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author James
 */
public class Planet extends PhysicalObject implements Renderable {

    protected double radius;
    protected String name;

    // Where the light comes from
    // the sun is pinned at the origin so this is fine for now
    protected Vec3d lightSource = new Vec3d(0, 0, 0);

    // Spin around the Y axis in degrees
    protected double spin = 0;
    protected double spinSpeed = 0;

    // Worked out every frame in renderSphere so the colour/brightness hooks can use them
    protected Vec3d toLight = new Vec3d(0, 1, 0);
    protected Vec3d toCamera = new Vec3d(0, 0, 1);

    public Planet(String name, Vec3d position, Vec3d velocity, double mass, double radius) {
        super(position, velocity, mass);
        this.name = name;
        this.radius = radius;
    }

    public Planet(Vec3d position, Vec3d velocity, double mass, double radius) {
        this(null, position, velocity, mass, radius);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {

    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}

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
        y(i, j) = r * sin(φ_i)
        z(i, j) = r * cos(φ_i) * sin(θ_j)

   Y is up here so the poles point up out of the orbit plane
     */
    protected void renderSphere(int segments) {
        Vec3d pos = getPosition();

        // direction from the planet to the light and to the camera
        // has to happen before we translate or the camera position is wrong
        this.toLight = lightSource.copy().subtract(pos).normalize();
        this.toCamera = getCameraPosition().subtract(pos).normalize();

        double spinRad = Math.toRadians(spin);

        GL11.glPushMatrix();
        GL11.glTranslated(pos.x, pos.y, pos.z);

        for(int i = 0; i < segments; i++) {
            double lat0 = Math.PI * (-0.5 + (double) i / segments);
            double lat1 = Math.PI * (-0.5 + (double) (i + 1) / segments);

            GL11.glBegin(GL11.GL_QUAD_STRIP);

            for(int j = 0; j <= segments; j++) {
                double lng = 2 * Math.PI * (double) j / segments;

                sphereVertex(lat1, lng, spinRad);
                sphereVertex(lat0, lng, spinRad);
            }

            GL11.glEnd();
        }

        GL11.glPopMatrix();
    }

    private void sphereVertex(double lat, double lng, double spinRad) {
        // unit normal, on a sphere the normal is just the point divided by the radius
        double nx = Math.cos(lat) * Math.cos(lng);
        double ny = Math.sin(lat);
        double nz = Math.cos(lat) * Math.sin(lng);

        // We spin the surface colours instead of the mesh
        // rotate the normal around Y:
        // x' = x cosθ + z sinθ
        // z' = -x sinθ + z cosθ
        double sx = nx * Math.cos(spinRad) + nz * Math.sin(spinRad);
        double sz = -nx * Math.sin(spinRad) + nz * Math.cos(spinRad);

        Color c = getSurfaceColor(sx, ny, sz);
        double brightness = getBrightness(nx, ny, nz);

        GL11.glColor4d(
                Math.min(1.0, c.getRed() / 255.0 * brightness),
                Math.min(1.0, c.getGreen() / 255.0 * brightness),
                Math.min(1.0, c.getBlue() / 255.0 * brightness),
                c.getAlpha() / 255.0
        );
        GL11.glNormal3d(nx, ny, nz);
        GL11.glVertex3d(radius * nx, radius * ny, radius * nz);
    }

    /**
     * Colour of the surface at a point, the normal passed in is already spun
     */
    protected Color getSurfaceColor(double nx, double ny, double nz) {
        return Color.WHITE;
    }

    /**
     * Lambert lighting: brightness = n · L
     * faces pointing at the sun are bright, faces pointing away go dark
     */
    protected double getBrightness(double nx, double ny, double nz) {
        double diffuse = Math.max(0, nx * toLight.x + ny * toLight.y + nz * toLight.z);
        return 0.08 + 0.92 * diffuse; // small ambient so the night side isnt pure black
    }

    /**
     * Draws a soft ring around the planet that always faces the camera (billboard)
     * alpha goes from innerAlpha at innerRadius down to 0 at outerRadius
     */
    protected void renderGlow(double innerRadius, double outerRadius, Color color, double innerAlpha, boolean additive) {
        Vec3d pos = getPosition();

        double[] m = new double[16];
        GL11.glGetDoublev(GL11.GL_MODELVIEW_MATRIX, m);

        // The rows of the camera rotation are the cameras right and up vectors in world space
        // OpenGL is column major so row 0 is m[0], m[4], m[8]
        Vec3d right = new Vec3d(m[0], m[4], m[8]);
        Vec3d up = new Vec3d(m[1], m[5], m[9]);

        double r = color.getRed() / 255.0;
        double g = color.getGreen() / 255.0;
        double b = color.getBlue() / 255.0;

        GL11.glPushMatrix();
        GL11.glTranslated(pos.x, pos.y, pos.z);

        // Dont write depth or the glow would hide things behind it
        GL11.glDepthMask(false);
        if (additive) {
            // adding light on top instead of blending makes it look like it glows
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        }

        int segments = 48;
        GL11.glBegin(GL11.GL_QUAD_STRIP);

        for(int i = 0; i <= segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            // point = right * cos + up * sin
            double dx = right.x * cos + up.x * sin;
            double dy = right.y * cos + up.y * sin;
            double dz = right.z * cos + up.z * sin;

            GL11.glColor4d(r, g, b, innerAlpha);
            GL11.glVertex3d(dx * innerRadius, dy * innerRadius, dz * innerRadius);

            GL11.glColor4d(r, g, b, 0);
            GL11.glVertex3d(dx * outerRadius, dy * outerRadius, dz * outerRadius);
        }

        GL11.glEnd();

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);

        GL11.glPopMatrix();
    }

    // Pull the camera position back out of the modelview matrix
    // modelview = R * T(-eye) so the translation column is t = -R * eye
    // which means eye = -Rᵀ * t
    protected Vec3d getCameraPosition() {
        double[] m = new double[16];
        GL11.glGetDoublev(GL11.GL_MODELVIEW_MATRIX, m);

        double tx = m[12];
        double ty = m[13];
        double tz = m[14];

        return new Vec3d(
                -(m[0] * tx + m[1] * ty + m[2] * tz),
                -(m[4] * tx + m[5] * ty + m[6] * tz),
                -(m[8] * tx + m[9] * ty + m[10] * tz)
        );
    }

    // Not real perlin noise, just a few sine waves stacked on top of each other
    // its enough to break the sphere up into blobs, roughly -1.75 to 1.75
    protected static double fakeNoise(double x, double y, double z, double seed) {
        double n = Math.sin(x * 3.1 + seed) * Math.sin(y * 2.7 + seed * 1.3) * Math.sin(z * 3.4 + seed * 0.7);
        n += 0.5 * Math.sin(x * 7.3 + y * 5.1 + seed) * Math.sin(z * 6.2 - y * 3.3 + seed);
        n += 0.25 * Math.sin(x * 13.7 - z * 11.3 + seed) * Math.sin(y * 12.1 + seed * 2.0);
        return n;
    }

    // mix two colours, t = 0 gives a and t = 1 gives b
    protected static Color mix(Color a, Color b, double t) {
        t = Math.max(0, Math.min(1, t));
        return new Color(
                (int) (a.getRed() + (b.getRed() - a.getRed()) * t),
                (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t),
                (int) (a.getAlpha() + (b.getAlpha() - a.getAlpha()) * t)
        );
    }

    public double getRadius() {
        return radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLightSource(Vec3d lightSource) {
        this.lightSource = lightSource;
    }
}
