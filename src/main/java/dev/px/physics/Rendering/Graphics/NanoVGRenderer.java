package dev.px.physics.Rendering.Graphics;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

/**
 * @author James
 */
public class NanoVGRenderer {


    private long vg;
    private int fontRegular;
    private int width;
    private int height;

    public NanoVGRenderer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init() {
        vg = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
        if (vg == 0) {
            throw new RuntimeException("Could not initialize NanoVG");
        }

        // Try to load a system font
        fontRegular = nvgCreateFont(vg, "sans", "C:/Windows/Fonts/arial.ttf");
        if (fontRegular == -1) {
            // Fallback to another common font
            fontRegular = nvgCreateFont(vg, "sans", "C:/Windows/Fonts/segoeui.ttf");
            if (fontRegular == -1) {
                throw new RuntimeException("Could not load font");
            }
        }
    }

    public void beginFrame() {
        // Save OpenGL state
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPushClientAttrib(GL11.GL_CLIENT_ALL_ATTRIB_BITS);

        nvgBeginFrame(vg, width, height, 1.0f);
    }

    public void endFrame() {
        nvgEndFrame(vg);

        // Restore OpenGL state
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
    }

    public void renderText(String text, float x, float y, float fontSize, NVGColor color) {
        nvgFontSize(vg, fontSize);
        nvgFontFace(vg, "sans");
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, color);
        nvgText(vg, x, y, text);
    }

    public void renderTextWithShadow(String text, float x, float y, float fontSize) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NVGColor shadowColor = NVGColor.mallocStack(stack);
            NVGColor textColor = NVGColor.mallocStack(stack);

            // Black shadow
            nvgRGBA((byte) 0, (byte) 0, (byte) 0, (byte) 180, shadowColor);
            renderText(text, x + 2, y + 2, fontSize, shadowColor);

            // White text
            nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) 255, textColor);
            renderText(text, x, y, fontSize, textColor);
        }
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void cleanup() {
        if (vg != 0) {
            nvgDelete(vg);
        }
    }

}
