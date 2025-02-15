package com.dokko.win4jui.api.window;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.element.Element4JUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @since v0.0
 */
public class Window4JUI extends JFrame {
    private int targetWidth, targetHeight;
    private final ArrayList<Element4JUI> elements;

    public ArrayList<Element4JUI> getElements() {
        return elements;
    }

    private class ScalingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            float scalingFactorW = (float) getWidth() / targetWidth;
            float scalingFactorH = (float) getHeight() / targetHeight;
            for(Element4JUI element4JUI : getElements()) {
                element4JUI.render(graphics2D, element4JUI.getX() * scalingFactorW, element4JUI.getY() * scalingFactorH,
                        element4JUI.getWidth() * scalingFactorW, element4JUI.getHeight() * scalingFactorH, scalingFactorW, scalingFactorH);
            }
        }
    }
    @SuppressWarnings("unused")
    public void changeTargetSize(int width, int height){
        targetWidth = width;
        targetHeight = height;
    }
    public Window4JUI(String title, int expectedWidth, int expectedHeight) {
        elements = new ArrayList<>();
        targetWidth = expectedWidth;
        targetHeight = expectedHeight;
        if(Win4JUI.getDeveloperScreenSize() != null){
            float initialScalingFactorWidth = (float) Win4JUI.getScreenWidth() / Win4JUI.getDeveloperScreenSize().width;
            float initialScalingFactorHeight = (float) Win4JUI.getScreenHeight() / Win4JUI.getDeveloperScreenSize().height;
            expectedWidth = (int) (expectedWidth * initialScalingFactorWidth);
            expectedHeight = (int) (expectedHeight * initialScalingFactorHeight);
        }
        setSize(expectedWidth, expectedHeight);
        setTitle(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ScalingPanel scalingPanel = new ScalingPanel();
        add(scalingPanel);
    }
}
