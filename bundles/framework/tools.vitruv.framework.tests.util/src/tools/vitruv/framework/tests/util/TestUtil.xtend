package tools.vitruv.framework.tests.util

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.Date
import java.util.List

import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout

import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IProjectDescription
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.ecore.EPackage
import org.eclipse.jdt.core.IClasspathEntry
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.IPackageFragmentRoot
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.launching.IVMInstall
import org.eclipse.jdt.launching.JavaRuntime
import org.eclipse.jdt.launching.LibraryLocation

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.AbstractVitruvDomain
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.VirtualModelConfiguration

/**
 * Utility class for all Vitruvius test cases
 * @author Langhamm
 * @author Heiko Klare
 */
final class TestUtil {
	static val VM_ARGUMENT_LOG_OUTPUT_LEVEL = "logOutputLevel"
	static val VM_ARGUMENT_TEST_WORKSPACE_PATH = "testWorkspacePath"
	public static val SOURCE_FOLDER = "src"

	/**
	 * Utility classes should not have a public constructor
	 */
	private new() {
	}

	/**
	 * Creates a test project with the given name. It automatically adds a
	 * timestamp to the name.
	 * @param projectName- name of the project to create
	 * @param addTimestampAndMakeNameUnique- specifies if a timestamp shall be added to the name and if
	 * the name shall be made unique so that is does not conflict
	 * with an existing project
	 * @return the created {@link IProject}
	 * @throws IllegalStateExceptionif project with given name already exists and its not
	 * specified to make name unique
	 */
	def static IProject createPlatformProject(String projectName, boolean addTimestampAndMakeNameUnique) {
		var String finalProjectName = projectName
		if (addTimestampAndMakeNameUnique) {
			finalProjectName = addTimestampToProjectNameAndMakeUnique(finalProjectName)
		}
		val IProject testProject = TestUtil::getProjectByName(finalProjectName)
		if (testProject.exists)
			throw new IllegalStateException("Project already exists")
		try {
			return initializeProject(testProject)
		} catch (CoreException e) {
			throw new IllegalStateException("Project could not be created")
		}

	}

	def static File addTimestampToProjectNameAndMakeUnique(File projectFolder) {
		val String timestampedProjectName = addTimestampToString(projectFolder.toString)
		var File timestampedProjectFolder = new File(timestampedProjectName)
		var String countedProjectName = timestampedProjectName
		// If project exists, add an index
		var int counter = 1
		while (timestampedProjectFolder.exists) {
			countedProjectName = '''«timestampedProjectName»--«counter++»'''.toString
			timestampedProjectFolder = new File(countedProjectName)
		}
		return timestampedProjectFolder
	}

	def static String addTimestampToProjectNameAndMakeUnique(String projectName) {
		val String timestampedProjectName = addTimestampToString(projectName)
		var IProject testProject = TestUtil::getProjectByName(timestampedProjectName)
		var String countedProjectName = timestampedProjectName
		// If project exists, add an index
		var int counter = 1
		while (testProject.exists) {
			countedProjectName = '''«timestampedProjectName»--«counter++»'''.toString
			testProject = TestUtil::getProjectByName(countedProjectName)
		}
		return countedProjectName
	}

