package testapp;

import com.dokko.win4jui.Win4JUI;

public class Main {
    public static void main(String[] args) {
        try {
            Win4JUI.initialize();
            System.out.println(Win4JUI.getScreenDimensions());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
