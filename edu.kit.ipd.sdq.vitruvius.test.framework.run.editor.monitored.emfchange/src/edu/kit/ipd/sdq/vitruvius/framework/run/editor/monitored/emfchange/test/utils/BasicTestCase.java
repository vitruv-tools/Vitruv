package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BasicTestCase {

    private static boolean hasLoggerBeenInitialized = false;
    protected Logger LOGGER = Logger.getLogger(BasicTestCase.class);

    public BasicTestCase() {
        synchronized (BasicTestCase.class) {
            if (!hasLoggerBeenInitialized) {
                org.apache.log4j.BasicConfigurator.configure();
                hasLoggerBeenInitialized = true;
                Logger.getRootLogger().setLevel(Level.ALL);
            }
        }
    }
}
