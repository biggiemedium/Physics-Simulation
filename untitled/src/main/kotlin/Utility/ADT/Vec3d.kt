package design.after.image.Utility.ADT

import kotlin.math.sqrt

/**
 * @author James
 */
data class Vec3d(var x: Float, var y: Float, var z: Float) {

    operator fun plus(other: Vec3d): Vec3d =
        Vec3d(x + other.x, y + other.y, z + other.z)

    operator fun plusAssign(other: Vec3d) {
        x += other.x; y += other.y; z += other.z
    }

    operator fun minus(other: Vec3d): Vec3d =
        Vec3d(x - other.x, y - other.y, z - other.z)

    operator fun times(scalar: Float): Vec3d =
        Vec3d(x * scalar, y * scalar, z * scalar)

    operator fun div(scalar: Float): Vec3d =
        Vec3d(x / scalar, y / scalar, z / scalar)

    fun set(x: Float, y: Float, z: Float): Vec3d {
        this.x = x; this.y = y; this.z = z
        return this
    }

    fun normalize(): Vec3d {
        val len = sqrt(x * x + y * y + z * z)
        if (len != 0f) {
            x /= len; y /= len; z /= len
        }
        return this
    }
}
