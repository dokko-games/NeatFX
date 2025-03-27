package com.dokko.win4jui;

import com.dokko.win4jui.api.Logger4JUI;
import com.dokko.win4jui.api.error.Error4JUI;
import com.dokko.win4jui.api.ui.design.Design4JUI;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class to set up everything needed for the API to work. Also holds some values for developer usage like the user's screen dimensions.
 * @since v0.0
 */
public class Win4JUI {
    public static final String SDK_NAME = "Win4JUI";
    public static final long SDK_VERSION = 0x10101220325L;
    // FORMAT: 0x I (debug) II (major) II (minor) II (release day) II (release month) II (release year)
    public static final boolean SDK_IS_DEBUG = (SDK_VERSION & 0xF0000000000L) != 0;

    public static String GET_SDK_VERSION_NAME() {
        String SDK_VERSION_STRING = String.format("%011X", SDK_VERSION);
        String major = SDK_VERSION_STRING.substring(1, 3);
        String minor = SDK_VERSION_STRING.substring(3, 5);
        String day = SDK_VERSION_STRING.substring(5, 7);
        String month = SDK_VERSION_STRING.substring(7, 9);
        String year = SDK_VERSION_STRING.substring(9, 11);

        // Build the version string
        StringBuilder sb = new StringBuilder();
        sb.append("SDK v").append(major).append('.').append(minor);
        if (SDK_IS_DEBUG) sb.append("dbg");

        sb.append(" (").append(day).append('/').append(month).append('/').append(year).append(")");

        return sb.toString();
    }
    public static String GET_SIMPLIFIED_SDK_VERSION_NAME() {
        String SDK_VERSION_STRING = String.format("%011X", SDK_VERSION);
        String major = SDK_VERSION_STRING.substring(1, 3);
        String minor = SDK_VERSION_STRING.substring(3, 5);
        String day = SDK_VERSION_STRING.substring(5, 7);
        String month = SDK_VERSION_STRING.substring(7, 9);
        String year = SDK_VERSION_STRING.substring(9, 11);

        // Build the version string
        StringBuilder sb = new StringBuilder();
        sb.append("SDK.").append(major).append('.').append(minor)
                .append(".").append(day).append('.').append(month).append('.').append(year);

        if (SDK_IS_DEBUG) sb.append("d");

        return sb.toString();
    }
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
        Win4JUI.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        if(getDeveloperScreenSize() != null){
            Logger4JUI.info("Target Screen: %{0}x%{1}", Win4JUI.getDeveloperScreenSize().width, Win4JUI.getDeveloperScreenSize().height);
        }
        Logger4JUI.info("User Screen: %{0}", Win4JUI.getScreenDimensions());
    }
    @SuppressWarnings("unused")
    public static String getScreenDimensions() {
        return Win4JUI.getScreenWidth()+"x"+Win4JUI.getScreenHeight();
    }

    public static void exit(int code){
        if(code < 0){
            Logger4JUI.error("Exited app with exit code '%{0}': %{1} messages, %{2} warnings, %{3} errors and %{4} critical errors"
                    , code, Logger4JUI.infos, Logger4JUI.warnings, Logger4JUI.errors, Logger4JUI.fatals);
        }else {
            Logger4JUI.info("Exited app with exit code '%{0}': %{1} messages, %{2} warnings, %{3} errors and %{4} critical errors"
                    , code, Logger4JUI.infos, Logger4JUI.warnings, Logger4JUI.errors, Logger4JUI.fatals);
        }
        System.exit(code);
    }

    public static void processError(Error4JUI error) {
        error.print();
    }
}
