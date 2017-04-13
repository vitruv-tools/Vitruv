package tools.vitruv.framework.tests;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.After;
import org.junit.Before;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.metamodel.Metamodel;
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

	protected abstract Iterable<Metamodel> createMetamodels();

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
		Iterable<Metamodel> metamodels = this.createMetamodels();
		this.virtualModel = TestUtil.createVirtualModel(currentTestProjectVsumName, metamodels,
				createChangePropagationSpecifications());
		// TODO HK Implement correctly: Should be obsolete when correspondence
		// model is not MM-pair-specific any more
		Iterator<Metamodel> it = metamodels.iterator();
		Metamodel firstMetamodel = it.next();
		Metamodel secondMetamodel = it.hasNext() ? it.next() : firstMetamodel; 
		this.correspondenceModel = virtualModel.getCorrespondenceModel(firstMetamodel.getURI(), secondMetamodel.getURI());
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
		assertTrue("Model at " + modelPathWithinProject + " does not exist bust should", modelExists);
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
