package tools.vitruv.testutils.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.PatternLayout
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.framework.domains.AbstractVitruvDomain
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator
import tools.vitruv.framework.tuid.TuidCalculatorAndResolverBase
import tools.vitruv.framework.tuid.TuidManager

import static org.apache.log4j.Level.*
import static org.apache.log4j.Logger.getLogger
import static org.apache.log4j.Logger.getRootLogger
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl

/** 
 * Utility class for setting up Vitruvius test cases
 * @author Langhamm
 * @author Heiko Klare
 */
@Utility
class TestSetup {
	static val VM_ARGUMENT_LOG_OUTPUT_LEVEL = "logOutputLevel"
	static val VM_ARGUMENT_LOG_OUTPUT_ID_INFO = "logOutputIdInfo"

	/** 
	 * Creates and returns a {@link VitruvDomain}.
	 * @param metamodelRootPackage- the root {@link EPackage} of the {@link VitruvDomain} to
	 * create
	 * @param fileExt- fileExtension for which the {@link VitruvDomain} is
	 * responsible
	 * @return the create {@link VitruvDomain}
	 */
	def static VitruvDomain createVitruvDomain(String name, EPackage metamodelRootPackage, String fileExt) {
		new AbstractVitruvDomain(name, metamodelRootPackage, fileExt) {
			override VitruviusProjectBuilderApplicator getBuilderApplicator() {
				return null
			}
		}
	}

	/** 
	 * Initializes console logger for tests. Sets the logger level to{@link Level#ERROR} by default. If the VM property <i>logOutputLevel</i>
	 * is specified, it is used to determine the logger level.
	 */
	def static void initializeLogger() {
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
