package testapp;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.Logger4JUI;
import com.dokko.win4jui.api.image.Image4JUI;
import com.dokko.win4jui.api.image.ImageFilter4JUI;
import com.dokko.win4jui.api.ui.Input4JUI;
import com.dokko.win4jui.api.ui.design.impl.DefaultDarkDesign;
import com.dokko.win4jui.api.ui.element.Anchors;
import com.dokko.win4jui.api.ui.element.impl.Button4JUI;
import com.dokko.win4jui.api.ui.element.impl.ImageRenderer4JUI;
import com.dokko.win4jui.api.ui.element.impl.Panel4JUI;
import com.dokko.win4jui.api.ui.element.impl.Text4JUI;
import com.dokko.win4jui.api.ui.window.Window4JUI;

import javax.imageio.ImageIO;
import java.io.File;


public class Main { //TODO: ImageRenderer4JUI
    public static void main(String[] args) {
        try {
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
        } catch (Exception e) {
            Logger4JUI.fatal("%{0}", e.getMessage());
            Logger4JUI.fatalNoCount("%{0}", e.getStackTrace()[0]);
            Win4JUI.exit(-1);
        }
    }
}
