package tools.vitruv.framework.tests;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.tuid.TuidManager;
import tools.vitruv.framework.util.bridges.EMFBridge;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Base class for all Vitruvius application tests
 *
 * @author langhamm
 * @author Heiko Klare
 *
 */
public abstract class VitruviusApplicationTest {
	private static final boolean ADD_TIMESTAMP_TO_PROJECT_NAMES = true;

	@Rule
	public TestName testName = new TestName();

	private ResourceSet resourceSet;
	private TestUserInteractor testUserInteractor;
	private IProject currentTestProject;
	private InternalVirtualModel virtualModel;
	private Iterable<Metamodel> metamodels;

	protected abstract Iterable<ChangePropagationSpecification> createChangePropagationSpecifications();
	protected abstract Iterable<Metamodel> createMetamodels();

	@BeforeClass
	public static void setUpAllTests() {
		TestUtil.initializeLogger();
	}

	@After
	public abstract void afterTest();

	@Before
	public void beforeTest() {
		TuidManager.getInstance().reinitialize();
		this.resourceSet = new ResourceSetImpl();
		String testMethodName = testName.getMethodName();
		this.currentTestProject = initializeTestProject(testMethodName);
		createVirtualModel(testMethodName);
	}
	
	protected IProject initializeTestProject(final String testName) {
		String testProjectName = TestUtil.PROJECT_URI + "_" + testName;
		IProject testProject = null;
		try {
			testProject = TestUtil.createProject(testProjectName, ADD_TIMESTAMP_TO_PROJECT_NAMES); 
		} catch (CoreException e) {
			fail("Exception during creation of test project");
		}
		return testProject;
	}
	
	private void createVirtualModel(final String testName) {
		String currentTestProjectVsumName = TestUtil.PROJECT_URI + "_" + testName + "_vsum_";
		this.metamodels = this.createMetamodels();
		this.virtualModel = TestUtil.createVirtualModel(currentTestProjectVsumName, ADD_TIMESTAMP_TO_PROJECT_NAMES,
				metamodels,	createChangePropagationSpecifications());
		this.testUserInteractor = new TestUserInteractor();
		this.getVirtualModel().setUserInteractor(testUserInteractor);
	}
	

	protected CorrespondenceModel getCorrespondenceModel() throws Throwable {
		// TODO HK Implement correctly: Should be obsolete when correspondence
		// model is not MM-pair-specific any more
		Iterator<Metamodel> it = metamodels.iterator();
		return this.getVirtualModel().getCorrespondenceModel(it.next().getURI(), it.next().getURI());
	}

	protected InternalVirtualModel getVirtualModel() {
		return virtualModel;
	}
	
	protected IProject getCurrentTestProject() {
		return currentTestProject;
	}
	
	protected TestUserInteractor getUserInteractor() {
		return testUserInteractor;
	}

	private String getPlatformModelPath(final String modelPathWithinProject) {
		return this.currentTestProject.getName() + "/" + modelPathWithinProject;
	}

	private VURI getModelVuri(String modelPathWithinProject) {
		return VURI.getInstance(getPlatformModelPath(modelPathWithinProject));
	}

	protected Resource createModelResource(String modelPathWithinProject) {
		return resourceSet.createResource(getModelVuri(modelPathWithinProject).getEMFUri());
	}
	
	private Resource getModelResource(String modelPathWithinProject, ResourceSet resourceSet) {
		return resourceSet.getResource(getModelVuri(modelPathWithinProject).getEMFUri(), true);
	}
	
	protected Resource getModelResource(String modelPathWithinProject) {
		return getModelResource(modelPathWithinProject, this.resourceSet);
	}

	private EObject getFirstRootElement(String modelPathWithinProject, ResourceSet resourceSet) {
		List<EObject> resourceContents = getModelResource(modelPathWithinProject, resourceSet).getContents();
		if (resourceContents.size() < 1) {
			throw new IllegalStateException("Model has no root");
		}
		return resourceContents.get(0);
	}

	protected EObject getFirstRootElement(String modelPathWithinProject) {
		return getFirstRootElement(modelPathWithinProject, this.resourceSet);
	}

	protected void assertModelExists(String modelPathWithinProject) {
		boolean modelExists = EMFBridge.existsResourceAtUri(getModelVuri(modelPathWithinProject).getEMFUri());
		assertTrue("Model at " + modelPathWithinProject +  " does not exist bust should", modelExists);
	}
	
	protected void assertModelNotExists(String modelPathWithinProject) {
		boolean modelExists = EMFBridge.existsResourceAtUri(getModelVuri(modelPathWithinProject).getEMFUri());
		assertFalse("Model at " + modelPathWithinProject + " exists but should not", modelExists);
	}
	
	protected void assertPersistedModelsEqual(String firstModelPathWithinProject, String secondModelPathWithinProject) {
		ResourceSet testResourceSet = new ResourceSetImpl();
		EObject firstRoot = getFirstRootElement(firstModelPathWithinProject, testResourceSet);
		EObject secondRoot = getFirstRootElement(secondModelPathWithinProject, testResourceSet);
		assertTrue(EcoreUtil.equals(firstRoot, secondRoot));
	}

}
