package tools.vitruv.testutils.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.nio.file.Path
import java.util.Date
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.PatternLayout
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.AbstractVitruvDomain
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator
import tools.vitruv.framework.tuid.TuidCalculatorAndResolverBase
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.VirtualModelConfiguration
import tools.vitruv.framework.vsum.VirtualModelImpl

import static java.nio.file.Files.createDirectories
import static java.nio.file.Files.createDirectory
import static java.nio.file.Files.createFile
import static java.nio.file.Files.createTempDirectory
import static java.nio.file.Files.exists
import static org.apache.log4j.Level.*
import static org.apache.log4j.Logger.getLogger
import static org.apache.log4j.Logger.getRootLogger
import static tools.vitruv.framework.util.VitruviusConstants.getTestProjectMarkerFileName

/** 
 * Utility class for setting up Vitruvius test cases
 * @author Langhamm
 * @author Heiko Klare
 */
@Utility
class TestSetup {
	static final String VM_ARGUMENT_LOG_OUTPUT_LEVEL = "logOutputLevel"
	static final String VM_ARGUMENT_LOG_OUTPUT_ID_INFO = "logOutputIdInfo"
	static final String VM_ARGUMENT_TEST_WORKSPACE_PATH = "testWorkspacePath"
	public static final String SOURCE_FOLDER = "src"

	def private static Path addTimestampToProjectNameAndMakeUnique(Path projectName) {
		var timestamp = new Date().toString().replace(" ", "_").replace(":", "_")
		var timestamped = projectName.resolveSibling('''«projectName.fileName»_«timestamp»''')
		var uniqueProject = timestamped
		for (var counter = 0, uniqueProject = timestamped; exists(uniqueProject); counter++) {
			uniqueProject = uniqueProject.resolveSibling('''«timestamped.fileName»--«counter»''')
		}
		uniqueProject
	}

	/** 
	 * Creates a VSUM with the given name in the provided workspace with the provided {@link AbstractVitruvDomain}s and{@link ChangePropagationSpecification}s.
	 * @param virtualModelName- name of the VSUM folder in the temp files directory
	 * @param addTimestampAndMakeNameUnique- specifies if a timestamp shall be added to the name and if
	 * the name shall be made unique so that is does not conflict
	 * with an existing project
	 * @param metamodels- {@link AbstractVitruvDomain}s to add to the VSUM
	 * @param changePropagationSpecifications- {@link ChangePropagationSpecification}s to add to the VSUM
	 * @return the created {@link VirtualModel}
	 */
	def static InternalVirtualModel createVirtualModel(Path workspace, String virtualModelName,
		boolean addTimestampAndMakeNameUnique, Iterable<VitruvDomain> metamodels,
		Iterable<ChangePropagationSpecification> changePropagationSpecifications,
		InternalUserInteractor userInteractor) {
		var projectFolder = if (addTimestampAndMakeNameUnique)
				workspace.resolve(virtualModelName).addTimestampToProjectNameAndMakeUnique()
			else
				workspace.resolve(virtualModelName)
		var VirtualModelConfiguration vmodelConfig = new VirtualModelConfiguration()
		for (VitruvDomain metamodel : metamodels) {
			vmodelConfig.addMetamodel(metamodel)
		}
		for (ChangePropagationSpecification changePropagationSpecification : changePropagationSpecifications) {
			vmodelConfig.addChangePropagationSpecification(changePropagationSpecification)
		}
		new VirtualModelImpl(projectFolder.toFile(), userInteractor, vmodelConfig)
	}

	def static Path createTestWorkspace() {
		var String testWorkspacePath = System::getProperty(VM_ARGUMENT_TEST_WORKSPACE_PATH)
		if (testWorkspacePath === null) {
			createTempDirectory("vitruv-test-workspace") => [
				toFile().deleteOnExit()
			]
		} else {
			createDirectories(Path.of(testWorkspacePath))
		}
	}

	def static Path createProjectFolder(Path workspace, String projectName,
		boolean addTimestampAndMakeNameUniqueAndAddIdentifierFile) {
		var projectFolder = workspace.resolve(projectName)
		if (addTimestampAndMakeNameUniqueAndAddIdentifierFile) {
			projectFolder = projectFolder.addTimestampToProjectNameAndMakeUnique()
		}
		createDirectory(projectFolder)
		createFile(projectFolder.resolve(testProjectMarkerFileName))
		return projectFolder
	}

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
