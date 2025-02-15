package testapp;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.element.Element4JUI;
import com.dokko.win4jui.api.window.Window4JUI;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            Win4JUI.setDeveloperScreenSize(1920, 1080);
            Win4JUI.initialize();
            System.out.println("Rendering Window on "+Win4JUI.getScreenDimensions());
            Window4JUI window4JUI = new Window4JUI("Test Application", 1100, 800);
            window4JUI.setVisible(true);
            window4JUI.getElements().add(new Element4JUI(35, 15, 110, 400) {
                @Override
                protected void doRender(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY) {
                    graphics.setColor(Color.red);
                    graphics.fillRect((int) x, (int) y, (int) width, (int) height);
                }
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
