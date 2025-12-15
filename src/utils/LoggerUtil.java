package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * LoggerUtil class to manage logging.
 * 
 * Author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class LoggerUtil {
    private static Logger logger = Logger.getLogger("GymLogger");

    /**
     * Default private constructor to prevent instantiation.
     */
    private LoggerUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Static block to configure the logger.
     * @throws IOException if there is an error creating the log file.
     * 
     */
    static {
        try {
            FileHandler fh = new FileHandler("gym_log.txt", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the logger instance.
     * 
     * @return Logger instance
     */
    public static Logger getLogger() {
        return logger;
    }
}