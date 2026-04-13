package dev.px.physics.Rendering.Scenes.Api;

/**
 * @author James
 */
public interface Renderable {

    void render(double mouseX, double mouseY, double delta);

    void mouseClicked(double mouseX, double mouseY, int button);

    void mouseReleased(double mouseX, double mouseY, int button);

}
