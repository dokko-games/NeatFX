package com.dokko.win4jui.api;

import com.dokko.win4jui.Win4JUI;

import java.io.PrintStream;
import java.util.Calendar;

public class Logger4JUI {
    private static final String PREFIX = Win4JUI.SDK_NAME;

    public static int infos, warnings, errors, fatals;

    public static void info(String format, Object... parameters){
        infoNoCount(format, parameters);
        infos++;
    }
    public static void infoNoCount(String format, Object... parameters) {
        LOG("info", System.out, format, parameters);
    }
    public static void warn(String format, Object... parameters){
        warnNoCount(format, parameters);
        warnings++;
    }
    public static void warnNoCount(String format, Object... parameters){
        LOG("warning", System.out, format, parameters);
    }
    public static void error(String format, Object... parameters){
        errorNoCount(format, parameters);
        errors++;
    }

    public static void errorNoCount(String format, Object... parameters) {
        LOG("error", System.err, format, parameters);
    }

    public static void fatal(String format, Object... parameters){
        fatalNoCount(format, parameters);
        fatals++;
    }
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
