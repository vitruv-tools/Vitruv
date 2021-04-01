package tools.vitruv.framework.tests.echange

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
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
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import org.eclipse.emf.ecore.EObject
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolver.*

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
	var UuidGeneratorAndResolver creationUuidGeneratorAndResolver
	var UuidGeneratorAndResolver reapplicationUuidGeneratorAndResolver

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
		val fileUri = modelFile.toFile().createFileURI()

		// Create model
		resourceSet = new ResourceSetImpl().withGlobalFactories
		resource = resourceSet.createResource(fileUri)

		rootObject = aet.Root
		resource.contents += rootObject
		resource.save(null)

		// Factories for creating changes
		this.creationUuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet)
		this.reapplicationUuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet)
		atomicFactory = new TypeInferringUnresolvingAtomicEChangeFactory(creationUuidGeneratorAndResolver)
		compoundFactory = new TypeInferringUnresolvingCompoundEChangeFactory(creationUuidGeneratorAndResolver)
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
		return change.resolveBefore(reapplicationUuidGeneratorAndResolver)
	}

	def protected List<EChange> resolveBefore(List<? extends EChange> changes) {
		val result = newArrayList
		result += changes.map[resolveBefore]
		return result
	}

	static def protected void applyBackward(List<EChange> changes) {
		assertIsResolved(changes)
		changes.reverseView.forEach[applyBackward]
	}

	static def protected void applyForward(List<EChange> changes) {
		assertIsResolved(changes)
		changes.forEach[applyForward]
	}
	
	protected def <T extends EObject> T withUuid(T eObject) {
		creationUuidGeneratorAndResolver.generateUuid(eObject)
		return eObject
	}
	
	protected def <T extends EObject> T registerAsPreexisting(T eObject) {
		reapplicationUuidGeneratorAndResolver.registerEObject(creationUuidGeneratorAndResolver.getUuid(eObject), eObject)
		return eObject
	}
}
