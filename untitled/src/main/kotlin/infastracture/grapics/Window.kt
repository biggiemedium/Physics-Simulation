package design.after.image.infastracture.grapics

import design.after.image.infastracture.Input.InputHandler
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryUtil.NULL

/**
 * @author James
 */
class Window(private val width: Int, private val height: Int, private val title: String) {

    private var windowHandle: Long = 0
    private var inputHandler: InputHandler? = null

    init {
        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL)
        if (windowHandle == NULL) {
            throw RuntimeException("Failed to create GLFW window")
        }

        glfwMakeContextCurrent(windowHandle)
        GL.createCapabilities()
        glfwSwapInterval(1) // Enable vsync

        glfwShowWindow(windowHandle)
    }

    fun runLoop(loop: (Float) -> Unit) {
        var lastFrameTime = glfwGetTime()

        while (!glfwWindowShouldClose(windowHandle)) {
            val currentFrameTime = glfwGetTime()
            val deltaTime = (currentFrameTime - lastFrameTime).toFloat()
            lastFrameTime = currentFrameTime

            // Handle keyboard input
            inputHandler?.handleKeyboard(windowHandle, deltaTime)

            loop(deltaTime)
            glfwSwapBuffers(windowHandle)
            glfwPollEvents()
        }
    }

    fun cleanup() {
        Callbacks.glfwFreeCallbacks(windowHandle)
        glfwDestroyWindow(windowHandle)
        glfwTerminate()
    }


    private fun setupInputCallbacks() { // TODO: MOVE TO AN ACTUAL EVENT SYSTEM
        // Mouse button callback
        glfwSetMouseButtonCallback(windowHandle) { _, button, action, mods ->
            inputHandler?.handleMouseButton(button, action, mods)
        }

        // Mouse position callback
        glfwSetCursorPosCallback(windowHandle) { _, xpos, ypos ->
            inputHandler?.handleMouseMove(xpos, ypos)
        }
    }

    fun setInputHandler(handler: InputHandler) {
        this.inputHandler = handler
    }
}