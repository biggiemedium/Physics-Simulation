package design.after.image.infastracture.math

import design.after.image.Utility.ADT.Vec3d

/**
 * Simulates and stores the trajectory of a PhysicalObject over time.
 *
 * Stores the positions of the object at each simulation step.
 *
 * @property physicalObject The object being simulated.
 */
class Trajectory(val physicalObject: PhysicalObject) {

    private val positions = mutableListOf<Vec3d>()

    /**
     * Simulate the motion of the physical object over [totalTime] seconds,
     * advancing in increments of [deltaTime] seconds per step.
     *
     * Forces should be applied to the physical object externally before calling this method.
     *
     * @param totalTime The total time to simulate (seconds).
     * @param deltaTime The timestep duration for each update (seconds).
     */
    fun simulate(totalTime: Float, deltaTime: Float) {
        positions.clear()
        var timeElapsed = 0f

        // Record initial position
        positions.add(physicalObject.position.copy())

        while (timeElapsed < totalTime) {
            // Update the physical object state
            physicalObject.update(deltaTime)

            // Record position after update
            positions.add(physicalObject.position.copy())

            timeElapsed += deltaTime
        }
    }

    /**
     * Returns the list of recorded positions representing the trajectory path.
     */
    fun getPositions(): List<Vec3d> = positions.toList()
}