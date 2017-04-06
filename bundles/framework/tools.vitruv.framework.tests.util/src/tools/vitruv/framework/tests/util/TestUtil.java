package tools.vitruv.framework.tests.util;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.framework.vsum.VirtualModelConfiguration;
import tools.vitruv.framework.vsum.VirtualModelImpl;
import tools.vitruv.framework.vsum.helper.FileSystemHelper;

/**
 * Utility class for all Vitruvius test cases
 *
 * @author Langhamm
 *
 */
public final class TestUtil {

    private static final Logger logger = Logger.getLogger(TestUtil.class.getSimpleName());
    public static final String PROJECT_URI = "MockupProject";
    public static final int WAITING_TIME_FOR_SYNCHRONIZATION = 1 * 1000;
    public static final String SOURCE_FOLDER = "src";
    public static final String ORIGINAL_FILE_PREFIX = "ORIGINAL_";

    /**
     * Utility classes should not have a public constructor
     */
    private TestUtil() {
    }

    public static InternalVirtualModel createVSUM(final String vsumName, final Iterable<Metamodel> metamodels) {
        return createVSUM(vsumName, metamodels, new ArrayList<ChangePropagationSpecification>(), null);
    }
    
    /**
     * creates and returns a VSUM with the given meta repository
     *
     * @param metaRepository
     *            metaRepository for the VSUM
     * @return vsum
     */
    public static InternalVirtualModel createVSUM(final String vsumName, final Iterable<Metamodel> metamodels, final Iterable<ChangePropagationSpecification> changePropagationSpecifications) {
        return createVSUM(vsumName, metamodels, changePropagationSpecifications, null);
    }

    /**
     * creates and returns a VSUM with the given meta repository
     *
     * @param metaRepository
     *            metaRepository for the VSUM
     * @return vsum
     */
    public static InternalVirtualModel createVSUM(final String vsumName, final Iterable<Metamodel> metamodels, final Iterable<ChangePropagationSpecification> changePropagationSpecifications, final ClassLoader classLoader) {
        VirtualModelConfiguration vmodelConfig = new VirtualModelConfiguration();
        for (Metamodel metamodel : metamodels) {
        	vmodelConfig.addMetamodel(metamodel);
        }
        for (ChangePropagationSpecification changePropagationSpecification : changePropagationSpecifications) {
        	vmodelConfig.addChangePropagationSpecification(changePropagationSpecification);
        }
        // TODO HK Replace name with parameter
    	final InternalVirtualModel vmodel = new VirtualModelImpl(vsumName, vmodelConfig, classLoader);
        return vmodel;
    }

    /**
     * Creates and returns a Metamodel
     *
     * @param nsURI
     *            namespaceURI of the metamodel
     * @param uri
     *            the actual URI for the metamodel
     * @param fileExt
     *            fileExtension for which the metamodel is repsonsible
     * @return
     */
    public static Metamodel createMetamodel(final String nsURI, final VURI uri, final String fileExt) {
        final Metamodel mm = new Metamodel(uri, nsURI, new AttributeTUIDCalculatorAndResolver(nsURI), fileExt);
        return mm;
    }

    /**
     * Moves the created model and src folder files to a specific folder/path.
     *
     * @param destinationPathAsString
     *            destinationPath in test workspace
     */
    public static void moveSrcFilesFromMockupProjectToPath(final String sourcePathAsString, final String destinationPathAsString) {
        moveFilesFromMockupProjectTo("src", sourcePathAsString, destinationPathAsString);
    }

    /**
     * moves created model fodlder
     *
     * @param destPathWithTimestamp
     */
    public static void moveModelFilesFromProjectToPath(final String sourcePath, final String destPathWithTimestamp) {
        moveFilesFromMockupProjectTo("model", sourcePath, "model" + destPathWithTimestamp);
    }

    /**
     * Moves the created model and src folder files to a specific folder/path. Adds time stamp to
     * destination path string
     *
     * @param destinationPathAsStringWithoutTimestamp
     *            destination path in test workspace
     */
    public static void moveSrcFilesFromMockupProjectToPathWithTimestamp(
            final String mockupProjectName) {
        final String destPathWithTimestamp = getStringWithTimestamp(mockupProjectName);
        moveSrcFilesFromMockupProjectToPath(mockupProjectName, destPathWithTimestamp);
    }

    public static String getStringWithTimestamp(final String destinationPathAsStringWithoutTimestamp) {
        final String timestamp = new Date(System.currentTimeMillis()).toString().replace(" ", "_").replace(":", "_");
        final String destPathWithTimestamp = destinationPathAsStringWithoutTimestamp + "_" + timestamp;
        return destPathWithTimestamp;
    }

    public static void moveModelFilesFromMockupProjectToPathWithTimestamp(
            final String destinationPathAsStringWithoutTimeStamp) {
        final String destPathWithTimestamp = getStringWithTimestamp(destinationPathAsStringWithoutTimeStamp);
        moveModelFilesFromProjectToPath(destinationPathAsStringWithoutTimeStamp, destPathWithTimestamp);
    }

    /**
     * Moves files from one folder in the MockupProject to another one
     *
     * @param srcPath
     * @param destinationPath
     */
    public static void moveFilesFromMockupProjectTo(final String srcFolder, final String sourcePath, final String destinationPath) {
        moveFilesFromTo(sourcePath, srcFolder, destinationPath);
    }

