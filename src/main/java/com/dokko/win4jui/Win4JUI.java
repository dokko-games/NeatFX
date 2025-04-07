package com.dokko.win4jui;

import com.dokko.win4jui.core.window.Anchors;
import com.dokko.win4jui.core.window.Window;
import com.dokko.win4jui.engine.util.design.Design4JUI;
import com.dokko.win4jui.engine.util.design.impl.DefaultBrightDesign;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.Version;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to set up everything needed for the API to work. Also holds some values for developer usage like the user's screen dimensions.
 * @since v0.0
 */
public class Win4JUI {
    private static boolean initialized = false;

    public static Map<String, Object> globalParameters;


    /**
     * The version of the Win4JUI SDK
     */
    public static final String SDK_VERSION = "Win4JUI b0.01 DEBUG";
    /**
     * Returns whether the application is in a debug version or not
     */
    public static boolean APP_IS_DEBUG = false;
    /**
     * The size of the user's screen
     */
    @Getter
    private static int screenWidth, screenHeight;
    /**
     * The size of the developer's screen, AKA, the resolution the program was developed for
     */
    private static int developerScreenWidth = 0, developerScreenHeight = 0;
    /**
     * The design of the program
     */
    @Getter @Setter
    private static Design4JUI design;
    public static Dimension getDeveloperScreenSize() {
        if(!initialized)return null;
        return new Dimension(developerScreenWidth, developerScreenHeight);
    }

    /**
     * Sets the target screen resolution. It will automatically resize frames if different from the users resolution
     * @param width the width of the target screen
     * @param height the height of the target screen
     */
    public static void setDeveloperScreenSize(int width, int height){
        developerScreenWidth = width;
        developerScreenHeight = height;
    }

    /**
     * Initializes the Win4JUI SDK
     */
    public static void initialize() {
        design = new DefaultBrightDesign();
        initialized = true;
        globalParameters = new HashMap<>();
        Window.registeredWindows = new ArrayList<>();
        Window.windowsToRemove = new ArrayList<>();
        Logger4JUI.info("OpenGL version: %{0}", Version.getVersion());
        Logger4JUI.info("Win4JUI version: \"%{0}\"", SDK_VERSION);
        setDesign(design);
        Win4JUI.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        Win4JUI.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        Logger4JUI.info("User Screen: %{0}", Win4JUI.getScreenDimensions());
        if(getDeveloperScreenSize() != null && getDeveloperScreenSize().getWidth() != 0){
            Logger4JUI.info("Target Screen: %{0}x%{1}", Win4JUI.getDeveloperScreenSize().width, Win4JUI.getDeveloperScreenSize().height);
            if(getDeveloperScreenSize().width != getScreenWidth() || getDeveloperScreenSize().height != getScreenHeight()){
                Logger4JUI.warn("Resolutions do not match. Windows will be scaled");
            }
        }
        Logger4JUI.infoDebug("%{0} anchors registered", Anchors.values().length);
    }

    /**
     * Returns the screen size as a string, with a WIDTH x HEIGHT format
     * @return the screen size
     */
    public static String getScreenDimensions() {
        return Win4JUI.getScreenWidth()+"x"+Win4JUI.getScreenHeight();
    }

    /**
     * List with any code that should be run before closing the app
     */
    public static final ArrayList<Runnable> runOnShutdown = new ArrayList<>();

    /**
     * Exits the application, calling every runnable on {@link Win4JUI#runOnShutdown}
     * @param code the exit code. Make it be negative if there was an error
     */
    public static void exit(int code){
        for(Window window : Window.registeredWindows) {
            window.cleanUp();
        }
        Window.registeredWindows.clear();
        runOnShutdown.forEach(Runnable::run);
        if(code < 0){
            Logger4JUI.error("Exited app with exit code '%{0}': %{1} messages, %{2} warnings, %{3} errors and %{4} critical errors"
                    , code, Logger4JUI.infos, Logger4JUI.warnings, Logger4JUI.errors, Logger4JUI.fatals);
        }else {
            Logger4JUI.info("Exited app with exit code '%{0}': %{1} messages, %{2} warnings, %{3} errors and %{4} critical errors"
                    , code, Logger4JUI.infos, Logger4JUI.warnings, Logger4JUI.errors, Logger4JUI.fatals);
        }
        System.exit(code);
    }
}
