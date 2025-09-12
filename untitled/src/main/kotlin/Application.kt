package design.after.image

import design.after.image.core.Engine
import design.after.image.infastracture.grapics.Renderer
import design.after.image.infastracture.grapics.Window
import me.zero.alpine.fork.bus.EventBus
import me.zero.alpine.fork.bus.EventManager

/**
 * @author James
 */


fun main() {
    // Create window
    val window = Window(width = 800, height = 600, title = "Physics Simulator")
    val renderer = Renderer()
    val engine = Engine(window, renderer)
    val EVENT_BUS = EventManager()

    window.runLoop {
        engine.run()
    }
    window.cleanup()
}
