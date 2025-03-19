package testapp;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.design.impl.DefaultDarkDesign;
import com.dokko.win4jui.api.element.Anchors;
import com.dokko.win4jui.api.element.impl.Panel4JUI;
import com.dokko.win4jui.api.element.impl.Text4JUI;
import com.dokko.win4jui.api.window.Window4JUI;

import java.awt.*;

public class Main { //TODO: ImageRenderer4JUI
    public static void main(String[] args) {
        try {
            Win4JUI.setDeveloperScreenSize(1920, 1080);
            Win4JUI.setDesign(new DefaultDarkDesign());
            Win4JUI.initialize();
            Window4JUI window4JUI = new Window4JUI("Win4JUI Screen: " + Win4JUI.getScreenWidth()+"x"+Win4JUI.getScreenHeight(), 1100, 800, 30);
            window4JUI.getElements().add(new Panel4JUI(12, 12, 300, 776, Anchors.SCALED_LEFT));
            window4JUI.getElements().add(new Panel4JUI(0, 24, 950, 100, Anchors.BOTTOM_CENTER).setBackground(Color.darkGray));
            window4JUI.getElements().add(new Text4JUI("Anchored to the right", 4, 4, Anchors.CENTER_RIGHT).setShadow(true).setScaleText(false).setForeground(Color.blue));
            window4JUI.getElements().add(new Text4JUI("Anchored to the top", 0, 2, Anchors.TOP_CENTER).setBackground(Color.CYAN));
            window4JUI.setVisible(true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
