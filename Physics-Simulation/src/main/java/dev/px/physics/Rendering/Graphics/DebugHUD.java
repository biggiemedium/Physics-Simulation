package dev.px.physics.Rendering.Graphics;

import dev.px.physics.Rendering.Camera;
import dev.px.physics.Util.Math.Vector.Vec2d;
import dev.px.physics.Util.Math.Vector.Vec3d;

/**
 * @author James
 */
public class DebugHUD {

    private NanoVGRenderer nvgRenderer;
    private double lastTime;
    private int frameCount;
    private double fps;

    public DebugHUD(int width, int height) {
        this.nvgRenderer = new NanoVGRenderer(width, height);
        this.lastTime = System.nanoTime() / 1_000_000_000.0;
        this.frameCount = 0;
        this.fps = 0.0;
    }

    public void init() {
        nvgRenderer.init();
    }

    public void render(Camera camera) {
        if (camera == null) {
            return;
        }

        // Calculate FPS
        updateFPS();

        nvgRenderer.beginFrame();

        Vec3d pos = camera.getPosition();
        Vec2d rot = camera.getRotation();

        String posText = String.format("Pos: %.1f, %.1f, %.1f", pos.x, pos.y, pos.z);
        String rotText = String.format("Rot: %.1f, %.1f", rot.x, rot.y);
        String fpsText = String.format("FPS: %.0f", fps);

        nvgRenderer.renderTextWithShadow(posText, 10, 20, 18);
        nvgRenderer.renderTextWithShadow(rotText, 10, 45, 18);
        nvgRenderer.renderTextWithShadow(fpsText, 10, 70, 18);

        nvgRenderer.endFrame();
    }

    private void updateFPS() {
        double currentTime = System.nanoTime() / 1_000_000_000.0;
        frameCount++;

        // Update FPS every second
        if (currentTime - lastTime >= 1.0) {
            fps = frameCount / (currentTime - lastTime);
            frameCount = 0;
            lastTime = currentTime;
        }
    }

    public void setSize(int width, int height) {
        nvgRenderer.setSize(width, height);
    }

    public void cleanup() {
        nvgRenderer.cleanup();
    }
}
