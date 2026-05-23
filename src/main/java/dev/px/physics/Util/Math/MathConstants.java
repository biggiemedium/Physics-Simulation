package dev.px.physics.Util.Math;

/**
 * @author James
 */
public class MathConstants {

    /**
     * Gravitational constant scaled for simulation units.
     * Real G = 6.67430e-11, but with toy masses (~5-10) and distances (~100 units)
     * the force would be negligible. This scaled value produces visible orbital motion.
     */
    public static final double GRAVITY = 6674.30;

    public static float PI = 3.1415926f;

    public static double phi() {
        double phi = (1 + Math.sqrt(5)) / 2;
        return phi;
    }

}
