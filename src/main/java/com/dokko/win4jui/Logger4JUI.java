package com.dokko.win4jui;

import java.io.PrintStream;
import java.util.Calendar;

/**
 * Utility class for Win4JUI logging
 */
public class Logger4JUI {
    private static final String PREFIX = Win4JUI.SDK_VERSION;
    /**
     * The amount of messages sent in each category
     */
    public static int infos, warnings, errors, fatals;

    /**
     * Sends a message if {@link Win4JUI#isAppDebug()} is true
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void infoDebug(String format, Object... parameters){
        if(Win4JUI.isAppDebug()){
            info(format, parameters);
        }
    }
    /**
     * Sends a message if {@link Win4JUI#isAppDebug()} is true, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void infoDebugNoCount(String format, Object... parameters){
        if(Win4JUI.isAppDebug()){
            infoNoCount(format, parameters);
        }
    }
    /**
     * Sends a message
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void info(String format, Object... parameters){
        infoNoCount(format, parameters);
        infos++;
    }
    /**
     * Sends a message, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void infoNoCount(String format, Object... parameters) {
        LOG("info", System.out, format, parameters);
    }
    /**
     * Sends a warning if {@link Win4JUI#isAppDebug()} is true
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void warnDebug(String format, Object... parameters){
        if(Win4JUI.isAppDebug()){
            warn(format, parameters);
        }
    }
    /**
     * Sends a warning if {@link Win4JUI#isAppDebug()} is true, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void warnDebugNoCount(String format, Object... parameters){
        if(Win4JUI.isAppDebug()){
            warnNoCount(format, parameters);
        }
    }
    /**
     * Sends a warning
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void warn(String format, Object... parameters){
        warnNoCount(format, parameters);
        warnings++;
    }
    /**
     * Sends a warning, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void warnNoCount(String format, Object... parameters){
        LOG("warn", System.out, format, parameters);
    }
    /**
     * Sends an error if {@link Win4JUI#isAppDebug()} is true
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void errorDebug(String format, Object... parameters){
        if(Win4JUI.isAppDebug()){
            error(format, parameters);
        }
    }
    /**
     * Sends an error if {@link Win4JUI#isAppDebug()} is true, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void errorDebugNoCount(String format, Object... parameters){
        if(Win4JUI.isAppDebug()){
            errorNoCount(format, parameters);
        }
    }
    /**
     * Sends an error
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void error(String format, Object... parameters){
        errorNoCount(format, parameters);
        errors++;
    }
    /**
     * Sends an error, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void errorNoCount(String format, Object... parameters) {
        LOG("error", System.err, format, parameters);
    }
    /**
     * Sends a fatal error if {@link Win4JUI#isAppDebug()} is true
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void fatalDebug(String format, Object... parameters){
        if(Win4JUI.isAppDebug()){
            fatal(format, parameters);
        }
    }
    /**
     * Sends a fatal error if {@link Win4JUI#isAppDebug()} is true, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void fatalDebugNoCount(String format, Object... parameters){
        if(Win4JUI.isAppDebug()){
            fatalNoCount(format, parameters);
        }
    }
    /**
     * Sends a fatal error
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void fatal(String format, Object... parameters){
        fatalNoCount(format, parameters);
        fatals++;
    }
    /**
     * Sends a fatal error, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void fatalNoCount(String format, Object... parameters){
        LOG("fatal", System.err, format, parameters);
    }

    private static void LOG(String logger, PrintStream printStream, String format, Object[] parameters){
        String message = FORMAT_MESSAGE(format, parameters);
        Calendar c = Calendar.getInstance();
        String prfix = " (" + String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", c.get(Calendar.MONTH)) + "/" + c.get(Calendar.YEAR)
                + " " + String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + ":" + String.format("%02d", c.get(Calendar.SECOND)) + ")";
        printStream.println("["+PREFIX+"/"+logger.toUpperCase()+prfix+"]: "+message);
    }

    private static String FORMAT_MESSAGE(String format, Object[] parameters) {
        String result = format;
        for(int i = 0; i < parameters.length; i++){
            result = result.replace("%{"+i+"}", parameters[i].toString());
        }
        return result;
    }
}
