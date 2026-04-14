package dev.px.physics;

import dev.px.physics.Core.Engine;
import dev.px.physics.Rendering.Scenes.GalaxyScene;
import dev.px.physics.Rendering.Window.Window;

/**
 * @author James
 */
public class Main {

    public static void main(String[] args) {

        Window window = new Window("Physics simulation", 1400, 1000);
        window.init();

        Engine engine = new Engine();
        engine.start(window, new GalaxyScene());
    }

}