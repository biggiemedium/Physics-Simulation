package dev.px.physics.Util.Math.Vector;

/**
 * @author James
 */
public class Vec2d {

    public double x, y;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d add(Vec2d v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vec2d add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2d subtract(Vec2d v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vec2d subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vec2d multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vec2d multiply(Vec2d v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vec2d divide(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public Vec2d normalize() {
        double len = length();
        if (len != 0) {
            divide(len);
        }
        return this;
    }

    public double dot(Vec2d v) {
        return x * v.x + y * v.y;
    }

    public double distanceTo(Vec2d v) {
        double dx = v.x - x;
        double dy = v.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Vec2d copy() {
        return new Vec2d(x, y);
    }

    public Vec2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "Vec2d{" + "x=" + x + ", y=" + y + '}';
    }

}
