package sn.ashia.projekt.util;

import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
public class LoggerUtil {
    public static void log(Level level, String message) {
        log.log(level, message);
    }

    public static void log(Level level, String message, Object... params) {
        log.log(level, message, params);
    }

    public static void info(String message) {
        LoggerUtil.log(Level.INFO, message);
    }

    public static void info(String message, Object... params) {
        LoggerUtil.log(Level.INFO, message, params);
    }

    public static void warning(String message) {
        LoggerUtil.log(Level.WARNING, message);
    }

    public static void warning(String message, Object... params) {
        LoggerUtil.log(Level.WARNING, message, params);
    }

    public static void severe(String message) {
        LoggerUtil.log(Level.SEVERE, message);
    }

    public static void severe(String message, Object... params) {
        LoggerUtil.log(Level.SEVERE, message, params);
    }
}
