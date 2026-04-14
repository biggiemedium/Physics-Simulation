package dev.px.physics.Core;

import dev.px.physics.Rendering.Scenes.Api.Scene;
import dev.px.physics.Rendering.Window.Window;
import dev.px.physics.System.ScalingSystem;

/**
 * @author James
 */
public class Engine {

    private Window window;
    private Scene currentScene;

    private ScalingSystem scalingSystem;

    public Engine() {
        this.scalingSystem = new ScalingSystem(500.0);
    }

    public Engine(double worldSize) {
        this.scalingSystem = new ScalingSystem(worldSize);
    }

    public void start(Window window, Scene scene) {
        this.window = window;
        this.currentScene = scene;

        scene.setScalingSystem(scalingSystem);

        window.setScene(scene);
        window.run();
    }

    public ScalingSystem getScalingSystem() {
        return scalingSystem;
    }

    public void setScalingSystem(ScalingSystem scalingSystem) {
        this.scalingSystem = scalingSystem;
    }
}
