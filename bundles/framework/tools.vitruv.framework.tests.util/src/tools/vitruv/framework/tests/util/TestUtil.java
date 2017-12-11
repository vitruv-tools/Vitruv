package tools.vitruv.framework.tests.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator;
import tools.vitruv.framework.domains.AbstractVitruvDomain;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.VitruviusConstants;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelConfiguration;
import tools.vitruv.framework.vsum.VirtualModelImpl;

/**
 * Utility class for all Vitruvius test cases
 *
 * @author Langhamm
 * @author Heiko Klare
 *
 */
public final class TestUtil {
	private static final String VM_ARGUMENT_LOG_OUTPUT_LEVEL = "logOutputLevel";
	private static final String VM_ARGUMENT_TEST_WORKSPACE_PATH = "testWorkspacePath";
	public static final String SOURCE_FOLDER = "src";

	/**
	 * Utility classes should not have a public constructor
	 */
	private TestUtil() {
	}

	/**
	 * Creates a test project with the given name. It automatically adds a
	 * timestamp to the name.
	 * 
	 * @param projectName
	 *            - name of the project to create
	 * @param addTimestampAndMakeNameUnique
	 *            - specifies if a timestamp shall be added to the name and if
	 *            the name shall be made unique so that is does not conflict
	 *            with an existing project
	 * @return the created {@link IProject}
	 * @throws IllegalStateException
	 *             if project with given name already exists and its not
	 *             specified to make name unique
	 */
	public static IProject createPlatformProject(String projectName, boolean addTimestampAndMakeNameUnique) {
		String finalProjectName = projectName;
		if (addTimestampAndMakeNameUnique) {
			finalProjectName = addTimestampToProjectNameAndMakeUnique(finalProjectName);
		}
		IProject testProject = TestUtil.getProjectByName(finalProjectName);

		if (testProject.exists()) {
			throw new IllegalStateException("Project already exists");
		}

		try {
			return initializeProject(testProject);
		} catch (CoreException e) {
			throw new IllegalStateException("Project could not be created");
		}
	}
	
	private static File addTimestampToProjectNameAndMakeUnique(File projectFolder) {
		String timestampedProjectName = addTimestampToString(projectFolder.toString());
		File timestampedProjectFolder = new File(timestampedProjectName);

		String countedProjectName = timestampedProjectName;
		// If project exists, add an index
		int counter = 1;
		while (timestampedProjectFolder.exists()) {
			countedProjectName = timestampedProjectName + "--" + counter++;
			timestampedProjectFolder = new File(countedProjectName);
		}
		return timestampedProjectFolder;
	}
	
	private static String addTimestampToProjectNameAndMakeUnique(String projectName) {
		String timestampedProjectName = addTimestampToString(projectName);
		IProject testProject = TestUtil.getProjectByName(timestampedProjectName);

		String countedProjectName = timestampedProjectName;
		// If project exists, add an index
		int counter = 1;
		while (testProject.exists()) {
			countedProjectName = timestampedProjectName + "--" + counter++;
			testProject = TestUtil.getProjectByName(countedProjectName);
		}
		return countedProjectName;
	}

	private static IProject initializeProject(IProject testProject) throws CoreException {
		// copied from:
		// https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Creating_Eclipse_Java_Projects_Programmatically
		testProject.create(new NullProgressMonitor());
		testProject.open(new NullProgressMonitor());
		final IProjectDescription description = testProject.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		testProject.setDescription(description, null);
		final IJavaProject javaProject = JavaCore.create(testProject);
		final IFolder binFolder = testProject.getFolder("bin");
		binFolder.create(false, true, null);
		javaProject.setOutputLocation(binFolder.getFullPath(), null);
		final List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		final IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
		if (null != vmInstall) {
			final LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
			for (final LibraryLocation element : locations) {
				entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
			}
		}
		// add libs to project class path
		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
		final IFolder sourceFolder = testProject.getFolder("src");
		sourceFolder.create(false, true, null);
		final IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(sourceFolder);
		final IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
		final IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
		java.lang.System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
		newEntries[oldEntries.length] = JavaCore.newSourceEntry(root.getPath());
		javaProject.setRawClasspath(newEntries, null);

		return testProject;
	}

	/**
	 * Creates a VSUM in the given folder, with the given {@link AbstractVitruvDomain}s and
	 * {@link ChangePropagationSpecification}s.
	 * 
	 * @param virtualModelFolder
	 *            - folder of the VSUM
	 * @param addTimestampAndMakeNameUnique
	 *            - specifies if a timestamp shall be added to the name and if
	 *            the name shall be made unique so that is does not conflict
	 *            with an existing project
	 * @param metamodels
	 *            - {@link AbstractVitruvDomain}s to add to the VSUM
	 * @param changePropagationSpecifications
	 *            - {@link ChangePropagationSpecification}s to add to the VSUM
	 * @return the created {@link VirtualModel}
	 */
	public static InternalVirtualModel createVirtualModel(final File virtualModelFolder, boolean addTimestampAndMakeNameUnique,
			final Iterable<VitruvDomain> metamodels,
			final Iterable<ChangePropagationSpecification> changePropagationSpecifications,
			final UserInteracting userInteracting) {
		File projectFolder = virtualModelFolder;
		if (addTimestampAndMakeNameUnique) {
			projectFolder = addTimestampToProjectNameAndMakeUnique(projectFolder);
		}
		
		VirtualModelConfiguration vmodelConfig = new VirtualModelConfiguration();
		for (VitruvDomain metamodel : metamodels) {
			vmodelConfig.addMetamodel(metamodel);
		}
		for (ChangePropagationSpecification changePropagationSpecification : changePropagationSpecifications) {
			vmodelConfig.addChangePropagationSpecification(changePropagationSpecification);
		}
		final InternalVirtualModel vmodel = new VirtualModelImpl(projectFolder, userInteracting, vmodelConfig);
		return vmodel;
	}
	
