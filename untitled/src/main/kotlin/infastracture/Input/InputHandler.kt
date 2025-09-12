package design.after.image.infastracture.Input

import design.after.image.infastracture.grapics.Camera.Camera
import org.lwjgl.glfw.GLFW.*

/**
 * Handles input for camera controls
 * @author James
 */
class InputHandler(private val camera: Camera) {

    // Input state
    private var mousePressed = false
    private var lastMouseX = 0.0
    private var lastMouseY = 0.0
    private var firstMouse = true

    // Settings
    private val mouseSensitivity = 0.1f
    private val moveSpeed = 5.0f

    fun handleKeyboard(window: Long, deltaTime: Float) {
        val speed = moveSpeed * deltaTime

        // Arrow key movement
        if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
            camera.move(0f, 0f, -speed) // Move forward (negative Z)
        }
        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
            camera.move(0f, 0f, speed) // Move backward (positive Z)
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            camera.move(-speed, 0f, 0f) // Move left (negative X)
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            camera.move(speed, 0f, 0f) // Move right (positive X)
        }

        // Optional: WASD controls as well
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            camera.move(0f, 0f, -speed)
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            camera.move(0f, 0f, speed)
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            camera.move(-speed, 0f, 0f)
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            camera.move(speed, 0f, 0f)
        }
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            camera.move(0f, speed, 0f) // Move up
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            camera.move(0f, -speed, 0f) // Move down
        }
    }

    fun handleMouseButton(button: Int, action: Int, mods: Int) {
        if (button == GLFW_MOUSE_BUTTON_LEFT) {
            mousePressed = (action == GLFW_PRESS)
            if (mousePressed) {
                firstMouse = true
            }
        }
    }

    fun handleMouseMove(xpos: Double, ypos: Double) {
        if (!mousePressed) return

        if (firstMouse) {
            lastMouseX = xpos
            lastMouseY = ypos
            firstMouse = false
            return
        }

        val xOffset = (xpos - lastMouseX).toFloat() * mouseSensitivity
        val yOffset = (lastMouseY - ypos).toFloat() * mouseSensitivity // Reversed since y-coordinates go from bottom to top

        lastMouseX = xpos
        lastMouseY = ypos

        camera.rotate(xOffset, yOffset)

        // Constrain pitch to prevent flipping
        if (camera.pitch > 89.0f) camera.pitch = 89.0f
        if (camera.pitch < -89.0f) camera.pitch = -89.0f
    }
}