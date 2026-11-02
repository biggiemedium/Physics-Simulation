package dev.px.physics.Util.Math;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Util.Math.Vector.Vec3d;

/**
 * @author James
 */
public class MathUtil {

    // Linear interpolation, slides between a and b
    // t = 0 gives a, t = 1 gives b, t = 0.5 is halfway
    // a + (b - a) * t
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    // Straight line distance between two points in 3D
    // its just pythagoras with an extra z term
    // sqrt((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)
    public static double distanceBetween(Vec3d A, Vec3d B) {
        double distance = Math.sqrt((A.x - B.x)*(A.x - B.x) + (A.y - B.y)*(A.y - B.y) + (A.z - B.z)*(A.z - B.z));
        return distance;
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }
}