    /**
     * Moves files from one folder in the MockupProject to another one
     *
     * @param srcProjectName
     *            Name of the srcProject
     * @param srcPath
     *            srcPath in workspace
     * @param destinationPath
     *            destinationPath in test workspace
     * @throws URISyntaxException
     */
    public static void moveFilesFromTo(final String srcProjectName, final String srcPath,
            final String destinationPath) {
        // IResource iResource = Wor
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        final IProject project = root.getProject(srcProjectName);
        final IResource member = project.findMember(srcPath);
        if (null == member) {
            logger.warn("Member ('" + srcPath + "') not found. Nothing to do in ‘moveCreatedFilesToPath‘");
            return;
        }
        final IPath destinationIPath = new Path(destinationPath);
        try {
            member.move(destinationIPath, true, new NullProgressMonitor());
        } catch (final CoreException e) {
            logger.warn("Could not move src folder to destination folder " + destinationIPath + ": " + e.getMessage());
        }
    }
    
    public static void moveProjectToProjectWithTimeStamp(final String projectName) {
    	final String destPathWithTimestamp = getStringWithTimestamp(projectName);
        renameProject(projectName, destPathWithTimestamp);
    }
    
    private static void renameProject(final String sourceProjectName, final String destinationProjectName) {
    	final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        final IProject project = root.getProject(sourceProjectName);
    	try {
        	IProjectDescription descr = project.getDescription();
            descr.setName(destinationProjectName);
        	project.move(descr, true, new NullProgressMonitor());
        } catch (final CoreException e) {
            logger.warn("Could not rename project " + sourceProjectName + " to " + destinationProjectName + ": " + e.getMessage());
        }
    }

    /**
     * init logger for test purposes
     */
    public static void initializeLogger() {
    	Logger.getRootLogger().setLevel(Level.WARN);
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger()
                .addAppender(new ConsoleAppender(new PatternLayout("[%-5p] %d{HH:mm:ss,SSS} %-30C{1} - %m%n")));
    	String outputLevelProperty = System.getProperty("logOutputLevel");
    	if (outputLevelProperty != null) {
    		if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
                Logger.getRootLogger().addAppender(new ConsoleAppender());
            }
            Logger.getRootLogger().setLevel(Level.toLevel(outputLevelProperty));
    	} else {
    		Logger.getRootLogger().setLevel(Level.WARN);
    	}
    }

    public static void waitForSynchronization(final int waitingTimeForSynchronization) {
        try {
            Thread.sleep(waitingTimeForSynchronization);
        } catch (final InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void waitForSynchronization() {
        TestUtil.waitForSynchronization(WAITING_TIME_FOR_SYNCHRONIZATION);
    }

    public static IProject getTestProject() {
        return getProjectByName(PROJECT_URI);
    }

    public static IProject getProjectByName(final String projectName) {
        final IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        return iProject;
    }

    public static void deleteAllProjectFolderCopies(final String originalProjectName) {
        final IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects(0);
        for (final IProject project : allProjects) {
            final boolean copyOfOriginalProject = isCopyWithEqualPrefix(originalProjectName, project);
            // boolean copyOfMetaProject = isCopyWithEqualPrefix(VSUMConstants.VSUM_PROJECT_NAME,
            // project);
            if (copyOfOriginalProject) {// || copyOfMetaProject) {
                try {
                    project.delete(true, new NullProgressMonitor());
                } catch (final CoreException e) {
                    // soften
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static boolean isCopyWithEqualPrefix(final String originalProjectName, final IProject project) {
        final String currentProjectName = project.getName();
        final boolean samePrefix = currentProjectName.startsWith(originalProjectName);
        final boolean copyOfOriginalProject = samePrefix && !currentProjectName.equals(originalProjectName);
        return copyOfOriginalProject;
    }

    public static String copyProjectFolder(final String projectFolderName) {
        final String timestamp = "" + System.currentTimeMillis();
        try {
            final IProject originalProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectFolderName);
            return copyProjectWithSuffix(originalProject, timestamp);
            // IProject metaProject = FileSystemHelper.getVSUMProject();
            // copyProjectWithSuffix(metaProject, timestamp);
        } catch (final CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }

    private static String copyProjectWithSuffix(final IProject originalProject, final String suffix)
            throws CoreException {
        final IPath originalPath = originalProject.getFullPath();
        final String lastSegment = originalPath.lastSegment();
        String projectCopyName = lastSegment + suffix;
        IProject project = FileSystemHelper.getProject(projectCopyName);
        int count = 0;
        while (project.exists()) {
            count++;
            project = FileSystemHelper.getProject(projectCopyName + count);
        }
        projectCopyName += count;
        final IPath copyPath = originalPath.removeLastSegments(1).append(projectCopyName);
        originalProject.copy(copyPath, true, new NullProgressMonitor());
        return projectCopyName;
    }
    
    public static String createProjectFolderWithTimestamp(final String projectName) {
        final String timestamp = "" + System.currentTimeMillis();
        try {
            return createProjectWithSuffix(projectName, timestamp);
        } catch (final CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }
    
    private static String createProjectWithSuffix(final String projectName, final String suffix)
            throws CoreException {
        String projectCopyName = projectName + suffix;
        int count = 0;
        IProject project = FileSystemHelper.getProject(projectCopyName + count);
        while (project.exists()) {
            count++;
            project = FileSystemHelper.getProject(projectCopyName + count);
        }
        project.create(null);
        project.open(null);
        return project.getName();
    }

    public static void clearMetaProject(String vsumName) {
        try {
        	FileSystemHelper fsHelper = new FileSystemHelper(vsumName);
            final IFolder correspondenceFolder = fsHelper.getCorrespondenceFolder();
            correspondenceFolder.delete(true, new NullProgressMonitor());
            FileSystemHelper.createFolder(correspondenceFolder);
            final IFile currentInstancesFile = fsHelper.getVSUMInstancesFile();
            currentInstancesFile.delete(true, new NullProgressMonitor());
        } catch (final CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }
}
