package tools.vitruv.change.testutils

import edu.kit.ipd.sdq.activextendannotations.Lazy
import org.apache.logging.log4j.Level
import org.junit.jupiter.api.^extension.BeforeAllCallback
import org.junit.jupiter.api.^extension.ExtensionContext

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;

import java.util.List

/**
 * Initializes console logger for tests. Sets the logger level to {@code WARN} by default. If the VM property
 * {@link VM_ARGUMENT_LOG_LEVEL} is specified, it is used to determine the logger level.
 */
class TestLogging implements BeforeAllCallback {
	@Lazy static val String desiredLogLevel = System.getProperty(VM_ARGUMENT_LOG_LEVEL) ?: "WARN"
	/**
	 * The default root log level. Defaults to {@link Level#WARN}. 
	 */
	public static val VM_ARGUMENT_LOG_LEVEL = "vitruv.logLevel"
	static val LOG_PATTERN = "%d{HH:mm:ss.SSS} [%35.35c{1}] %5p: %m%n"
	static val VITRUV_LOG_ROOTS = List.of("tools.vitruv", "mir.reactions", "mir.routines")

	override beforeAll(ExtensionContext context) throws Exception {
		configureLog4J()
	}
	
	def private static configureLog4J() {
        val builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        builder.setStatusLevel(Level.ERROR);
        builder.setConfigurationName("RollingBuilder");
        // create the console appender
        var appenderBuilder =
                builder.newAppender("Stdout", "CONSOLE").addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(builder.newLayout("PatternLayout").addAttribute("pattern",LOG_PATTERN));
        builder.add(appenderBuilder);
        val config = builder.build();
        config.initialize();
        VITRUV_LOG_ROOTS.forEach [loggerName |
            val logger = LogManager.getLogger(loggerName) as org.apache.logging.log4j.core.Logger
            logger.level = Level.toLevel(desiredLogLevel, Level.WARN)
        ]
        val testLogger = LogManager.getLogger(TestProjectManager) as org.apache.logging.log4j.core.Logger
        testLogger.level = Level.INFO
	}
}