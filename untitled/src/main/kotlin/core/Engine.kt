package design.after.image.core

import design.after.image.Rendering.SunRenderer
import design.after.image.Utility.ADT.Vec3d
import design.after.image.Utility.Constructors.Renderable
import design.after.image.infastracture.Entity.Entity
import design.after.image.infastracture.Input.InputHandler
import design.after.image.infastracture.grapics.Renderer
import design.after.image.infastracture.grapics.Window
import design.after.image.infastracture.math.PhysicalObject

/**
 * @author James
 */
class Engine(
    private val window: Window,
    private val renderer: Renderer
) {

    private val entities = mutableListOf<Entity>()
    private val deltaTick = 1f // engine tick speed -> set to 1x of itself

    private val inputHandler: InputHandler

    init {
        renderer.initialize(800, 600)

        inputHandler = InputHandler(renderer.getCamera())
        window.setInputHandler(inputHandler)
        // manually add objects for now
        entities.add(Entity(PhysicalObject(Vec3d(0f, 0f, -5f)), SunRenderer()))
    }

    fun run() {
        renderer.clearScreen()

        for (entity in entities) {
            entity.update(deltaTick)
            entity.render()
        }
    }


}