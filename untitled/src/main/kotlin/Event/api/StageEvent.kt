package design.after.image.Event.api

import design.after.image.Event.Event

/**
 * @author James
 */
open class StageEvent(val stage: Stage = Stage.PRE) : Event() {

    enum class Stage {
        PRE, POST
    }

    fun isPre(): Boolean = stage == Stage.PRE
    fun isPost(): Boolean = stage == Stage.POST
}