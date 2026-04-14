package dev.px.physics.Objects.Impl;

import dev.px.physics.Objects.PhysicalObject;
import dev.px.physics.Rendering.Scenes.Api.Renderable;
import dev.px.physics.System.ScalingSystem;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * @author James
 */
public class SpaceTimeCurvature implements Renderable {

    private ScalingSystem scalingSystem;
    private int gridDivisions;

    private List<PhysicalObject> objects;

    public SpaceTimeCurvature(ScalingSystem scalingSystem, int gridDivisions, List<PhysicalObject> objects) {
        this.scalingSystem = scalingSystem;
        this.gridDivisions = gridDivisions;
        this.objects = objects;
    }

    public SpaceTimeCurvature(ScalingSystem scalingSystem, List<PhysicalObject> objects) {
        this(scalingSystem, 50, objects);
    }

    @Override
    public void render(double mouseX, double mouseY, double delta) {
        if(this.scalingSystem == null) {
            return;
        }

        GL11.glPushMatrix();

        for(int i = 0; i < this.objects.size(); i++) {
            PhysicalObject o = this.objects.get(i);
            if(o == null) {
                continue;
            }


        }

        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {}

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {}
}
