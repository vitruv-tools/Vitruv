package tools.vitruv.testutils

import org.junit.jupiter.api.^extension.BeforeAllCallback
import org.junit.jupiter.api.^extension.ExtensionContext

import static org.apache.log4j.Level.*
import static org.apache.log4j.Logger.getLogger
import static org.apache.log4j.Logger.getRootLogger
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.PatternLayout
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.tuid.TuidCalculatorAndResolverBase

/** 
 * Initializes console logger for tests. Sets the logger level to{@link Level#ERROR} by default. If the VM property <i>logOutputLevel</i>
 * is specified, it is used to determine the logger level.
 */
class TestLogging implements BeforeAllCallback {
	static val VM_ARGUMENT_LOG_OUTPUT_LEVEL = "logOutputLevel"
	static val VM_ARGUMENT_LOG_OUTPUT_ID_INFO = "logOutputIdInfo"

	override beforeAll(ExtensionContext context) throws Exception {
		rootLogger.removeAllAppenders()
		rootLogger.addAppender(new ConsoleAppender(new PatternLayout("[%-5p] %d{HH:mm:ss,SSS} %-30C{1} - %m%n")))
		var outputLevelProperty = System.getProperty(VM_ARGUMENT_LOG_OUTPUT_LEVEL)
		if (outputLevelProperty !== null) {
			if (!rootLogger.allAppenders.hasMoreElements()) {
				rootLogger.addAppender(new ConsoleAppender())
			}
			rootLogger.level = toLevel(outputLevelProperty)
		} else {
			rootLogger.level = ERROR
		}
		var String outputIdInfoProperty = System.getProperty(VM_ARGUMENT_LOG_OUTPUT_ID_INFO)
		if (outputIdInfoProperty === null) {
			getLogger(typeof(TuidManager)).level = OFF
			getLogger(typeof(TuidCalculatorAndResolverBase)).level = OFF
			getLogger(typeof(UuidGeneratorAndResolverImpl)).level = OFF
		}
	}

}
