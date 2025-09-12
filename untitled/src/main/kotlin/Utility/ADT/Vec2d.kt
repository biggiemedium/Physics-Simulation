package design.after.image.Utility.ADT

import kotlin.math.sqrt

/**
 * @author James
 */
data class Vec2d(var x: Float, var y: Float) {
    fun set(x: Float, y: Float): Vec2d {
        this.x = x; this.y = y
        return this
    }

    fun add(other: Vec2d): Vec2d {
        x += other.x; y += other.y
        return this
    }

    fun subtract(other: Vec2d): Vec2d {
        x -= other.x; y -= other.y
        return this
    }

    fun multiply(scalar: Float): Vec2d {
        x *= scalar; y *= scalar
        return this
    }

    fun normalize(): Vec2d {
        val len = sqrt(x * x + y * y)
        if (len != 0f) {
            x /= len; y /= len
        }
        return this
    }
}
