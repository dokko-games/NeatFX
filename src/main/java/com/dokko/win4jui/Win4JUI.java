package com.dokko.win4jui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class to set up everything needed for the API to work. Also holds some values for developer usage like the user's screen dimensions.
 * @since v0.0
 */
public class Win4JUI {

    private static int screenWidth, screenHeight;
    private static int developerScreenWidth = 0, developerScreenHeight = 0;
    private static boolean darkTheme;
    @SuppressWarnings("unused")
    public static boolean isDarkTheme() {
        return darkTheme;
    }
    private static LookAndFeel design = new FlatDarculaLaf();
    @SuppressWarnings("unused")
    public static LookAndFeel getDesign() {
        return design;
    }
    @SuppressWarnings("unused")
    public static void setDesign(LookAndFeel design) throws Exception {
        Win4JUI.design = design;
        UIManager.setLookAndFeel(Win4JUI.getDesign());
        darkTheme = FlatLaf.isLafDark();
    }
    @SuppressWarnings("unused")
    public static Dimension getDeveloperScreenSize() {
        if(developerScreenWidth == 0 || developerScreenHeight == 0)return null;
        return new Dimension(developerScreenWidth, developerScreenHeight);
    }
    /**After called, when opening a window it will automatically resize to the percentage of the screen*/
    @SuppressWarnings("unused")
    public static void setDeveloperScreenSize(int width, int height){
        developerScreenWidth = width;
        developerScreenHeight = height;
    }
    @SuppressWarnings("unused")
    public static void initialize() throws Exception {
        setDesign(design);
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
