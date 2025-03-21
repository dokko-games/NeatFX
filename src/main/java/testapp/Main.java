package testapp;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.design.impl.DefaultDarkDesign;
import com.dokko.win4jui.api.element.Anchors;
import com.dokko.win4jui.api.element.impl.Panel4JUI;
import com.dokko.win4jui.api.element.impl.Text4JUI;
import com.dokko.win4jui.api.window.Window4JUI;


public class Main { //TODO: ImageRenderer4JUI
    public static void main(String[] args) {
        try {
            Win4JUI.setDeveloperScreenSize(1920, 1080);
            Win4JUI.setDesign(new DefaultDarkDesign());
            Win4JUI.initialize();
            Window4JUI window4JUI = new Window4JUI("Win4JUI Test Application", 1100, 800, 30);
            Panel4JUI leftPanel = new Panel4JUI(5, 5, 300, 790, Anchors.SCALED_LEFT);
            leftPanel.addForeground(new Text4JUI("Win4JUI Test Application", 4, 4, Anchors.TOP_CENTER)
                    .setShadow(true).setFontSize(11).setScaleText(false));
            window4JUI.getElements().add(leftPanel);

            Panel4JUI rightPanel = new Panel4JUI(5, 5, 300, 790, Anchors.SCALED_RIGHT);
            rightPanel.addForeground(new Text4JUI("SDK version "+Win4JUI.SDK_VERSION, 4, 4, Anchors.BOTTOM_RIGHT)
                    .setScaleText(false).setShadow(true).setFontSize(14));
            window4JUI.getElements().add(rightPanel);
            window4JUI.setVisible(true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
