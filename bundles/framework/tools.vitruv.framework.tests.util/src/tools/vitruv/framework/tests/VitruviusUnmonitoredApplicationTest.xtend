package tools.vitruv.framework.tests

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.After
import org.junit.Before
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel

import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

/**
 * Basic test class for all Vitruvius application tests that require a test
 * project and a VSUM project within the test workspace. The class creates a
 * test project and a VSUM for each test case within the workspace of the
 * Eclipse test instance. It provides several methods for handling models and
 * their resources.
 * @author langhamm
 * @author Heiko Klare
 */
abstract class VitruviusUnmonitoredApplicationTest extends VitruviusTest {
	static extension VModelExtention = VModelExtention::instance
	ResourceSet resourceSet
	@Accessors(PROTECTED_GETTER)
	TestUserInteractor testUserInteractor
	@Accessors(PROTECTED_GETTER)
	InternalVirtualModel virtualModel
	@Accessors(PROTECTED_GETTER)
	CorrespondenceModel correspondenceModel

	def protected abstract Iterable<ChangePropagationSpecification> createChangePropagationSpecifications()

	def protected abstract Iterable<VitruvDomain> getVitruvDomains()

	@After
	def abstract void afterTest()

	@Before
	override void beforeTest() {
		super.beforeTest
		resourceSet = new ResourceSetImpl
		val testMethodName = testName.getMethodName
		createVirtualModel(testMethodName)
	}

	def private createVirtualModel(String testName) {
		val currentTestProjectVsumName = '''«testName»_vsum_'''
		val domains = vitruvDomains
		virtualModel = TestUtil::createVirtualModel(currentTestProjectVsumName, true, domains,
			createChangePropagationSpecifications)
		correspondenceModel = virtualModel.getCorrespondenceModel
		testUserInteractor = new TestUserInteractor
		virtualModel.userInteractor = testUserInteractor
	}

	def String getPlatformModelPath(String modelPathWithinProject) {
		currentTestProject.getPlatformModelPath(modelPathWithinProject)
	}

	def VURI getModelVuri(String modelPathWithinProject) {
		currentTestProject.getModelVuri(modelPathWithinProject)
	}

	/**
	 * Creates and returns a resource with the given path relative to the
	 * project folder.
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * including the model file extension
	 * @return the created resource or <code>null</code> if not factory is
	 * registered for resource with the given file extension
	 */
	def protected Resource createModelResource(String modelPathWithinProject) {
		currentTestProject.createModelResource(resourceSet, modelPathWithinProject)
	}

	def Resource getModelResource(String modelPathWithinProject, ResourceSet resourceSet) {
		currentTestProject.getModelResource(modelPathWithinProject, resourceSet)
	}

	/**
	 * Loads and returns the resource with the given {@link URI}.
	 * @param modelUri- The {@link URI} of the resource to load
	 * @return the resource loaded from the given {@link URI} or
	 * <code>null</code> if it could not be loaded
	 */
	def protected Resource getModelResource(URI modelUri) {
		resourceSet.getModelResource(modelUri)
	}

	/**
	 * Loads and returns the resource with the given path relative to the
	 * project folder.
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * including the model file extension
	 * @return the resource loaded from the given path or <code>null</code> if
	 * it could not be loaded
	 */
	def protected Resource getModelResource(String modelPathWithinProject) {
		currentTestProject.getModelResource(resourceSet, modelPathWithinProject)
	}

	def EObject getFirstRootElement(String modelPathWithinProject, ResourceSet resourceSet) {
		currentTestProject.getFirstRootElement(modelPathWithinProject, resourceSet)
	}

	/**
	 * Returns the first root element within the resource with the given path
	 * relative to the project folder
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * including the model file extension
	 * @return the root element of the resource
	 * @throws IllegalStateExceptionif the resource does not contain a root element
	 */
	def protected EObject getFirstRootElement(String modelPathWithinProject) {
		currentTestProject.getFirstRootElement(modelPathWithinProject, resourceSet)
	}

	/**
	 * Asserts that a model with the given path relative to the project folder
	 * exists.
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * including the model file extension
	 */
	def protected void assertModelExists(String modelPathWithinProject) {
		val modelExists = EMFBridge::existsResourceAtUri(getModelVuri(modelPathWithinProject).getEMFUri)
		assertTrue('''Model at «modelPathWithinProject» does not exist bust should''', modelExists)
	}

	/**
	 * Asserts that no model with the given path relative to the project folder
	 * exists.
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * including the model file extension
	 */
	def protected void assertModelNotExists(String modelPathWithinProject) {
		val modelExists = EMFBridge::existsResourceAtUri(getModelVuri(modelPathWithinProject).getEMFUri)
		assertFalse('''Model at «modelPathWithinProject» exists but should not''', modelExists)
	}

	/**
	 * Asserts that the two models persisted in resources with the given paths
	 * relative to the project folder have equal contents.
	 * @param firstModelPathWithinProject- the path to the first resource within the project folder,
	 * including the model file extension
	 * @param secondModelPathWithinProject- the path to the second resource within the project folder,
	 * including the model file extension
	 */
	protected def void assertPersistedModelsEqual(String firstModelPathWithinProject,
		String secondModelPathWithinProject) {
		val testResourceSet = new ResourceSetImpl
		val firstRoot = getFirstRootElement(firstModelPathWithinProject, testResourceSet)
		val secondRoot = getFirstRootElement(secondModelPathWithinProject, testResourceSet)
		assertTrue(EcoreUtil::equals(firstRoot, secondRoot))
	}
}
