package tools.vitruv.framework.tests.util;

import java.util.ArrayList;
import java.util.Collections;
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
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.framework.vsum.VirtualModelConfiguration;
import tools.vitruv.framework.vsum.VirtualModelImpl;

/**
 * Utility class for all Vitruvius test cases
 *
 * @author Langhamm
 *
 */
public final class TestUtil {

    public static final String PROJECT_URI = "MockupProject";
    public static final int WAITING_TIME_FOR_SYNCHRONIZATION = 1 * 1000;
    public static final String SOURCE_FOLDER = "src";

    /**
     * Utility classes should not have a public constructor
     */
    private TestUtil() {
    }
    
	public static String getTempDirPath() {
		String path = System.getProperty("java.io.tmpdir").replace("\\", "/");
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		if (!path.endsWith("/")) {
			path = path + "/";
		}
		return path;
	}
	
	public static IProject createProject(String projectName, boolean addTimestamp) throws CoreException {
		String finalProjectName = projectName;
		if (addTimestamp) {
			finalProjectName = getStringWithTimestamp(projectName);
		}
		IProject testProject = TestUtil.getProjectByName(finalProjectName);
		
		// If project exists, add an index
		int counter = 1;
		while (testProject.exists()) {
			testProject = TestUtil.getProjectByName(finalProjectName + "--" + counter++);
		}
		
		// copied from: https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Creating_Eclipse_Java_Projects_Programmatically
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
	
	
    public static InternalVirtualModel createVirtualModel(final String vsumName, final Iterable<Metamodel> metamodels) {
        return createVirtualModel(vsumName, false, metamodels, Collections.emptyList());
    }
    
    public static InternalVirtualModel createVirtualModel(final String vsumName, boolean addTimestamp, final Iterable<Metamodel> metamodels) {
        return createVirtualModel(vsumName, addTimestamp, metamodels, Collections.emptyList());
    }
    
    public static InternalVirtualModel createVirtualModel(final String vsumName, boolean addTimestamp, 
    		final Iterable<Metamodel> metamodels, final Iterable<ChangePropagationSpecification> changePropagationSpecifications) {
    	String finalVsumName = vsumName;
    	if (addTimestamp) {
    		finalVsumName = getStringWithTimestamp(finalVsumName);
    	}
        return createVirtualModel(finalVsumName, metamodels, changePropagationSpecifications);
    }

    public static InternalVirtualModel createVirtualModel(final String vsumName, final Iterable<Metamodel> metamodels, final Iterable<ChangePropagationSpecification> changePropagationSpecifications) {
        VirtualModelConfiguration vmodelConfig = new VirtualModelConfiguration();
        for (Metamodel metamodel : metamodels) {
        	vmodelConfig.addMetamodel(metamodel);
        }
        for (ChangePropagationSpecification changePropagationSpecification : changePropagationSpecifications) {
        	vmodelConfig.addChangePropagationSpecification(changePropagationSpecification);
        }
    	final InternalVirtualModel vmodel = new VirtualModelImpl(vsumName, vmodelConfig);
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
        final Metamodel mm = new Metamodel(uri, nsURI, new AttributeTuidCalculatorAndResolver(nsURI), fileExt);
        return mm;
    }

    public static String getStringWithTimestamp(final String destinationPathAsStringWithoutTimestamp) {
        final String timestamp = new Date(System.currentTimeMillis()).toString().replace(" ", "_").replace(":", "_");
        final String destPathWithTimestamp = destinationPathAsStringWithoutTimestamp + "_" + timestamp;
        return destPathWithTimestamp;
    }

    /**
     * init logger for test purposes
     */
    public static void initializeLogger() {
    	Logger.getRootLogger().setLevel(Level.ERROR);
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
    		Logger.getRootLogger().setLevel(Level.ERROR);
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

    public static IProject getProjectByName(final String projectName) {
        final IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        return iProject;
    }

}
