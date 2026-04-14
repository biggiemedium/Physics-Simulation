package dev.px.physics.System;

import dev.px.physics.Rendering.Camera;
import dev.px.physics.Util.Math.Vector.Vec3d;
import org.lwjgl.glfw.GLFW;

/**
 * @author James
 */
public class InputSystem {

    private long window;
    private Camera camera;

    private double speed = 50.0;

    // Mouse
    private double mouseSensitivity = 0.1;
    private boolean isMousePressed = false;
    private double lastMouseX = 0;
    private double lastMouseY = 0;

    public InputSystem(long window, Camera camera) {
        this.window = window;
        this.camera = camera;
    }

    public void update(double delta) {
        if (camera == null) {
            return;
        }
        if(this.speed < 0) {
            this.speed = 0;
        }

        Vec3d move = new Vec3d(0, 0, 0);

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
            move.add(camera.getForward());
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
            move.subtract(camera.getForward());
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
            move.add(camera.getRight());
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
            move.subtract(camera.getRight());
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
            move.add(camera.getUp());
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS) {
            move.subtract(camera.getUp());
        }

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS) {
            this.speed += 10;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS) {
            this.speed -= 10;
        }

        if (move.lengthSquared() > 0) {
            move.normalize().multiply(speed * delta);
            camera.move(move);
        }
    }

    public void handleMouseButton(int button, int action, double mouseX, double mouseY) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if (action == GLFW.GLFW_PRESS) {
                isMousePressed = true;
                lastMouseX = mouseX;
                lastMouseY = mouseY;
            } else if (action == GLFW.GLFW_RELEASE) {
                isMousePressed = false;
            }
        }
    }

    public void handleMouseMove(double mouseX, double mouseY) {
        if (camera == null || !isMousePressed) {
            return;
        }

        double deltaX = mouseX - lastMouseX;
        double deltaY = mouseY - lastMouseY;

        // Rotate camera: deltaX affects yaw, deltaY affects pitch
        camera.rotate(-deltaY * mouseSensitivity, deltaX * mouseSensitivity);

        lastMouseX = mouseX;
        lastMouseY = mouseY;

    }
}
