package dev.px.physics.Util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author James
 */
public class ScreenshotUtil {

    private static final File SCREENSHOT_FOLDER = new File("screenshots");

    // Has to be called after everything (HUD included) is drawn but before glfwSwapBuffers
    // otherwise we read an empty back buffer
    public static void takeScreenshot(int width, int height) {
        // 4 bytes per pixel, RGBA
        ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

        // rows are packed tightly, no padding at the end of each row
        GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

        // Writing the png is slow so do it off the main thread, otherwise the sim stutters
        new Thread(() -> save(pixels, width, height), "Screenshot").start();
    }

    private static void save(ByteBuffer pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int i = (y * width + x) * 4;
                int r = pixels.get(i) & 0xFF;
                int g = pixels.get(i + 1) & 0xFF;
                int b = pixels.get(i + 2) & 0xFF;

                // OpenGL starts at the bottom left, images start at the top left so flip y
                image.setRGB(x, height - 1 - y, (r << 16) | (g << 8) | b);
            }
        }

        if (!SCREENSHOT_FOLDER.exists()) {
            SCREENSHOT_FOLDER.mkdirs();
        }

        String time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        File file = new File(SCREENSHOT_FOLDER, "screenshot_" + time + ".png");

        try {
            ImageIO.write(image, "png", file);
            System.out.println("Saved screenshot to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}
