package com.dokko.win4jui;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class to set up everything needed for the API to work. Also holds some values for developer usage like the user's screen dimensions.
 * @since v0.0
 */
public class Win4JUI {

    private static int screenWidth, screenHeight;
    //When assigned, when opening a window it will automatically resize to the percentage of the screen
    private static int developerScreenWidth, developerScreenHeight;
    @SuppressWarnings("unused")
    public static Dimension getDeveloperScreenSize() {
        return new Dimension(developerScreenWidth, developerScreenHeight);
    }
    @SuppressWarnings("unused")
    public static void setDeveloperScreenSize(int width, int height){
        developerScreenWidth = width;
        developerScreenHeight = height;
    }
    private static LookAndFeel design = new FlatIntelliJLaf();
    @SuppressWarnings("unused")
    public static LookAndFeel getDesign() {
        return design;
    }
    @SuppressWarnings("unused")
    public static void setDesign(LookAndFeel design) {
        Win4JUI.design = design;
    }
    @SuppressWarnings("unused")
    public static void initialize() throws Exception {
        UIManager.setLookAndFeel(design);
        Win4JUI.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        Win4JUI.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }
    @SuppressWarnings("unused")
    public static int getScreenWidth() {
        return Win4JUI.screenWidth;
    }
    @SuppressWarnings("unused")
    public static int getScreenHeight() {
        return Win4JUI.screenHeight;
    }
    @SuppressWarnings("unused")
    public static String getScreenDimensions() {
        return Win4JUI.getScreenWidth()+"x"+Win4JUI.getScreenHeight();
    }
}
