package testapp;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.ui.Input4JUI;
import com.dokko.win4jui.api.ui.design.impl.DefaultDarkDesign;
import com.dokko.win4jui.api.ui.element.Anchors;
import com.dokko.win4jui.api.ui.element.impl.Button4JUI;
import com.dokko.win4jui.api.ui.element.impl.Panel4JUI;
import com.dokko.win4jui.api.ui.element.impl.Text4JUI;
import com.dokko.win4jui.api.ui.window.Window4JUI;


public class Main { //TODO: ImageRenderer4JUI
    public static void main(String[] args) {
        try {
            Win4JUI.setDeveloperScreenSize(1920, 1080);
            Win4JUI.setDesign(new DefaultDarkDesign());
            Win4JUI.initialize();
            Window4JUI window4JUI = new Window4JUI("Win4JUI Test Application", 1100, 800, 30);
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
            window4JUI.setVisible(true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
