package tools.vitruv.framework.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.modelsynchronization.ChangePropagationListener;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.TuidManager;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.vsum.InternalVirtualModel;

/**
 * Base class for all Vitruvius case study tests
 *
 * @author langhamm
 *
 */
public abstract class VitruviusCasestudyTest implements ChangePropagationListener {

	private static final boolean ADD_TIMESTAMP_TO_PROJECT_NAMES = true;
	
	protected ResourceSet resourceSet;

    protected TestUserInteractor testUserInteractor;

    protected String currentTestProjectName;

    protected IProject currentTestProject;

    private InternalVirtualModel virtualModel;
    protected Iterable<Metamodel> metamodels;
    
    protected abstract void afterTest(Description description);

    protected abstract CorrespondenceModel getCorrespondenceModel() throws Throwable;
    
    protected abstract Iterable<ChangePropagationSpecification> createChangePropagationSpecifications();
    protected abstract Iterable<Metamodel> createMetamodels();
    
    protected void beforeTest(final Description description) throws Throwable {
    	TuidManager.getInstance().reinitialize();
        createTestProject(description);
        createVirtualModel();
    }

    // ensure that MockupProject is existing
	protected void createTestProject(final Description description) throws CoreException {
		this.currentTestProjectName = TestUtil.PROJECT_URI + "_" + description.getMethodName();
		if (ADD_TIMESTAMP_TO_PROJECT_NAMES) {
			this.currentTestProjectName = TestUtil.getStringWithTimestamp(this.currentTestProjectName);
		}
        this.currentTestProject = TestUtil.getProjectByName(this.currentTestProjectName);
        if (!this.currentTestProject.exists()) {
            this.createProject(this.currentTestProject);
        }
	}
	
	private void createVirtualModel() {
		this.metamodels = this.createMetamodels();
		this.virtualModel = TestUtil.createVSUM(metamodels, createChangePropagationSpecifications());
		this.virtualModel.addChangePropagationListener(this);
	}

	protected InternalVirtualModel getVirtualModel() {
		return virtualModel;
	}
	
    @BeforeClass
    public static void setUpAllTests() {
        TestUtil.initializeLogger();
    }

    /**
     * Test watcher that moves src and model files as well as the VSUM project (which are created
     * during the previous test) to own folders and removes the PCMJavaBuilder from the project
     */
    @Rule
    public TestWatcher watchmen = new TestWatcher() {
        @Override
        protected void finished(final org.junit.runner.Description description) {
            VitruviusCasestudyTest.this.afterTest(description);
            VitruviusCasestudyTest.this.resourceSet = null;
            final String previousMethodName = description.getMethodName();
            //TestUtil.moveProjectToProjectWithTimeStamp(currentTestProjectName);
            TestUtil.moveVSUMProjectToOwnFolderWithTimepstamp(previousMethodName);
        };

        @Override
        protected void starting(final Description description) {
            try {
                VitruviusCasestudyTest.this.beforeTest(description);
            } catch (final Throwable e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        }
    };

    protected String getProjectPath() {
        return this.currentTestProjectName + "/";
    }

    protected void setUserInteractor(final UserInteracting newUserInteracting) throws Throwable {
    	this.virtualModel.setUserInteractor(newUserInteracting);
    }

    /**
     * copied from:
     * https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Creating_Eclipse_Java_Projects_Programmatically
     * :)
     *
     * @param testProject
     * @throws CoreException
     */
    private void createProject(final IProject testProject) throws CoreException {
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
    }

}