	def static IProject initializeProject(IProject testProject) throws CoreException {
		// copied from:
		// https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Creating_Eclipse_Java_Projects_Programmatically
		testProject.create(new NullProgressMonitor)
		testProject.open(new NullProgressMonitor)
		val IProjectDescription description = testProject.description
		description.setNatureIds((#[JavaCore.NATURE_ID] as String[]))
		testProject.setDescription(description, null)
		val IJavaProject javaProject = JavaCore::create(testProject)
		val IFolder binFolder = testProject.getFolder("bin")
		binFolder.create(false, true, null)
		javaProject.setOutputLocation(binFolder.fullPath, null)
		val List<IClasspathEntry> entries = newArrayList
		val IVMInstall vmInstall = JavaRuntime::defaultVMInstall
		if (null !== vmInstall) {
			val LibraryLocation[] locations = JavaRuntime::getLibraryLocations(vmInstall)
			for (LibraryLocation element : locations) {
				entries.add(JavaCore.newLibraryEntry(element.systemLibraryPath, null, null))
			}
		}
		// add libs to project class path
		javaProject.setRawClasspath(entries.toArray(newArrayOfSize(entries.size)), null)
		val IFolder sourceFolder = testProject.getFolder("src")
		sourceFolder.create(false, true, null)
		val IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(sourceFolder)
		val IClasspathEntry[] oldEntries = javaProject.rawClasspath
		val IClasspathEntry[] newEntries = newArrayOfSize(oldEntries.length + 1)
		System::arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length)
		val _wrVal_newEntries = newEntries
		val _wrIndx_newEntries = oldEntries.length
		_wrVal_newEntries.set(_wrIndx_newEntries, JavaCore::newSourceEntry(root.path))
		javaProject.setRawClasspath(newEntries, null)
		return testProject
	}

	/**
	 * Creates a VSUM in the given folder, with the given {@link AbstractVitruvDomain}s and{@link ChangePropagationSpecification}s.
	 * @param virtualModelFolder- folder of the VSUM
	 * @param addTimestampAndMakeNameUnique- specifies if a timestamp shall be added to the name and if
	 * the name shall be made unique so that is does not conflict
	 * with an existing project
	 * @param metamodels- {@link AbstractVitruvDomain}s to add to the VSUM
	 * @param changePropagationSpecifications- {@link ChangePropagationSpecification}s to add to the VSUM
	 * @return the created {@link VirtualModel}
	 */
	def static InternalVirtualModel createVirtualModel(
		File virtualModelFolder,
		boolean addTimestampAndMakeNameUnique,
		Iterable<VitruvDomain> metamodels,
		Iterable<ChangePropagationSpecification> changePropagationSpecifications,
		UserInteracting userInteracting
	) {
		val projectFolder = if (addTimestampAndMakeNameUnique)
				addTimestampToProjectNameAndMakeUnique(virtualModelFolder)
			else
				virtualModelFolder
		val vmodelConfig = new VirtualModelConfiguration
		metamodels.forEach[vmodelConfig.addMetamodel(it)]
		changePropagationSpecifications.forEach[vmodelConfig.addChangePropagationSpecification(it)]
		val vmodel = InternalVirtualModel::createInternalTestVirtualModel(
			projectFolder,
			userInteracting,
			vmodelConfig
		)
		return vmodel
	}

	/**
	 * Creates a VSUM with the given name in a temporary folder, {@link AbstractVitruvDomain}s and{@link ChangePropagationSpecification}s.
	 * @param virtualModelName- name of the VSUM folder in the temp files directory
	 * @param addTimestampAndMakeNameUnique- specifies if a timestamp shall be added to the name and if
	 * the name shall be made unique so that is does not conflict
	 * with an existing project
	 * @param metamodels- {@link AbstractVitruvDomain}s to add to the VSUM
	 * @param changePropagationSpecifications- {@link ChangePropagationSpecification}s to add to the VSUM
	 * @return the created {@link VirtualModel}
	 */
	def static InternalVirtualModel createVirtualModel(
		String virtualModelName,
		boolean addTimestampAndMakeNameUnique,
		Iterable<VitruvDomain> metamodels,
		Iterable<ChangePropagationSpecification> changePropagationSpecifications,
		UserInteracting userInteracting
	) {
		val File testWorkspace = createTestWorkspace
		return createVirtualModel(
			new File(testWorkspace, virtualModelName),
			addTimestampAndMakeNameUnique,
			metamodels,
			changePropagationSpecifications,
			userInteracting
		)
	}

	def static File createTestWorkspace() {
		val String testWorkspacePath = System::getProperty(VM_ARGUMENT_TEST_WORKSPACE_PATH)
		var File testWorkspace = null
		if (testWorkspacePath === null) {
			try {
				testWorkspace = Files.createTempDirectory("vitruv-test-workspace").toFile();
				testWorkspace.deleteOnExit();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			testWorkspace = new File(testWorkspacePath);
		}
		if (!testWorkspace.exists)
			testWorkspace.mkdir
		return testWorkspace
	}

	def static File createProjectFolder(String projectName, boolean addTimestampAndMakeNameUnique) {
		val File testWorkspace = createTestWorkspace
		var File projectFolder = new File(testWorkspace, projectName)
		if (addTimestampAndMakeNameUnique)
			projectFolder = addTimestampToProjectNameAndMakeUnique(projectFolder)
		projectFolder.mkdir
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
		return new AbstractVitruvDomain(name, metamodelRootPackage,
			new AttributeTuidCalculatorAndResolver(metamodelRootPackage.nsURI), fileExt) {
			override getBuilderApplicator() { null }
		}
	}

	def static String addTimestampToString(String originalString) {
		val timestamp = new Date(System::currentTimeMillis()).toString.replace(" ", "_").replace(":", "_")
		val stringWithTimeStamp = '''«originalString»_«timestamp»'''
		return stringWithTimeStamp
	}

	/**
	 * Initializes console logger for tests. Sets the logger level to{@link Level#ERROR} by default. If the VM property <i>logOutputLevel</i>
	 * is specified, it is used to determine the logger level.
	 */
	def static void initializeLogger() {
		Logger::rootLogger.level = Level::ERROR
		Logger::rootLogger.removeAllAppenders
		Logger::rootLogger.addAppender(
			new ConsoleAppender(new PatternLayout("[%-5p] %d{HH:mm:ss,SSS} %-30C{1} - %m%n")))
		val String outputLevelProperty = System::getProperty(VM_ARGUMENT_LOG_OUTPUT_LEVEL)
		if (outputLevelProperty !== null) {
			if (!Logger.rootLogger.allAppenders.hasMoreElements) {
				Logger.rootLogger.addAppender(new ConsoleAppender)
			}
			Logger.rootLogger.setLevel(Level.toLevel(outputLevelProperty))
		} else {
			Logger.rootLogger.level = Level::ERROR
		}
	}

	def static IProject getProjectByName(String projectName) {
		val iProject = ResourcesPlugin::workspace.root.getProject(projectName)
		return iProject
	}
}
