package com.dokko.neatfx;

import com.dokko.neatfx.core.window.Anchors;
import com.dokko.neatfx.core.window.Window;
import com.dokko.neatfx.engine.argument.Arguments;
import com.dokko.neatfx.engine.render.text.BFont;
import com.dokko.neatfx.engine.util.design.NeatDesign;
import com.dokko.neatfx.engine.util.design.impl.DefaultBrightDesign;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.Version;

import java.awt.*;
import java.util.ArrayList;

/**
 * Utility class to set up everything needed for the API to work. Also holds some values for developer usage like the user's screen dimensions.
 * @since v0.0
 */
public class NeatFX {
    public static final String LIB_NAME = "NeatFX";
    /**
     * The version of the library
     */
    public static final String LIB_VERSION = LIB_NAME + " b0.01";
    public static String HOME_PATH = System.getProperty("user.home")+"/"+LIB_NAME.toLowerCase();
    /**
     * Returns whether the application is in a debug version or not
     */
    private static boolean APP_IS_DEBUG = false;

    private static boolean initialized = false;
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
     * The size of the developer's screen, AKA, the resolution the program was developed for
     */
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
     * The design of the program
     */
    @Getter @Setter
    private static NeatDesign design;

    /**
     * Initializes the library
     */
    public static void initialize(String[] args) {
        design = new DefaultBrightDesign();
        initialized = true;
        Window.registeredWindows = new ArrayList<>();
        Window.windowsToRemove = new ArrayList<>();
        NeatLogger.info("OpenGL version: \"%{0}\"", Version.getVersion());
        NeatLogger.info("NeatFX version: \"%{0}\"", LIB_VERSION);
        setDesign(design);
        NeatFX.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        NeatFX.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        NeatLogger.info("User Screen: %{0}", NeatFX.getScreenDimensions());
        if(getDeveloperScreenSize() != null && getDeveloperScreenSize().getWidth() != 0){
            NeatLogger.info("Target Screen: %{0}x%{1}", NeatFX.getDeveloperScreenSize().width, NeatFX.getDeveloperScreenSize().height);
            if(getDeveloperScreenSize().width != getScreenWidth() || getDeveloperScreenSize().height != getScreenHeight()){
                NeatLogger.warn("Resolutions do not match. Windows will be scaled");
            }
        }
        NeatLogger.infoDebug("%{0} anchors registered", Anchors.values().length);
        processLibraryArguments(args);
    }

    private static void processLibraryArguments(String[] args) {
        Arguments arguments = new Arguments(Arguments.MODE_IGNORE);
        arguments.accept("store_fonts"); //Store font sprites.
        arguments.addAlias("store_fonts", "sf");
        arguments.accept("debug");
        arguments.addAlias("debug", "d");
        arguments.parse(args);
        if(arguments.hasArgument("store_fonts")) BFont.STORE_FONTS = true;
        if(arguments.hasArgument("debug")) APP_IS_DEBUG = true;
    }

    /**
     * Returns the screen size as a string, with a WIDTH x HEIGHT format
     * @return the screen size
     */
    public static String getScreenDimensions() {
        return NeatFX.getScreenWidth()+"x"+ NeatFX.getScreenHeight();
    }

    /**
     * List with any code that should be run before closing the app
     */
    public static final ArrayList<Runnable> runOnShutdown = new ArrayList<>();

    /**
     * Exits the application, calling every runnable on {@link NeatFX#runOnShutdown}
     * @param code the exit code. Make it be negative if there was an error
     */
    public static void exit(int code){
        if(Window.registeredWindows != null){
            for(Window window : Window.registeredWindows) {
                window.cleanUp();
            }
            Window.registeredWindows.clear();
        }
        runOnShutdown.forEach(Runnable::run);
        if(code < 0){
            NeatLogger.error("Exited app with exit code '%{0}': %{1} messages, %{2} warnings, %{3} errors and %{4} critical errors"
                    , code, NeatLogger.infos, NeatLogger.warnings, NeatLogger.errors, NeatLogger.fatals);
        }else {
            NeatLogger.info("Exited app with exit code '%{0}': %{1} messages, %{2} warnings, %{3} errors and %{4} critical errors"
                    , code, NeatLogger.infos, NeatLogger.warnings, NeatLogger.errors, NeatLogger.fatals);
        }
        NeatLogger.writeFile();
        System.exit(code);
    }

    public static boolean isAppDebug() {
        return APP_IS_DEBUG;
    }

    public static String getFilePath(String folder, String fileName, String extension) {
        return HOME_PATH+"/"+folder+"/"+fileName+"."+extension;
    }
}
