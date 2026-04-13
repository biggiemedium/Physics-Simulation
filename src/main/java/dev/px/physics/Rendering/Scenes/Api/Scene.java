package dev.px.physics.Rendering.Scenes.Api;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Camera;
import dev.px.physics.System.InputSystem;
import dev.px.physics.System.PhysicsSystem;
import dev.px.physics.Util.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    protected List<PhysicalObject> objects = new ArrayList<>();
    protected List<Renderable> renderables = new ArrayList<>();

    protected Camera camera;
    protected PhysicsSystem physicsSystem = new PhysicsSystem();
    protected InputSystem inputSystem;

    public void init(long windowHandle) {
        if (camera == null) {
            camera = new Camera(new Vec3d(0, 0, 0));
        }
        this.inputSystem = new InputSystem(windowHandle, camera);
    }

    public void update(double delta) {
        physicsSystem.update(objects, delta);

        if (inputSystem != null) {
            inputSystem.update(delta);
        }
    }

    public void render(double mouseX, double mouseY, double delta) {
        for (Renderable r : renderables) {
            r.render(mouseX, mouseY, delta);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        for (Renderable r : renderables) {
            r.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (Renderable r : renderables) {
            r.mouseReleased(mouseX, mouseY, button);
        }
    }

    public void addObject(PhysicalObject obj) {
        objects.add(obj);
    }

    public void addRenderable(Renderable r) {
        renderables.add(r);
    }

    public Camera getCamera() {
        return camera;
    }

    public InputSystem getInputSystem() {
        return inputSystem;
    }

    public void onLoad() {}
    public void onUnload() {}
}