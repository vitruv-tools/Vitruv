package tools.vitruv.framework.tests;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.After;
import org.junit.Before;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.util.bridges.EMFBridge;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Basic test class for all Vitruvius application tests that require a test
 * project and a VSUM project within the test workspace. The class creates a
 * test project and a VSUM for each test case within the workspace of the
 * Eclipse test instance. It provides several methods for handling models and
 * their resources.
 *
 * @author langhamm
 * @author Heiko Klare
 *
 */
public abstract class VitruviusUnmonitoredApplicationTest extends VitruviusTest {

	private ResourceSet resourceSet;
	private TestUserInteractor testUserInteractor;
	private InternalVirtualModel virtualModel;
	private CorrespondenceModel correspondenceModel;

	protected abstract Iterable<ChangePropagationSpecification> createChangePropagationSpecifications();

	protected abstract Iterable<VitruvDomain> getVitruvDomains();

	@After
	public abstract void afterTest();

	@Before
	public void beforeTest() {
		super.beforeTest();
		this.resourceSet = new ResourceSetImpl();
		String testMethodName = testName.getMethodName();
		createVirtualModel(testMethodName);
	}

	private void createVirtualModel(final String testName) {
		String currentTestProjectVsumName = testName + "_vsum_";
		Iterable<VitruvDomain> domains = this.getVitruvDomains();
		this.virtualModel = TestUtil.createVirtualModel(currentTestProjectVsumName, true, domains,
				createChangePropagationSpecifications());
		this.correspondenceModel = virtualModel.getCorrespondenceModel();
		this.testUserInteractor = new TestUserInteractor();
		this.getVirtualModel().setUserInteractor(testUserInteractor);
	}

	protected CorrespondenceModel getCorrespondenceModel() {
		return correspondenceModel;
	}

	protected InternalVirtualModel getVirtualModel() {
		return virtualModel;
	}

	protected TestUserInteractor getUserInteractor() {
		return testUserInteractor;
	}

	private String getPlatformModelPath(final String modelPathWithinProject) {
		return this.getCurrentTestProject().getName() + "/" + modelPathWithinProject;
	}

	private VURI getModelVuri(String modelPathWithinProject) {
		return VURI.getInstance(getPlatformModelPath(modelPathWithinProject));
	}

	/**
	 * Creates and returns a resource with the given path relative to the
	 * project folder.
	 * 
	 * @param modelPathWithinProject
	 *            - the path to the resource within the project folder,
	 *            including the model file extension
	 * @return the created resource or <code>null</code> if not factory is
	 *         registered for resource with the given file extension
	 */
	protected Resource createModelResource(String modelPathWithinProject) {
		return resourceSet.createResource(getModelVuri(modelPathWithinProject).getEMFUri());
	}

	private Resource getModelResource(String modelPathWithinProject, ResourceSet resourceSet) {
		return resourceSet.getResource(getModelVuri(modelPathWithinProject).getEMFUri(), true);
	}

	/**
	 * Loads and returns the resource with the given {@link URI}.
	 * 
	 * @param modelUri
	 *            - The {@link URI} of the resource to load
	 * @return the resource loaded from the given {@link URI} or
	 *         <code>null</code> if it could not be loaded
	 */
	protected Resource getModelResource(URI modelUri) {
		return resourceSet.getResource(modelUri, true);
	}

	/**
	 * Loads and returns the resource with the given path relative to the
	 * project folder.
	 * 
	 * @param modelPathWithinProject
	 *            - the path to the resource within the project folder,
	 *            including the model file extension
	 * @return the resource loaded from the given path or <code>null</code> if
	 *         it could not be loaded
	 */
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

	/**
	 * Returns the first root element within the resource with the given path
	 * relative to the project folder
	 * 
	 * @param modelPathWithinProject
	 *            - the path to the resource within the project folder,
	 *            including the model file extension
	 * @return the root element of the resource
	 * @throws IllegalStateException
	 *             if the resource does not contain a root element
	 */
	protected EObject getFirstRootElement(String modelPathWithinProject) {
		return getFirstRootElement(modelPathWithinProject, this.resourceSet);
	}

	/**
	 * Asserts that a model with the given path relative to the project folder
	 * exists.
	 * 
	 * @param modelPathWithinProject
	 *            - the path to the resource within the project folder,
	 *            including the model file extension
	 */
	protected void assertModelExists(String modelPathWithinProject) {
		boolean modelExists = EMFBridge.existsResourceAtUri(getModelVuri(modelPathWithinProject).getEMFUri());
		assertTrue("Model at " + modelPathWithinProject + " does not exist bust should", modelExists);
	}

	/**
	 * Asserts that no model with the given path relative to the project folder
	 * exists.
	 * 
	 * @param modelPathWithinProject
	 *            - the path to the resource within the project folder,
	 *            including the model file extension
	 */
	protected void assertModelNotExists(String modelPathWithinProject) {
		boolean modelExists = EMFBridge.existsResourceAtUri(getModelVuri(modelPathWithinProject).getEMFUri());
		assertFalse("Model at " + modelPathWithinProject + " exists but should not", modelExists);
	}

	/**
	 * Asserts that the two models persisted in resources with the given paths
	 * relative to the project folder have equal contents.
	 * 
	 * @param firstModelPathWithinProject
	 *            - the path to the first resource within the project folder,
	 *            including the model file extension
	 * @param secondModelPathWithinProject
	 *            - the path to the second resource within the project folder,
	 *            including the model file extension
	 */
	protected void assertPersistedModelsEqual(String firstModelPathWithinProject, String secondModelPathWithinProject) {
		ResourceSet testResourceSet = new ResourceSetImpl();
		EObject firstRoot = getFirstRootElement(firstModelPathWithinProject, testResourceSet);
		EObject secondRoot = getFirstRootElement(secondModelPathWithinProject, testResourceSet);
		assertTrue(EcoreUtil.equals(firstRoot, secondRoot));
	}

}
