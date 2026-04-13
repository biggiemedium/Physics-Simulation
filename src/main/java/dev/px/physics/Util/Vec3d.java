package dev.px.physics.Util;

/**
 * @author James
 */
public class Vec3d {

    public double x, y, z;

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d add(Vec3d v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    public Vec3d add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3d subtract(Vec3d v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    public Vec3d subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3d multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public Vec3d multiply(Vec3d v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }

    public Vec3d divide(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        return this;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vec3d normalize() {
        double len = length();
        if (len != 0) {
            divide(len);
        }
        return this;
    }

    public double dot(Vec3d v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public double distanceTo(Vec3d v) {
        double dx = v.x - x;
        double dy = v.y - y;
        double dz = v.z - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Vec3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3d copy() {
        return new Vec3d(x, y, z);
    }

    @Override
    public String toString() {
        return "Vec3d{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }

}
