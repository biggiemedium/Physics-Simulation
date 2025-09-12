package design.after.image.infastracture.Entity

import design.after.image.Utility.Constructors.Renderable
import design.after.image.infastracture.math.PhysicalObject

/**
 * @author James
 */
class Entity(
    val physics: PhysicalObject,
    val renderer: Renderable
) {

    fun update(deltaTime: Float) {
        physics.update(deltaTime)
    }

    fun render() {
        renderer.render(physics)
    }
}