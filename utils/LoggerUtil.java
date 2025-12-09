package JavaFinalWinter2025.utils;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    private static Logger logger = Logger.getLogger("GymLogger");

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

    public static Logger getLogger() {
        return logger;
    }
}

