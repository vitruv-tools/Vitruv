package tools.vitruvius.framework.tests.util;

import java.net.URISyntaxException;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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

import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.metamodel.Mapping;
import tools.vitruvius.framework.metarepository.MetaRepositoryImpl;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.framework.vsum.VSUMConstants;
import tools.vitruvius.framework.vsum.VSUMImpl;
import tools.vitruvius.framework.vsum.helper.FileSystemHelper;

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

    /**
     * creates and returns a VSUM with the given meta repository
     *
     * @param metaRepository
     *            metaRepository for the VSUM
     * @return vsum
     */
    public static VSUMImpl createVSUM(final MetaRepositoryImpl metaRepository) {
        return createVSUM(metaRepository, null);
    }

    /**
     * creates and returns a VSUM with the given meta repository
     *
     * @param metaRepository
     *            metaRepository for the VSUM
     * @return vsum
     */
    public static VSUMImpl createVSUM(final MetaRepositoryImpl metaRepository, final ClassLoader classLoader) {
        final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, classLoader);
        return vsum;
    }

    /**
     * creates a metarepository and adds mappings to it.
     *
     * @param mm1URIString
     * @param fileExt1
     * @param mm2URIString
     * @param fileExt2
     * @return
     */
    public static MetaRepositoryImpl createMetaRepositoryWithMapping(final String mm1URIString, final String fileExt1,
            final String mm2URIString, final String fileExt2) {
        final MetaRepositoryImpl metaRepository = new MetaRepositoryImpl();
        addMappingToRepository(metaRepository, mm1URIString, fileExt1, mm2URIString, fileExt2);
        return metaRepository;
    }

    /**
     * Creates two Mappings. One from mm1 to mm2 and one from mm2 to mm1. Adds the mappings to the
     * given Metarepository
     *
     * @param metaRepository
     * @param mm1URIString
     * @param fileExt1
     * @param mm2URIString
     * @param fileExt2
     * @return
     */
    public static Mapping addMappingToRepository(final MetaRepositoryImpl metaRepository, final String mm1URIString,
            final String fileExt1, final String mm2URIString, final String fileExt2) {
        final VURI uri1 = VURI.getInstance(mm1URIString);
        final Metamodel mm1 = createMetamodel(mm1URIString, uri1, fileExt1);
        metaRepository.addMetamodel(mm1);

        final VURI uri2 = VURI.getInstance(mm2URIString);
        final Metamodel mm2 = createMetamodel(mm2URIString, uri2, fileExt2);
        metaRepository.addMetamodel(mm2);

        final Mapping mapping = new Mapping(mm1, mm2);
        metaRepository.addMapping(mapping);
        return mapping;
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
        final Metamodel mm = new Metamodel(nsURI, uri, fileExt);
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
     * moves the VSUM Project to a own folder with empty name to include in VSUM project folder
     */
    public static void moveVSUMProjectToOwnFolder() {
        moveVSUMProjectToOwnFolderWithTimepstamp("");
    }

    /**
     * moves the VSUM Project to a own folder
     *
     * @param addtionalName
     *            name that will be included in to VSUM project folder
     */
    public static void moveVSUMProjectToOwnFolderWithTimepstamp(final String addtionalName) {
        final IProject project = FileSystemHelper.getVSUMProject();
        moveProjectToOwnFolderWithTimestamp(addtionalName, project);
    }

    public static void moveProjectToOwnFolderWithTimestamp(final String addtionalName, final IProject project) {
        final String timestamp = getStringWithTimestamp("");
        final IPath destinationPath = new Path(
                "/" + VSUMConstants.VSUM_PROJECT_NAME + "_" + addtionalName + "_" + timestamp);
        try {
            project.open(new NullProgressMonitor());
            //project.delete(true, new NullProgressMonitor());
            project.move(destinationPath, true, new NullProgressMonitor());
        } catch (final CoreException e) {
            logger.warn("Could not move " + VSUMConstants.VSUM_PROJECT_NAME + "project to folder. " + destinationPath
                    + ". Reason: " + e);
        }
    }

    /**
     * init logger for test purposes
     */
    public static void initializeLogger() {
        if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
            Logger.getRootLogger().addAppender(new ConsoleAppender());
            Logger.getRootLogger().setLevel(Level.ALL);
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

    public static void clearMetaProject() {
        try {
            final IFolder correspondenceFolder = FileSystemHelper.getCorrespondenceFolder();
            correspondenceFolder.delete(true, new NullProgressMonitor());
            FileSystemHelper.createFolder(correspondenceFolder);
            final IFile currentInstancesFile = FileSystemHelper.getVSUMInstancesFile();
            currentInstancesFile.delete(true, new NullProgressMonitor());
        } catch (final CoreException e) {
            // soften
            throw new RuntimeException(e);
        }
    }
}
