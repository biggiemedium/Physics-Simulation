package design.after.image.infastracture.math

import design.after.image.Utility.ADT.Vec3d


/**
 * @author James
 */
open class PhysicalObject(
    var position: Vec3d,
    var velocity: Vec3d = Vec3d(0f, 0f, 0f),
    var acceleration: Vec3d = Vec3d(0f, 0f, 0f),
    var mass: Float = 1f // default 1F
) { // mass can be used to compute gravitational pull

    /**
     * Applies a force to the object, updating its acceleration.
     *
     * According to Newton's second law of motion:
     *     F = m * a
     * where
     *     F = force vector (Newton)
     *     m = mass (kg)
     *     a = acceleration vector (m/s²)
     *
     * Rearranged to solve for acceleration:
     *     a = F / m
     *
     * This function calculates the acceleration contribution from the force and
     * adds it to the current acceleration.
     *
     * @param force The force vector applied to the object.
     */
    open fun applyForce(force: Vec3d) {
        // F = m * a  ->  a = F / m
        val delta = force / mass
        acceleration.plusAssign(delta)
    }

    /**
     * Updates the object's velocity and position based on its current acceleration over the time step.
     *
     * Uses basic kinematic equations assuming constant acceleration over deltaTime:
     *
     *     v(t + Δt) = v(t) + a(t) * Δt
     *     x(t + Δt) = x(t) + v(t) * Δt
     *
     * where
     *     v = velocity vector (m/s)
     *     a = acceleration vector (m/s²)
     *     x = position vector (m)
     *     Δt = time step duration (seconds)
     *
     * After updating, acceleration is reset to zero, expecting new forces to be applied for the next frame.
     *
     * @param deltaTime The elapsed time interval (in seconds) over which to update.
     */
    open fun update(deltaTime: Float) {
        velocity.plusAssign(acceleration * deltaTime)
        position.plusAssign(velocity * deltaTime)
        acceleration.set(0f, 0f, 0f) // reset acceleration for next frame
    }

}
