package dev.px.physics.Util.Math;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Util.Math.Vector.Vec3d;

/**
 * @author James
 */
public class MathUtil {

    /**
     * Performs linear interpolation between two values.
     *
     * <p>Given two values {@code a} and {@code b}, and a parameter {@code t} in the range [0, 1],
     * this returns a value proportionally between them.</p>
     *
     * <p>Formula: a + (b - a) * t</p>
     *
     * @param a the start value
     * @param b the end value
     * @param t interpolation factor (typically between 0 and 1)
     * @return interpolated value between a and b
     */
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    /**
     * Computes the Euclidean distance between two 3D vectors.
     *
     * <p>This uses the standard distance formula in 3D space:</p>
     *
     * <p>sqrt((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)</p>
     *
     * @param A first position vector
     * @param B second position vector
     * @return the straight-line distance between A and B
     */
    public static double distanceBetween(Vec3d A, Vec3d B) {
        double distance = Math.sqrt((A.x - B.x)*(A.x - B.x) + (A.y - B.y)*(A.y - B.y) + (A.z - B.z)*(A.z - B.z));
        return distance;
    }

}
