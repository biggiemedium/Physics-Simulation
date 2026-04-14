package dev.px.physics.System;

/**
 * @author James
 */
public class ScalingSystem {

    // cardinal scale
    // for example 1:10 scale turns original distance (100 -> 10)
    private double scale;

    // bounds of our world
    private double worldSize;

    public ScalingSystem(double worldSize, double scale) {
        this.worldSize = worldSize;
        this.scale = scale;
    }

    public ScalingSystem(double worldSize) {
        this(worldSize, 1.0);
    }

    /**
     * Scale a value from simulation space to render space
     */
    public double scaleToRender(double value) {
        return value * scale;
    }

    /**
     * Scale a value from render space to simulation space
     */
    public double scaleToSimulation(double value) {
        return value / scale;
    }

    /**
     * Convert real-world units (e.g., km) to simulation units
     * Example: if 1 sim unit = 10,000 km, then realToSim(695700) ≈ 69.57
     */
    public double realToSim(double realValue, double realUnitsPerSimUnit) {
        return realValue / realUnitsPerSimUnit;
    }

    /**
     * Convert simulation units to real-world units
     */
    public double simToReal(double simValue, double realUnitsPerSimUnit) {
        return simValue * realUnitsPerSimUnit;
    }

    public double getWorldSize() {
        return worldSize;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setWorldSize(double worldSize) {
        this.worldSize = worldSize;
    }
}
