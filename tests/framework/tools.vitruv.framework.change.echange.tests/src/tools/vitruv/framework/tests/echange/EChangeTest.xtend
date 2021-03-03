package tools.vitruv.framework.tests.echange

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import org.junit.jupiter.api.BeforeEach
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import org.eclipse.xtend.lib.annotations.Accessors
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import java.nio.file.Path
import tools.vitruv.framework.util.bridges.EMFBridge
import org.junit.jupiter.api.io.TempDir
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver

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
	var UuidGeneratorAndResolver uuidGeneratorAndResolver

	@Accessors(PROTECTED_GETTER)
	var TypeInferringAtomicEChangeFactory atomicFactory
	@Accessors(PROTECTED_GETTER)
	var TypeInferringCompoundEChangeFactory compoundFactory

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
		val fileUri = EMFBridge.getEmfFileUriForFile(modelFile.toFile)

		// Create model
		resourceSet = new ResourceSetImpl().withGlobalFactories
		resource = resourceSet.createResource(fileUri)

		rootObject = aet.Root
		resource.contents += rootObject
		resource.save(null)

		// Factories for creating changes
		this.uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet)
		atomicFactory = new TypeInferringUnresolvingAtomicEChangeFactory(uuidGeneratorAndResolver)
		compoundFactory = new TypeInferringUnresolvingCompoundEChangeFactory(uuidGeneratorAndResolver)
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
			assertTrue(change.isResolved)
		}
	}

	def protected EChange resolveBefore(EChange change) {
		return change.resolveBefore(uuidGeneratorAndResolver)
	}

	def protected List<EChange> resolveBefore(List<? extends EChange> changes) {
		val result = newArrayList
		result += changes.map[resolveBefore]
		return result
	}

	def protected EChange resolveAfter(EChange change) {
		return change.resolveAfter(uuidGeneratorAndResolver)
	}

	def protected List<EChange> resolveAfter(List<? extends EChange> changes) {
		val result = newArrayList
		result += changes.reverseView.map[resolveAfter]
		return result.reverse
	}

	static def protected boolean applyBackward(List<EChange> changes) {
		assertIsResolved(changes)
		var result = true
		for (change : changes.reverseView) {
			result = result && change.applyBackward
		}
		return result
	}

	static def protected boolean applyForward(List<EChange> changes) {
		assertIsResolved(changes)
		var result = true
		for (change : changes) {
			result = result && change.applyForward
		}
		return result
	}
}
