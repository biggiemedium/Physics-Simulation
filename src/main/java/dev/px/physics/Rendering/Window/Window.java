package dev.px.physics.Rendering.Window;

import dev.px.physics.Rendering.Camera;
import dev.px.physics.Rendering.Graphics.DebugHUD;
import dev.px.physics.Rendering.Scenes.Api.Scene;
import dev.px.physics.System.InputSystem;
import dev.px.physics.Util.Vec3d;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author James
 */
public class Window {

    private String title;
    private int width, height;

    private long windowHandle = 0L;

    private Scene currentScene;
    private DebugHUD debugHUD;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init() {
        GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

        if(!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);

        if (windowHandle == GLFW_PLATFORM_NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        // enable GL11 (OpenGL 1.1)
        // allows for 3d rendering capabilities
        GL11.glClearColor(0f, 0f, 0f, 1f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        glfwSwapInterval(1);

        // input callback
        glfwSetMouseButtonCallback(windowHandle, (win, button, action, mods) -> {
            double[] x = new double[1];
            double[] y = new double[1];
            glfwGetCursorPos(windowHandle, x, y);

            if (currentScene != null) {
                if (action == GLFW_PRESS) {
                    currentScene.mouseClicked(x[0], y[0], button);
                } else if (action == GLFW_RELEASE) {
                    currentScene.mouseReleased(x[0], y[0], button);
                }
            }
            if (currentScene.getInputSystem() != null) {
                currentScene.getInputSystem().handleMouseButton(button, action, x[0], y[0]);
            }
        });

        // Input callback
        glfwSetCursorPosCallback(windowHandle, (win, xpos, ypos) -> {
            if (currentScene != null && currentScene.getInputSystem() != null) {
                currentScene.getInputSystem().handleMouseMove(xpos, ypos);
            }
        });


        glfwShowWindow(windowHandle);
        debugHUD = new DebugHUD(width, height);
        debugHUD.init();
    }


    public void setScene(Scene scene) {
        if (this.currentScene != null) {
            this.currentScene.onUnload();
        }

        this.currentScene = scene;

        // onLoad must come before because it handlse our camera setup
        // otherwise the camera will not move position or rotation
        this.currentScene.onLoad();
        this.currentScene.init(windowHandle);
    }

    public void run() {
        double lastFrame = GLFW.glfwGetTime();

        while(!GLFW.glfwWindowShouldClose(windowHandle)) {
            double current = GLFW.glfwGetTime();
            double delta = current - lastFrame;
            lastFrame = current;

            double[] mouseX = new double[1];
            double[] mouseY = new double[1];
            glfwGetCursorPos(windowHandle, mouseX, mouseY);

            if (currentScene != null) {
                currentScene.update(delta);

                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                setupPerspective();
                if (currentScene.getCamera() != null) {
                    applyCameraTransform(currentScene.getCamera());
                }

                currentScene.render(mouseX[0], mouseY[0], delta);
                if (currentScene.getCamera() != null) {
                    debugHUD.render(currentScene.getCamera());
                }
            }

            glfwSwapBuffers(windowHandle);
            glfwPollEvents();
        }
    }

    private void setupPerspective() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity(); // reset any previous projection

        // Aspect ratio = screen width / height
        // Keeps objects from looking stretched
        double fov = 70.0;
        double aspect = (double) width / (double) height;

        // Near and far clipping planes
        // Anything closer than zNear or farther than zFar is not drawn
        double zNear = 0.1;
        double zFar = 1000.0;

        // tan(angle) = opposite / adjacent
        // so we use tan(FOV/2) to get how tall the view is at distance zNear
        double fH = Math.tan(fov / 360.0 * Math.PI) * zNear;

        // Width of the view box is based on aspect ratio
        // wider screen -> wider view
        double fW = fH * aspect;

        // Create a perspective projection box (frustum)
        // This makes far objects look smaller and creates 3D depth
        GL11.glFrustum(-fW, fW, -fH, fH, zNear, zFar);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    private void applyCameraTransform(Camera camera) {
        Vec3d pos = camera.getPosition();
        Vec3d forward = camera.getForward();
        Vec3d up = camera.getUp();

        // target = position + forward direction
        // This gives a point straight in front of the camera
        Vec3d target = new Vec3d(
                pos.x + forward.x,
                pos.y + forward.y,
                pos.z + forward.z
        );

        lookAt(pos.x, pos.y, pos.z, target.x, target.y, target.z, up.x, up.y, up.z);
    }

    private void lookAt(double eyeX, double eyeY, double eyeZ,
                        double centerX, double centerY, double centerZ,
                        double upX, double upY, double upZ
    ) {

        // forward
        // eye gives direction from camera position to target
        double fx = centerX - eyeX;
        double fy = centerY - eyeY;
        double fz = centerZ - eyeZ;

        // Normalize forward vector (make it length 1 so we only care about direction)
        double fLen = Math.sqrt(fx * fx + fy * fy + fz * fz);
        fx /= fLen;
        fy /= fLen;
        fz /= fLen;

        // Right vector (camera's horizontal axis)
        // cross(forward, up) gives a perpendicular direction to both
        double rx = fy * upZ - fz * upY;
        double ry = fz * upX - fx * upZ;
        double rz = fx * upY - fy * upX;

        // // Normalize right vector (keep it unit length for consistent scaling)
        double rLen = Math.sqrt(rx * rx + ry * ry + rz * rz);
        rx /= rLen;
        ry /= rLen;
        rz /= rLen;

        // cross(right, forward) ensures all axes are perfectly perpendicular
        double ux = ry * fz - rz * fy;
        double uy = rz * fx - rx * fz;
        double uz = rx * fy - ry * fx;

        GL11.glMultMatrixd(new double[] {
                rx, ux, -fx, 0,
                ry, uy, -fy, 0,
                rz, uz, -fz, 0,
                0, 0, 0, 1
        });

        GL11.glTranslated(-eyeX, -eyeY, -eyeZ);
    }

    public void cleanup() {
        if (currentScene != null) {
            currentScene.onUnload();
        }
        if (debugHUD != null) {
            debugHUD.cleanup();
        }
        Callbacks.glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
    }
}
