package design.after.image.Rendering

import design.after.image.Utility.Constructors.Renderable
import design.after.image.infastracture.math.PhysicalObject
import org.lwjgl.opengl.GL11.*

/**
 * @author James
 */
class SunRenderer : Renderable() {

    override fun render(obj: PhysicalObject) {
        glPushMatrix()
        glTranslatef(obj.position.x, obj.position.y, obj.position.z)
        glColor3f(1f, 1f, 0f) // yellow

        // Make it bigger and more visible
        glBegin(GL_QUADS)
        glVertex3f(-1f, -1f, 0f)  // Increased size from 0.5f -> 1f
        glVertex3f(1f, -1f, 0f)
        glVertex3f(1f, 1f, 0f)
        glVertex3f(-1f, 1f, 0f)
        glEnd()

        glPopMatrix()
    }

}