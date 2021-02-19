package tools.vitruv.testutils

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import edu.kit.ipd.sdq.activextendannotations.Lazy
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.PatternLayout
import org.junit.jupiter.api.^extension.BeforeAllCallback
import org.junit.jupiter.api.^extension.ExtensionContext
import org.slf4j.LoggerFactory

import static org.apache.log4j.Level.*
import static org.apache.log4j.Logger.getRootLogger
import static org.slf4j.Logger.ROOT_LOGGER_NAME

import static extension org.apache.log4j.Logger.getLogger
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
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
	public static val VM_ARGUMENT_ENABLE_ID_LOGGERS = "vitruv.enableIdLoggers"
	static val LOG_PATTERN = "%d{HH:mm:ss.SSS} [%35.35c{1}] %5p: %m%n"
	static val VITRUV_LOG_ROOTS = List.of("tools.vitruv", "mir.reactions", "mir.routines")

	override beforeAll(ExtensionContext context) throws Exception {
		configureLog4J()
		configureSlf4J()
	}
	
	def private static configureLog4J() {
		rootLogger.removeAllAppenders()
		rootLogger.addAppender(new ConsoleAppender(new PatternLayout(LOG_PATTERN)))
		rootLogger.level = ERROR
		VITRUV_LOG_ROOTS.forEach [logger.level = toLevel(desiredLogLevel, WARN)]
		if (System.getProperty(VM_ARGUMENT_ENABLE_ID_LOGGERS) != "true") {
			UuidGeneratorAndResolverImpl.logger.level = ERROR
		}	
		TestProjectManager.logger.level = INFO
	}
	
	def private static configureSlf4J() {
		val root = LoggerFactory.ILoggerFactory.getLogger(ROOT_LOGGER_NAME)
			if (root instanceof ch.qos.logback.classic.Logger) {
				val consoleAppender = new ch.qos.logback.core.ConsoleAppender => [
					name = "console"
					context = root.loggerContext
					encoder = new PatternLayoutEncoder => [
						context = root.loggerContext
						pattern = LOG_PATTERN
						start()
					]
					start()
				]
				root.detachAndStopAllAppenders()
				root.level = Level.toLevel(desiredLogLevel, Level.ERROR)
				root.addAppender(consoleAppender)
				// there are currently (2021-02-12) no Vitruv loggers, but let's set this up anyway to be prepared
				VITRUV_LOG_ROOTS.forEach [
					(LoggerFactory.ILoggerFactory.getLogger(it) as ch.qos.logback.classic.Logger)
						.level = Level.toLevel(desiredLogLevel, Level.WARN)
				]
			}
	}
}
