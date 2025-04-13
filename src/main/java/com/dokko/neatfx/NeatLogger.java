package com.dokko.neatfx;

import com.dokko.neatfx.core.error.Error;
import com.dokko.neatfx.engine.util.NeatFiles;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Calendar;

/**
 * Utility class for logging
 */
public class NeatLogger {

    private static final StringBuilder loggerString = new StringBuilder();

    private static final String PREFIX = NeatFX.LIB_VERSION;
    /**
     * The amount of messages sent in each category
     */
    public static int infos, warnings, errors, fatals;

    /**
     * Sends a message if {@link NeatFX#isAppDebug()} is true
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void infoDebug(String format, Object... parameters){
        if(NeatFX.isAppDebug()){
            info(format, parameters);
        }
    }
    /**
     * Sends a message if {@link NeatFX#isAppDebug()} is true, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void infoDebugNoCount(String format, Object... parameters){
        if(NeatFX.isAppDebug()){
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
     * Sends a warning if {@link NeatFX#isAppDebug()} is true
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void warnDebug(String format, Object... parameters){
        if(NeatFX.isAppDebug()){
            warn(format, parameters);
        }
    }
    /**
     * Sends a warning if {@link NeatFX#isAppDebug()} is true, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void warnDebugNoCount(String format, Object... parameters){
        if(NeatFX.isAppDebug()){
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
     * Sends an error if {@link NeatFX#isAppDebug()} is true
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void errorDebug(String format, Object... parameters){
        if(NeatFX.isAppDebug()){
            error(format, parameters);
        }
    }
    /**
     * Sends an error if {@link NeatFX#isAppDebug()} is true, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void errorDebugNoCount(String format, Object... parameters){
        if(NeatFX.isAppDebug()){
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
     * Sends a fatal error if {@link NeatFX#isAppDebug()} is true
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void fatalDebug(String format, Object... parameters){
        if(NeatFX.isAppDebug()){
            fatal(format, parameters);
        }
    }
    /**
     * Sends a fatal error if {@link NeatFX#isAppDebug()} is true, but without adding it to the message track
     * @param format the format of the message. For parameters, input %{n}, where n is the number of the parameter
     * @param parameters the list of parameters of the message
     */
    public static void fatalDebugNoCount(String format, Object... parameters){
        if(NeatFX.isAppDebug()){
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
        for(String line : message.split("\n")){
            Calendar c = Calendar.getInstance();
            String prfix = " (" + String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", c.get(Calendar.MONTH)) + "/" + c.get(Calendar.YEAR)
                    + " " + String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", c.get(Calendar.MINUTE)) + ":" + String.format("%02d", c.get(Calendar.SECOND)) + ")";
            printStream.println("["+PREFIX+"/"+logger.toUpperCase()+prfix+"]: "+line);
            loggerString.append("[" + PREFIX + "/").append(logger.toUpperCase()).append(prfix).append("]: ").append(line);
            loggerString.append("\n");
        }
    }

    private static String FORMAT_MESSAGE(String format, Object[] parameters) {
        String result = format;
        for(int i = 0; i < parameters.length; i++){
            result = result.replace("%{"+i+"}", parameters[i].toString());
        }
        return result;
    }

    public static void writeFile() {
        Calendar c = Calendar.getInstance();
        File todayFile = new File(NeatFX.getFilePath("logs", String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "_" + String.format("%02d", c.get(Calendar.MONTH)) + "_" + c.get(Calendar.YEAR)
                + "_" + String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + "_" + String.format("%02d", c.get(Calendar.MINUTE)) + "_" + String.format("%02d", c.get(Calendar.SECOND)), "log"));
        long size = NeatFiles.size(todayFile.getParentFile().toPath());
        if(size > 5000000000L){
            int dataI = (int)(size / 10000000L);
            float dataF = (float)dataI / 100;
            NeatLogger.warn("More than 5GB of logs found (%{0}GB). Clearing all log data", dataF);
            NeatFiles.delete(todayFile.getParentFile());
        }
        try {
            todayFile.getParentFile().mkdirs();
            Files.writeString(todayFile.toPath(), loggerString.toString());
        }catch (Exception e){
            throw Error.from(e).getException();
        }
    }
}
