package com.dokko.win4jui;

import com.dokko.win4jui.api.design.Design4JUI;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class to set up everything needed for the API to work. Also holds some values for developer usage like the user's screen dimensions.
 * @since v0.0
 */
public class Win4JUI {
    public static final int SDK_VERSION = 0x1000001;
    @Getter
    private static int screenWidth, screenHeight;
    private static int developerScreenWidth = 0, developerScreenHeight = 0;
    @Getter
    @Setter
    private static String defaultFont = "Press Start 2P";
    private static Design4JUI design;
    @SuppressWarnings("unused")
    public static Design4JUI getDesign() {
        return design;
    }
    @SuppressWarnings("unused")
    public static void setDesign(Design4JUI design) throws Exception {
        Win4JUI.design = design;
        UIManager.setLookAndFeel(Win4JUI.getDesign().getLookAndFeel());
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
        Win4JUI.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height; //TODO: replace with logger
        if(getDeveloperScreenSize() != null){
            System.out.println("Target Screen: "+Win4JUI.getDeveloperScreenSize().width+"x"+Win4JUI.getDeveloperScreenSize().height);
        }
        System.out.println("User Screen: "+Win4JUI.getScreenDimensions());
    }
    @SuppressWarnings("unused")
    public static String getScreenDimensions() {
        return Win4JUI.getScreenWidth()+"x"+Win4JUI.getScreenHeight();
    }

}
