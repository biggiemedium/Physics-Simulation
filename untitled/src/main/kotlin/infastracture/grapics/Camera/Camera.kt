package design.after.image.infastracture.grapics.Camera

/**
 * Represents a 3D camera with position, orientation, and field of view
 *
 * @author James
 */
class Camera(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f,
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var fov: Float = 90f
) {
    /** Moves the camera by the given offsets. */
    fun move(dx: Float, dy: Float, dz: Float) {
        x += dx
        y += dy
        z += dz
    }

    /** Rotates the camera by the given yaw and pitch offsets. */
    fun rotate(dYaw: Float, dPitch: Float) {
        yaw += dYaw
        pitch += dPitch
    }

}