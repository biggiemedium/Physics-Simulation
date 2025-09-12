package design.after.image.infastracture.grapics

import design.after.image.Utility.Constructors.Renderable
import design.after.image.infastracture.grapics.Camera.Camera
import org.lwjgl.opengl.GL11.*
import kotlin.math.tan


/**
 * @author James
 */
class Renderer {

    private val camera = Camera()

    fun initialize(windowWidth: Int, windowHeight: Int) {
        // Enable depth testing
        glEnable(GL_DEPTH_TEST)

        // Set up viewport
        glViewport(0, 0, windowWidth, windowHeight)

        // Set up projection matrix
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()

        // Set up perspective projection
        val aspect = windowWidth.toFloat() / windowHeight.toFloat()
        setPerspective(camera.fov, aspect, 0.1f, 100.0f)

        // Switch back to modelview matrix
        glMatrixMode(GL_MODELVIEW)

        // Position camera to see the scene
        camera.z = 5f // Move camera back to see objects at negative Z
    }


    fun clearScreen() {
        glClearColor(0f, 0f, 0f, 1f) // Black background
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        // Reset modelview matrix and set up camera
        glLoadIdentity()

        // Rotate based on camera pitch and yaw
        glRotatef(-camera.pitch, 1f, 0f, 0f)
        glRotatef(-camera.yaw, 0f, 1f, 0f)

        // Translate based on camera position (negative because we move the world, not the camera)
        glTranslatef(-camera.x, -camera.y, -camera.z)
    }

    // SOH-CAH-TOA
    private fun setPerspective(fovy: Float, aspect: Float, near: Float, far: Float) {
        val fH = tan(Math.toRadians(fovy.toDouble()) / 2.0).toFloat() * near
        val fW = fH * aspect
        glFrustum(-fW.toDouble(), fW.toDouble(), -fH.toDouble(), fH.toDouble(), near.toDouble(), far.toDouble())
    }

    fun getCamera(): Camera {
        return this.camera
    }
}