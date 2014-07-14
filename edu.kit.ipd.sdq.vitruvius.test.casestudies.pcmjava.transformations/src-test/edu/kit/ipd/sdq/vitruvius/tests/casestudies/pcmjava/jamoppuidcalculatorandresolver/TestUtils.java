package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.jamoppuidcalculatorandresolver;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.resource.java.mopp.JavaResourceFactory;

import de.uka.ipd.sdq.pcm.PcmPackage;
import de.uka.ipd.sdq.pcm.util.PcmResourceFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;

public abstract class TestUtils {

    private static Logger logger = Logger.getLogger(TestUtils.class.getSimpleName());

    // private constructor for Util class
    private TestUtils() {
    }

    /**
     * Static initialization of Log4J
     */
    static {
        BasicConfigurator.configure(new ConsoleAppender(new SimpleLayout()));
    }

    /**
     * Register JaMoPP, PCM and Correspondence packages to use these models in a non-Plugin test.
     */
    public static void registerMetamodels() {
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();

        // register JaMoPP package and factory globally
        EPackage.Registry.INSTANCE.put(JavaPackage.eNS_URI, JavaPackage.eINSTANCE);
        m.put("java", new JavaResourceFactory());
        // register PCM
        EPackage.Registry.INSTANCE.put(PcmPackage.eNS_URI, PcmPackage.eINSTANCE);
        m.put("repository", new PcmResourceFactoryImpl());
        // register correspondence model for xmi files
        EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, CorrespondencePackage.eINSTANCE);
        m.put("xmi", new XMIResourceFactoryImpl());
    }

    /**
     * Moves the created model and src folder files to a specific folder/path.
     *
     * @param destinationPathAsString
     *            destinationPath in test workspace
     */
    public static void moveSrcFilesToPath(final String destinationPathAsString) {
        moveFilesFromTo("src", destinationPathAsString);
    }

    /**
     * Moves the created model and src folder files to a specific folder/path. Adds timestamp to
     * destination path string
     *
     * @param destinationPathAsStringWithoutTimestamp
     *            destination path in test workspace
     */
    public static void moveSrcFilesToPathWithTimestamp(final String destinationPathAsStringWithoutTimestamp) {
        final String timestamp = new Date(System.currentTimeMillis()).toString().replace(" ", "_");
        final String destPathWithTimestamp = destinationPathAsStringWithoutTimestamp + "_" + timestamp;
        moveSrcFilesToPath(destPathWithTimestamp);
    }

    /**
     * Moves files from one folder in the MockupProject to another one
     *
     * @param destinationPath
     *            destinationPath in test workspace
     * @throws URISyntaxException
     */
    public static void moveFilesFromTo(final String srcPath, final String destinationPath) {
        // IResource iResource = Wor
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        final IProject project = root.getProject("MockupProject");
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

    public static void moveVSUMProjectToOwnFolder() {
        moveVSUMProjectToOwnFolder("");
    }

    public static void moveVSUMProjectToOwnFolder(final String addtionalName) {
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
}