	/**
	 * Creates a VSUM with the given name in a temporary folder, {@link AbstractVitruvDomain}s and
	 * {@link ChangePropagationSpecification}s.
	 * 
	 * @param virtualModelName
	 *            - name of the VSUM folder in the temp files directory
	 * @param addTimestampAndMakeNameUnique
	 *            - specifies if a timestamp shall be added to the name and if
	 *            the name shall be made unique so that is does not conflict
	 *            with an existing project
	 * @param metamodels
	 *            - {@link AbstractVitruvDomain}s to add to the VSUM
	 * @param changePropagationSpecifications
	 *            - {@link ChangePropagationSpecification}s to add to the VSUM
	 * @return the created {@link VirtualModel}
	 */
	public static InternalVirtualModel createVirtualModel(final String virtualModelName, boolean addTimestampAndMakeNameUnique,
			final Iterable<VitruvDomain> metamodels,
			final Iterable<ChangePropagationSpecification> changePropagationSpecifications,
			final UserInteracting userInteracting) {
		File testWorkspace = createTestWorkspace();
		return createVirtualModel(new File(testWorkspace, virtualModelName), 
				addTimestampAndMakeNameUnique, metamodels, changePropagationSpecifications, userInteracting);
	}
	
	public static File createTestWorkspace() {
		String testWorkspacePath = System.getProperty(VM_ARGUMENT_TEST_WORKSPACE_PATH);
		File testWorkspace = null;
		if (testWorkspacePath == null) {
			try {
				testWorkspace = Files.createTempDirectory("vitruv-test-workspace").toFile();
				testWorkspace.deleteOnExit();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			testWorkspace = new File(testWorkspacePath);
		}
		if (!testWorkspace.exists()) {
			testWorkspace.mkdir();	
		}
		return testWorkspace;
	}
	
	public static File createProjectFolder(String projectName, boolean addTimestampAndMakeNameUnique) {
		return createProjectFolder(createTestWorkspace(), projectName, addTimestampAndMakeNameUnique);
	}
	
	public static File createProjectFolder(File workspace, String projectName, boolean addTimestampAndMakeNameUniqueAndAddIdentifierFile) {
		File projectFolder = new File(workspace, projectName);
		if (addTimestampAndMakeNameUniqueAndAddIdentifierFile) {
			projectFolder = addTimestampToProjectNameAndMakeUnique(projectFolder);
		}
		projectFolder.mkdir();
		File identifierFile = new File(projectFolder, VitruviusConstants.getTestProjectMarkerFileName());
		try {
			identifierFile.createNewFile();
		} catch (IOException e) {
			throw new IllegalStateException("Identifier file " + identifierFile.toString() + " could not be created.");
		}
		return projectFolder;
	}

	/**
	 * Creates and returns a {@link VitruvDomain}.
	 *
	 * @param metamodelRootPackage
	 *            - the root {@link EPackage} of the {@link VitruvDomain} to
	 *            create
	 * @param fileExt
	 *            - fileExtension for which the {@link VitruvDomain} is
	 *            responsible
	 * @return the create {@link VitruvDomain}
	 */
	public static VitruvDomain createVitruvDomain(final String name, final EPackage metamodelRootPackage,
			final String fileExt) {
		final VitruvDomain domain = new AbstractVitruvDomain(name, metamodelRootPackage,
				new AttributeTuidCalculatorAndResolver(metamodelRootPackage.getNsURI()), fileExt) {
			@Override
			public VitruviusProjectBuilderApplicator getBuilderApplicator() {
				return null;
			}
		};
		return domain;
	}

	private static String addTimestampToString(final String originalString) {
		final String timestamp = new Date(System.currentTimeMillis()).toString().replace(" ", "_").replace(":", "_");
		final String stringWithTimeStamp = originalString + "_" + timestamp;
		return stringWithTimeStamp;
	}

	/**
	 * Initializes console logger for tests. Sets the logger level to
	 * {@link Level#ERROR} by default. If the VM property <i>logOutputLevel</i>
	 * is specified, it is used to determine the logger level.
	 */
	public static void initializeLogger() {
		Logger.getRootLogger().setLevel(Level.ERROR);
		Logger.getRootLogger().removeAllAppenders();
		Logger.getRootLogger()
				.addAppender(new ConsoleAppender(new PatternLayout("[%-5p] %d{HH:mm:ss,SSS} %-30C{1} - %m%n")));
		String outputLevelProperty = System.getProperty(VM_ARGUMENT_LOG_OUTPUT_LEVEL);
		if (outputLevelProperty != null) {
			if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
				Logger.getRootLogger().addAppender(new ConsoleAppender());
			}
			Logger.getRootLogger().setLevel(Level.toLevel(outputLevelProperty));
		} else {
			Logger.getRootLogger().setLevel(Level.ERROR);
		}
	}

	public static IProject getProjectByName(final String projectName) {
		final IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return iProject;
	}

}
