package tools.vitruv.framework.tests.echange

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import org.junit.jupiter.api.BeforeEach
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.eclipse.xtend.lib.annotations.Accessors
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import java.nio.file.Path
import org.junit.jupiter.api.io.TempDir
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import tools.vitruv.framework.tests.echange.util.EChangeAssertHelper
import tools.vitruv.framework.change.id.IdResolverAndRepository
import static tools.vitruv.framework.change.id.IdResolverAndRepositoryFactory.createIdResolverAndRepository

/**
 * Default class for testing EChange changes.
 * Prepares two temporary model instances of the allelementtypes metamodel which 
 * can be modified by the EChange tests. The model is stored in one temporary file.
 */
abstract class EChangeTest {
	static val METAMODEL = "allelementtypes"
	static val MODEL_FILE_NAME = "model"

	@Accessors(PROTECTED_GETTER)
	var Root rootObject
	@Accessors(PROTECTED_GETTER)
	var Resource resource
	var ResourceSet resourceSet
	var IdResolverAndRepository idResolverAndRepository

	@Accessors(PROTECTED_GETTER)
	var TypeInferringAtomicEChangeFactory atomicFactory
	@Accessors(PROTECTED_GETTER)
	var TypeInferringCompoundEChangeFactory compoundFactory
	protected var extension EChangeAssertHelper helper
	 
	/**
	 * Sets up a new model and the two model instances before every test.
	 * The model is stored in a temporary file with filename {@link MODEL_FILE_NAME} 
	 * with extension {@link METAMODEL}. The folder is accessible by the attribute {@link testFolder}.
	 * The two model instances are stored in the two {@link ResourceSet} attributes {@link resourceSet1} and
	 * {@link resourceSet2}.
	 */
	@BeforeEach
	def final void beforeTest(@TempDir Path testFolder) {
		// Setup Files
		var modelFile = testFolder.resolve(MODEL_FILE_NAME + "." + METAMODEL)
		val fileUri = modelFile.toFile().createFileURI()

		// Create model
		resourceSet = new ResourceSetImpl().withGlobalFactories
		resource = resourceSet.createResource(fileUri)

		rootObject = aet.Root
		resource.contents += rootObject
		resource.save(null)

		// Factories for creating changes
		idResolverAndRepository = createIdResolverAndRepository(resourceSet)
		atomicFactory = new TypeInferringUnresolvingAtomicEChangeFactory(idResolverAndRepository)
		compoundFactory = new TypeInferringUnresolvingCompoundEChangeFactory(idResolverAndRepository)
		helper = new EChangeAssertHelper(idResolverAndRepository)
	}

	protected def final getResourceContent() {
		resource.contents
	}

	/**
	 * Assert that change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		for (change : changes) {
			assertFalse(change.isResolved)
		}
	}

	/**
	 * Assert that change is resolved.
	 */
	def protected static void assertIsResolved(List<? extends EChange> changes) {
		for (change : changes) {
			change.assertIsResolved()
		}
	}
	
	def protected static void assertIsResolved(EChange change) {
		assertTrue(change.isResolved)
	}

	def protected EChange resolveBefore(EChange change) {
		return change.resolveBefore(idResolverAndRepository)
	}

	def protected List<EChange> resolveBefore(List<? extends EChange> changes) {
		val result = newArrayList
		result += changes.map[resolveBefore]
		return result
	}

	def protected void applyBackward(List<EChange> changes) {
		assertIsResolved(changes)
		changes.reverseView.forEach[applyBackward]
	}

	def protected void applyForward(List<EChange> changes) {
		changes.forEach[applyForward]
	}
	
	def protected void applyForward(EChange change) {
		assertIsResolved(change)
		change.assertApplyForward
	}
	
}
