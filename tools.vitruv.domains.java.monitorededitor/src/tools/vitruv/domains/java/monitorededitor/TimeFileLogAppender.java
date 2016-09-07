package tools.vitruv.domains.java.monitorededitor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * {@link FileAppender} for {@link ExecutionTimeLoggingProgressMonitor}, it retrieves the duration
 * in seconds from the message and uses one row to store it in the file.
 * 
 */
public final class TimeFileLogAppender extends FileAppender {

    private static final String TIME_FORMAT = "yyyy-MM-dd HH.mm.ss";
    private static final String MSG_PATTERN = "TimeMeasurement:";
    private static TimeFileLogAppender app = null;

    private TimeFileLogAppender(String file) throws IOException {
        super(new PatternLayout(), file);
    }

    @Override
    public synchronized void doAppend(LoggingEvent event) {
        String msg = event.getRenderedMessage();
        int index = msg.indexOf(MSG_PATTERN);
        if (index == -1) {
            return;
        } else {
            index += MSG_PATTERN.length();
            String csv = msg.substring(index);
            super.doAppend(new LoggingEvent(event.getFQNOfLoggerClass(), event.getLogger(), event.getLevel(), csv, null));
        }
    }

    public static synchronized TimeFileLogAppender createInstanceFor(String id, String folderPath) throws IOException {
        if (app == null) {
            app = new TimeFileLogAppender(createLogFileName(id, folderPath));
        }
        return app;
    }

    public static synchronized void resetInstance() {
        app = null;
    }

    private static String createLogFileName(String id, String folderPath) {
        folderPath += "/" + id + "/";
        SimpleDateFormat form = new SimpleDateFormat(TIME_FORMAT);
        String time = form.format(new Date());
        String name = id + " " + time + ".csv";
        return folderPath + name;
    }

}
