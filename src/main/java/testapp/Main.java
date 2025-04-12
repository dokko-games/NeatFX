package testapp;

import com.dokko.neatfx.NeatFX;
import com.dokko.neatfx.NeatLogger;
import com.dokko.neatfx.core.Input;
import com.dokko.neatfx.core.window.Anchors;
import com.dokko.neatfx.core.window.Window;
import com.dokko.neatfx.core.window.element.impl.advanced.ImageRenderer;
import com.dokko.neatfx.core.window.element.impl.basic.Button;
import com.dokko.neatfx.core.window.element.impl.basic.Panel;
import com.dokko.neatfx.core.window.element.impl.basic.Text;
import com.dokko.neatfx.engine.render.texture.Texture;
import org.lwjgl.glfw.GLFW;


public class Main {
    public static void main(String[] args) {
        try {
            newEngine(args);
        } catch (Exception e) {
            NeatLogger.fatal("%{0}", e.getClass().getSimpleName());
            NeatLogger.fatalNoCount("%{0}", e.getMessage());
            NeatLogger.fatalNoCount("%{0}", e.getStackTrace()[0]);
            NeatFX.exit(-1);
        }
    }

    private static void newEngine(String[] args) {
        NeatFX.setDeveloperScreenSize(1920, 1080);
        NeatFX.initialize(args);
        Window wr = new Window("Test App", 1100, 800);
        NeatFX.runOnShutdown.add(() -> {
            if(wr.isClosing()) NeatLogger.warn("Window Closed Manually.");
            else NeatLogger.warn("Window Closed Automatically.");
        });
        Panel left = new Panel(5, 5, 300, 790, Anchors.TOP_LEFT_SCALE.xy(false, true));
        left.addForeground(new Text("#{5CEAFF}NeatFX #{FFFFFF}Test Application", 4, 4, 0, 0, Anchors.SCALE_TOP_CENTER)
                .setShadow(true));
        Button b = new Button("Exit without closing", 0, 20, 0, 0, Anchors.BOTTOM_CENTER){
            @Override
            public void onInput(float x, float y, float w, float h, float sw, float sh) {
                if(Input.wasButtonJustReleased(GLFW.GLFW_MOUSE_BUTTON_1)){
                    NeatFX.exit(0);
                }
            }
        };
        left.addForeground(b);
        wr.add(left);
        Panel right = new Panel(5, 5, 300, 790, Anchors.TOP_RIGHT_SCALE.xy(false, true));
        right.addForeground(new Text(NeatFX.LIB_VERSION, 7, 4, 0, 0, Anchors.SCALE_BOTTOM_RIGHT)
                .setShadow(true));
        wr.add(right);
        int[] smileyFace = generateSmileyFace();
        ImageRenderer imageRenderer = new ImageRenderer(Texture.createTexture(16, 16, smileyFace), 0, 0, 150, 150, Anchors.CENTERED);
        wr.add(imageRenderer);
        wr.renderLoop();
        wr.cleanUp();
        NeatFX.exit(0);
    }

    private static int[] generateSmileyFace() {
        int width = 16, height = 16;
        // Define the colors
        int yellow = 0xFFFFFF00; // Yellow face
        int black = 0xFF000000;  // Black color (for eyes and mouth)
        int white = 0xFFFFFFFF;  // White color (for eyes)

        int[] smileyFace = new int[width * height];

        // Fill the face with yellow
        for (int i = 0; i < width * height; i++) {
            if(i == 0 || i == 15 || i == width*height-1 || i == width*(height-1))continue;
            smileyFace[i] = yellow;
        }

        // Eyes: Set two black eyes with white centers
        smileyFace[4 * width + 4] = white;  // Left eye (white)
        smileyFace[4 * width + 5] = black;  // Left eye (black)
        smileyFace[5 * width + 4] = white;  // Left eye (white)
        smileyFace[5 * width + 5] = black;  // Left eye (black)
        smileyFace[6 * width + 4] = white;  // Left eye (white)
        smileyFace[6 * width + 5] = black;  // Left eye (black)
        smileyFace[5 * width + 10] = black; // Right eye (black)
        smileyFace[5 * width + 11] = white; // Right eye (white)
        smileyFace[4 * width + 10] = black; // Right eye (black)
        smileyFace[4 * width + 11] = white; // Right eye (white)
        smileyFace[6 * width + 10] = black; // Right eye (black)
        smileyFace[6 * width + 11] = white; // Right eye (white)

        // Mouth: Set a smiling mouth
        smileyFace[10 * width + 4] = black;
        smileyFace[11 * width + 5] = black;
        smileyFace[11 * width + 6] = black;
        smileyFace[11 * width + 7] = black;
        smileyFace[11 * width + 8] = black;
        smileyFace[11 * width + 9] = black;
        smileyFace[11 * width + 10] = black;
        smileyFace[10 * width + 11] = black;
        return smileyFace;
    }
}
