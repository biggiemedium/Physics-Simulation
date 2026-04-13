package dev.px.physics.Core;

import dev.px.physics.Rendering.Scenes.Api.Scene;
import dev.px.physics.Rendering.Window.Window;

/**
 * @author James
 */
public class Engine {

    private Window window;
    private Scene currentScene;

    public void start(Window window, Scene scene) {
        this.window = window;
        this.currentScene = scene;

        window.setScene(scene);
        window.run();
    }

}
