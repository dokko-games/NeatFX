package testapp;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.Logger4JUI;
import com.dokko.win4jui.core.Input;
import com.dokko.win4jui.core.window.Anchors;
import com.dokko.win4jui.core.window.Window;
import com.dokko.win4jui.core.window.element.impl.advanced.ImageRenderer;
import com.dokko.win4jui.core.window.element.impl.basic.Button;
import com.dokko.win4jui.core.window.element.impl.basic.Panel;
import com.dokko.win4jui.core.window.element.impl.basic.Text;
import com.dokko.win4jui.engine.render.texture.Texture;
import org.lwjgl.glfw.GLFW;


public class Main {
    public static void main(String[] args) {
        try {
            //oldEngine();
            newEngine();
        } catch (Exception e) {
            Logger4JUI.fatal("%{0}", e.getClass().getSimpleName());
            Logger4JUI.fatalNoCount("%{0}", e.getMessage());
            Logger4JUI.fatalNoCount("%{0}", e.getStackTrace()[0]);
            Win4JUI.exit(-1);
        }
    }

    private static void newEngine() {
        Win4JUI.setDeveloperScreenSize(1920, 1080);
        Win4JUI.initialize();
        Win4JUI.globalParameters.put("STORE_FONTS", true);
        Window wr = new Window("Test App", 1100, 800);
        Win4JUI.runOnShutdown.add(() -> {
            if(wr.isClosing()) Logger4JUI.warn("Window Closed Manually.");
            else Logger4JUI.warn("Window Closed Automatically.");
        });
        Panel left = new Panel(5, 5, 300, 790, Anchors.TOP_LEFT_SCALE.xy(false, true));
        left.addForeground(new Text("#{5CEAFF}Win4JUI #{FFFFFF}Test Application", 4, 4, 0, 0, Anchors.SCALE_TOP_CENTER)
                .setShadow(true));
        Button b = new Button("Exit without closing", 0, 20, 0, 0, Anchors.BOTTOM_CENTER){
            @Override
            public void onInput(float x, float y, float w, float h, float sw, float sh) {
                if(Input.wasButtonJustReleased(GLFW.GLFW_MOUSE_BUTTON_1)){
                    Win4JUI.exit(0);
                }
            }
        };
        left.addForeground(b);
        wr.add(left);
        Panel right = new Panel(5, 5, 300, 790, Anchors.TOP_RIGHT_SCALE.xy(false, true));
        right.addForeground(new Text(Win4JUI.SDK_VERSION, 7, 4, 0, 0, Anchors.SCALE_BOTTOM_RIGHT)
                .setShadow(true));
        wr.add(right);
        int[] smileyFace = generateSmileyFace();
        ImageRenderer imageRenderer = new ImageRenderer(Texture.createTexture(16, 16, smileyFace), 0, 0, 150, 150, Anchors.CENTERED);
        wr.add(imageRenderer);
        wr.renderLoop();
        wr.cleanUp();
        Win4JUI.exit(0);
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

    /*
    private static void oldEngine() throws Exception {
        Win4JUI.setDeveloperScreenSize(1920, 1080);
        Win4JUI.setDesign(new DefaultDarkDesign());
        Win4JUI.initialize();
        Window4JUI window4JUI = new Window4JUI("Win4JUI Test Application", 1100, 800, 30);
        Win4JUI.runOnShutdown.add(() -> {
            if(window4JUI.isClosing()) Logger4JUI.warn("Window Closed.");
            else Logger4JUI.warn("Window Not Closed.");
        });
        Panel4JUI leftPanel = new Panel4JUI(5, 5, 300, 790, Anchors.TOP_SCALE_LEFT);
        leftPanel.addForeground(new Text4JUI("Win4JUI Test Application", 4, 4, Anchors.SCALE_TOP_CENTER)
                .setShadow(true).setFontSize(11).setScaleText(false));
        window4JUI.getElements().add(leftPanel);

        Panel4JUI rightPanel = new Panel4JUI(5, 5, 300, 790, Anchors.TOP_SCALE_RIGHT);
        rightPanel.addForeground(new Text4JUI(Win4JUI.GET_SDK_VERSION_NAME(), 4, 4, Anchors.SCALE_BOTTOM_RIGHT)
                .setScaleText(false).setShadow(true).setFontSize(11));
        window4JUI.getElements().add(rightPanel);
        leftPanel.addForeground(new Button4JUI("Click Me!", 10, 10, 150, 40, Anchors.BOTTOM_LEFT){
            @Override
            public void onInput(float x, float y, float width, float height, float scaleWidth, float scaleHeight) {
                if(!isHovered())return;
                if(!Input4JUI.isButtonJustReleased(Input4JUI.MOUSE_BUTTON_LEFT))return;
                exit(0);
            }
        });

        Image4JUI img = Image4JUI.fromData(8, 8,
                0, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0,
                0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
                0xFFFFFFFF, 0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF, 0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF, 0xFFFFFFFF,
                0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
                0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
                0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF,
                0xFFFFFFFF, 0xFFFFFFFF, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFFFFFFFF, 0xFFFFFFFF,
                0, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0);
        ImageIO.write(img.toBuffer(), "png", new File("C:\\Users\\fazed\\Desktop\\LMDWAOF.png"));
        leftPanel.addForeground(new ImageRenderer4JUI(img, 0, 0, 64, 64, 0, 0, 1, 1, Anchors.CENTERED).setFilter(new ImageFilter4JUI() {
            @Override
            public void process(Image4JUI image) {
                byte[] b = image.getImageData();
                for(int i = 0; i < b.length; i++){
                    byte b1 = (byte) (0xFF ^ b[i]);
                    if(i % 4 != 0) b[i] = b1; // ignore alpha channel
                }
                image.setImageData(b);
            }
        }));

        window4JUI.setVisible(true);
    }
     */
}
