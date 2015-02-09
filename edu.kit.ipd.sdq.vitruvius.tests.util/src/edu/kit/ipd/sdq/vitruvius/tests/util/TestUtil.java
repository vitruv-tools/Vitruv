package edu.kit.ipd.sdq.vitruvius.tests.util;

import java.net.URISyntaxException;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

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
        final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository);
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
    public static void moveSrcFilesFromMockupProjectToPath(final String destinationPathAsString) {
        moveFilesFromMockupProjectTo("src", destinationPathAsString);
    }

    /**
     * moves created model fodlder
     *
     * @param destPathWithTimestamp
     */
    public static void moveModelFilesFromProjectToPath(final String destPathWithTimestamp) {
        moveFilesFromMockupProjectTo("model", "model" + destPathWithTimestamp);
    }

    /**
     * Moves the created model and src folder files to a specific folder/path. Adds time stamp to
     * destination path string
     *
     * @param destinationPathAsStringWithoutTimestamp
     *            destination path in test workspace
     */
    public static void moveSrcFilesFromMockupProjectToPathWithTimestamp(
            final String destinationPathAsStringWithoutTimestamp) {
        final String destPathWithTimestamp = getStringWithTimestamp(destinationPathAsStringWithoutTimestamp);
        moveSrcFilesFromMockupProjectToPath(destPathWithTimestamp);
    }

    public static String getStringWithTimestamp(final String destinationPathAsStringWithoutTimestamp) {
        final String timestamp = new Date(System.currentTimeMillis()).toString().replace(" ", "_");
        final String destPathWithTimestamp = destinationPathAsStringWithoutTimestamp + "_" + timestamp;
        return destPathWithTimestamp;
    }

    public static void moveModelFilesFromMockupProjectToPathWithTimestamp(
            final String destinationPathAsStringWithoutTimeStamp) {
        final String destPathWithTimestamp = getStringWithTimestamp(destinationPathAsStringWithoutTimeStamp);
        moveModelFilesFromProjectToPath(destPathWithTimestamp);
    }

    /**
     * Moves files from one folder in the MockupProject to another one
     *
     * @param srcPath
     * @param destinationPath
     */
    public static void moveFilesFromMockupProjectTo(final String srcPath, final String destinationPath) {
        moveFilesFromTo("MockupProject", srcPath, destinationPath);
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
    public static void moveFilesFromTo(final String srcProjectName, final String srcPath, final String destinationPath) {
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
            logger.warn("Could not move src folder do destination folder " + destinationIPath + ": " + e.getMessage());
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
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        final IProject project = root.getProject(VSUMConstants.VSUM_PROJECT_NAME);
        final String timestamp = new Date(System.currentTimeMillis()).toString().replace(" ", "_");
        final IPath destinationPath = new Path("/" + VSUMConstants.VSUM_PROJECT_NAME + "_" + addtionalName + "_"
                + timestamp);
        try {
            project.open(new NullProgressMonitor());
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

    public static <T> T getFieldFromClass(final Class<?> classWithField, final String fieldName, final Object instance)
            throws Throwable {
        final java.lang.reflect.Field field = classWithField.getDeclaredField(fieldName);
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        final T t = (T) field.get(instance);
        return t;
    }

    public static IProject getTestProject() {
        final IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_URI);
        return iProject;
    }
}
