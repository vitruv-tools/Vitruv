package tools.vitruv.framework.tests

import java.io.File
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.junit.After
import org.junit.Before
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.util.ResourceSetUtil
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.datatypes.VURI
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.vsum.VersioningVirtualModel

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
	ResourceSet resourceSet
	@Accessors(PROTECTED_GETTER)
	TestUserInteractor userInteractor
	@Accessors(PROTECTED_GETTER)
	VersioningVirtualModel virtualModel
	@Accessors(PROTECTED_GETTER)
	CorrespondenceModel correspondenceModel

	def protected abstract Iterable<ChangePropagationSpecification> createChangePropagationSpecifications()

	def protected abstract Iterable<VitruvDomain> getVitruvDomains()

	@After
	def abstract void afterTest()

	@Before
	override void beforeTest() {
		super.beforeTest()
		this.resourceSet = new ResourceSetImpl()
		ResourceSetUtil.addExistingFactoriesToResourceSet(resourceSet)
		var String testMethodName = testName.getMethodName()
		createVirtualModel(testMethodName)
	}

	def private void createVirtualModel(String testName) {
		var String currentTestProjectVsumName = '''«testName»_vsum_'''
		var Iterable<VitruvDomain> domains = this.getVitruvDomains()
		this.userInteractor = new TestUserInteractor()
		this.virtualModel = TestUtil.createVirtualModel(currentTestProjectVsumName, true, domains,
			createChangePropagationSpecifications(), userInteractor) as VersioningVirtualModel
		this.correspondenceModel = virtualModel.getCorrespondenceModel()
	}

	def private File getPlatformModelPath(String modelPathWithinProject) {
		return new File(this.getCurrentTestProjectFolder(), modelPathWithinProject)
	}

	def protected VURI getModelVuri(String modelPathWithinProject) {
		return VURI.getInstance(EMFBridge.getEmfFileUriForFile(getPlatformModelPath(modelPathWithinProject)))
	}

	/** 
	 * Creates and returns a resource with the given path relative to the
	 * project folder.
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * using "/" as separator char and including the model file extension
	 * @return the created resource or <code>null</code> if not factory is
	 * registered for resource with the given file extension
	 */
	def protected Resource createModelResource(String modelPathWithinProject) {
		return resourceSet.createResource(getModelVuri(modelPathWithinProject).getEMFUri())
	}

	def private Resource getModelResource(String modelPathWithinProject, ResourceSet resourceSet) {
		return resourceSet.getResource(getModelVuri(modelPathWithinProject).getEMFUri(), true)
	}

	/** 
	 * Loads and returns the resource with the given {@link URI}.
	 * @param modelUri- The {@link URI} of the resource to load
	 * @return the resource loaded from the given {@link URI} or
	 * <code>null</code> if it could not be loaded
	 */
	def protected Resource getModelResource(URI modelUri) {
		return resourceSet.getResource(modelUri, true)
	}

	/** 
	 * Loads and returns the resource with the given path relative to the
	 * project folder.
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * using "/" as separator char and including the model file extension
	 * @return the resource loaded from the given path or <code>null</code> if
	 * it could not be loaded
	 */
	def protected Resource getModelResource(String modelPathWithinProject) {
		return getModelResource(modelPathWithinProject, this.resourceSet)
	}

	def protected EObject getFirstRootElement(String modelPathWithinProject, ResourceSet resourceSet) {
		var List<EObject> resourceContents = getModelResource(modelPathWithinProject, resourceSet).contents
		if (resourceContents.size < 1)
			throw new IllegalStateException("Model has no root")
		return resourceContents.get(0)
	}

	/** 
	 * Returns the first root element within the resource with the given path
	 * relative to the project folder
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * using "/" as separator char and including the model file extension
	 * @return the root element of the resource
	 * @throws IllegalStateExceptionif the resource does not contain a root element
	 */
	def protected EObject getFirstRootElement(String modelPathWithinProject) {
		return getFirstRootElement(modelPathWithinProject, this.resourceSet)
	}

	/** 
	 * Asserts that a model with the given path relative to the project folder
	 * exists.
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * using "/" as separator char and including the model file extension
	 */
	def protected void assertModelExists(String modelPathWithinProject) {
		var boolean modelExists = URIUtil.existsResourceAtUri(getModelVuri(modelPathWithinProject).getEMFUri())
		assertTrue('''Model at «modelPathWithinProject» does not exist bust should''', modelExists)
	}

	/** 
	 * Asserts that no model with the given path relative to the project folder
	 * exists.
	 * @param modelPathWithinProject- the path to the resource within the project folder,
	 * using "/" as separator char and including the model file extension
	 */
	def protected void assertModelNotExists(String modelPathWithinProject) {
		var boolean modelExists = URIUtil.existsResourceAtUri(getModelVuri(modelPathWithinProject).getEMFUri())
		assertFalse('''Model at «modelPathWithinProject» exists but should not''', modelExists)
	}

	/** 
	 * Asserts that the two models persisted in resources with the given paths
	 * relative to the project folder have equal contents.
	 * @param firstModelPathWithinProject- the path to the first resource within the project folder,
	 * using "/" as separator char and including the model file extension
	 * @param secondModelPathWithinProject- the path to the second resource within the project folder,
	 * using "/" as separator char and including the model file extension
	 */
	def protected void assertPersistedModelsEqual(String firstModelPathWithinProject,
		String secondModelPathWithinProject) {
		var ResourceSet testResourceSet = new ResourceSetImpl()
		testResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl())
		ResourceSetUtil.addExistingFactoriesToResourceSet(testResourceSet)
		var EObject firstRoot = getFirstRootElement(firstModelPathWithinProject, testResourceSet)
		var EObject secondRoot = getFirstRootElement(secondModelPathWithinProject, testResourceSet)
		assertTrue(EcoreUtil.equals(firstRoot, secondRoot))
	}
}
