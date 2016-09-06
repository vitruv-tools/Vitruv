package edu.kit.ipd.sdq.vitruvius.applications.jmljava.initializer;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Utility class for setting the default logger properties.
 */
public class LoggingSettings {

    /**
     * Sets the default logging properties for the Apache Log4J framework.
     */
    public static void setup() {
        Logger.getRootLogger().setLevel(Level.TRACE);
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(
                new ConsoleAppender(new PatternLayout("[%-5p] %d{HH:mm:ss,SSS} %-30C{1} - %m%n")));
    }

}
