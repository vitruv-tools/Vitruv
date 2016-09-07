package tools.vitruvius.extensions.constructionsimulation;

import java.io.File;
import java.net.URL;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class LoggerConfigurator {

    private static String LOGFILE = "Logs/integration.log";
    private static File logFile = null;

    public static void setUpLogger() {
        clearLogFile();
    }

    /**
     * clears the old log file if it exists
     */
    public static void clearLogFile() {
        final Bundle bundle = Platform.getBundle("tools.vitruvius.integration");

        String location = bundle.getLocation();
        location = location.substring(16);

        logFile = new File(location + LOGFILE);

        try {
            // Delete if logFile exists
            if (logFile.exists()) {
                logFile.delete();
            }

            logFile.createNewFile();

        } catch (Exception e) {
            // if any error occurs
            e.printStackTrace();
        }

        LoggerConfigurator.configureLogger(logFile);
    }

    public static void configureLogger(File logFile) {
        Bundle bundle = Platform.getBundle("tools.vitruvius.integration");
        URL fileURL = bundle.getEntry("log4j.xml");
        DOMConfigurator.configure(fileURL);

        FileAppender appender = new FileAppender();
        appender.setName("LogFile");
        appender.setFile(logFile.getAbsolutePath());
        appender.setThreshold(Level.INFO);
        appender.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
        appender.setAppend(true);
        appender.activateOptions();

        Logger.getRootLogger().addAppender(appender);
    }

}
