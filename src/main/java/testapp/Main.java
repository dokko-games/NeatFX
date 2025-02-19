package testapp;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.element.impl.Panel4JUI;
import com.dokko.win4jui.api.window.Window4JUI;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            Win4JUI.setDeveloperScreenSize(1920, 1080);
            Win4JUI.initialize();
            Window4JUI window4JUI = new Window4JUI("Test Application", 1100, 800);
            window4JUI.setVisible(true);
            window4JUI.getElements().add(new Panel4JUI(35, 15, 110, 400).setBackground(Color.red));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
