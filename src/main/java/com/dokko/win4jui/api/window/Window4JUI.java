package com.dokko.win4jui.api.window;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.Input4JUI;
import com.dokko.win4jui.api.element.Element4JUI;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @since v0.0
 */
public class Window4JUI extends JFrame {
    private int targetWidth, targetHeight;
    @Getter
    private final ArrayList<Element4JUI> elements;

    private class ScalingPanel extends JPanel {
        public ScalingPanel(int fps) {
            Timer t = new Timer(1000 / fps, e -> repaint());
            t.start();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            float scalingFactorW = (float) getWidth() / targetWidth;
            float scalingFactorH = (float) getHeight() / targetHeight;
            for(Element4JUI element4JUI : getElements()) {
                element4JUI.windowWidth = getWidth();
                element4JUI.windowHeight = getHeight();
                element4JUI.render(graphics2D, element4JUI.getXDistance(), element4JUI.getYDistance(),
                        element4JUI.getWidth(), element4JUI.getHeight(), scalingFactorW, scalingFactorH);
            }
        }
    }
    @SuppressWarnings("unused")
    public void changeTargetSize(int width, int height){
        targetWidth = width;
        targetHeight = height;
        for(Element4JUI element4JUI : getElements()) {
            element4JUI.windowTargetWidth = width;
            element4JUI.windowTargetHeight = height;
        }
    }
    public Window4JUI(String title, int expectedWidth, int expectedHeight, int updatesPerSecond) {
        if(Win4JUI.getScreenWidth() == 0){
            //todo error
            System.err.println("Error: SDK was not initialized (run Win4JUI.initialize)");
            System.exit(-1);
        }
        elements = new ArrayList<>();
        changeTargetSize(expectedWidth, expectedHeight);
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
        ScalingPanel scalingPanel = getScalingPanel(updatesPerSecond);
        add(scalingPanel);
    }

    private ScalingPanel getScalingPanel(int fps) {
        ScalingPanel scalingPanel = new ScalingPanel(fps);
        scalingPanel.setFocusable(true);
        scalingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Input4JUI.setButtonDown(e.getButton(), true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Input4JUI.setButtonDown(e.getButton(), false);
            }
        });
        scalingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Input4JUI.setPosition(e.getX(), e.getY());
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                Input4JUI.setPosition(e.getX(), e.getY());
            }
        });
        KeyAdapter listener = new KeyAdapter() {
            private final Set<Integer> pressedKeys = new HashSet<>();
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (!pressedKeys.contains(keyCode)) {  // Only trigger once per key press
                    pressedKeys.add(keyCode);
                    Input4JUI.setKeyDown(keyCode, true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                Input4JUI.setKeyDown(keyCode, false);
                pressedKeys.remove(keyCode); // Remove from the set when released
            }
        };
        this.addKeyListener(listener);
        scalingPanel.addKeyListener(listener);
        scalingPanel.setRequestFocusEnabled(true);
        scalingPanel.requestFocus();
        return scalingPanel;
    }
}
