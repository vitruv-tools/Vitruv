package tools.vitruv.framework.tests.echange

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.uuid.UuidResolver
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.api.BeforeEach
import java.io.File
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Default class for testing EChange changes.
 * Prepares two temporary model instances of the allelementtypes metamodel which 
 * can be modified by the EChange tests. The model is stored in one temporary file.
 */
abstract class EChangeTest {

	protected var Root rootObject = null
	protected var Resource resource = null
	protected var ResourceSet resourceSet = null
	protected var UuidGeneratorAndResolver uuidGeneratorAndResolver = null

	protected var TypeInferringAtomicEChangeFactory atomicFactory = null
	protected var TypeInferringCompoundEChangeFactory compoundFactory = null

	protected URI fileUri = null

	protected static val METAMODEL = "allelementtypes"
	protected static val MODEL_FILE_NAME = "model"

	@Accessors(PROTECTED_GETTER)
	File testFolder;

	/**
	 * Sets up a new model and the two model instances before every test.
	 * The model is stored in a temporary file with filename {@link MODEL_FILE_NAME} 
	 * with extension {@link METAMODEL}. The folder is accessible by the attribute {@link testFolder}.
	 * The two model instances are stored in the two {@link ResourceSet} attributes {@link resourceSet1} and
	 * {@link resourceSet2}.
	 */
	@BeforeEach
	def void beforeTest(@TempDir File testFolder) {
		this.testFolder = testFolder

		// Setup Files
		var modelFile = new File(testFolder, MODEL_FILE_NAME + "." + METAMODEL)
		fileUri = URI.createFileURI(modelFile.getAbsolutePath())

		// Create model
		resourceSet = new ResourceSetImpl().withGlobalFactories
		resource = resourceSet.createResource(fileUri)

		rootObject = AllElementTypesFactory.eINSTANCE.createRoot()
		resource.getContents().add(rootObject)

		resource.save(null)

		// Factories for creating changes
		this.uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(resourceSet, true)
		atomicFactory = new TypeInferringUnresolvingAtomicEChangeFactory(uuidGeneratorAndResolver);
		compoundFactory = new TypeInferringUnresolvingCompoundEChangeFactory(uuidGeneratorAndResolver);
	}

	/**
	 * Assert that change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		for (change : changes) {
			assertFalse(change.isResolved);
			for (involvedObject : change.involvedEObjects) {
				assertNull(involvedObject);
			}
		}
	}

	/**
	 * Assert that change is resolved.
	 */
	def protected static void assertIsResolved(List<? extends EChange> changes) {
		for (change : changes) {
			assertTrue(change.isResolved);
			for (involvedObject : change.involvedEObjects) {
				assertNotNull(involvedObject);
			}
		}
	}

	static def protected List<EChange> resolveBefore(List<? extends EChange> changes, UuidResolver uuidResolver) {
		val result = newArrayList
		for (change : changes) {
			result += change.resolveBefore(uuidResolver);
		}
		return result;
	}

	static def protected List<EChange> resolveAfter(List<? extends EChange> changes, UuidResolver uuidResolver) {
		val result = newArrayList
		for (change : changes.reverseView) {
			result.add(0, change.resolveAfter(uuidResolver));
		}
		return result;
	}

	static def protected boolean applyBackward(List<EChange> changes) {
		assertIsResolved(changes);
		var result = true;
		for (change : changes.reverseView) {
			result = result && change.applyBackward
		}
		return result;
	}

	static def protected boolean applyForward(List<EChange> changes) {
		assertIsResolved(changes);
		var result = true;
		for (change : changes) {
			result = result && change.applyForward
		}
		return result;
	}
}
