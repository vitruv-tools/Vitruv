package tools.vitruv.change.atomic

import allElementTypes.Root
import java.nio.file.Path
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import tools.vitruv.change.atomic.command.internal.ApplyEChangeSwitch
import tools.vitruv.change.atomic.resolve.internal.AtomicEChangeUnresolver
import tools.vitruv.change.atomic.util.EChangeAssertHelper
import tools.vitruv.change.atomic.uuid.AtomicEChangeUuidResolver
import tools.vitruv.change.atomic.uuid.Uuid
import tools.vitruv.change.atomic.uuid.UuidResolver

import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.mapFixed
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

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
	var UuidResolver uuidResolver
	var AtomicEChangeUuidResolver atomicChangeResolver

	var AtomicEChangeUnresolver changeUnresolver;
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
		uuidResolver = UuidResolver.create(resourceSet)
		atomicChangeResolver = new AtomicEChangeUuidResolver(uuidResolver)
		resource = resourceSet.createResource(fileUri)

		rootObject = aet.Root
		uuidResolver.registerEObject(rootObject)
		resource.contents += rootObject
		resource.save(null)

		// Factories for creating changes
		changeUnresolver = new AtomicEChangeUnresolver(uuidResolver, resourceSet)
		atomicFactory = new TypeInferringAtomicEChangeFactory()
		compoundFactory = new TypeInferringCompoundEChangeFactory(atomicFactory)
		helper = new EChangeAssertHelper()
	}

	protected def final getResourceContent() {
		resource.contents
	}

	def protected void applyBackward(List<? extends EChange<EObject>> changes) {
		changes.reverseView.forEach[applyBackward]
	}
	
	def protected void applyBackward(EChange<EObject> change) {
		ApplyEChangeSwitch.applyEChange(change, false)
	}

	def protected List<EChange<EObject>> applyForwardAndResolve(List<? extends EChange<Uuid>> changes) {
		return changes.mapFixed[applyForwardAndResolve]
	}
	
	def protected EChange<EObject> applyForwardAndResolve(EChange<Uuid> change) {
		return atomicChangeResolver.resolveAndApplyForward(change)
	}
	
	def protected EChange<Uuid> unresolve(EChange<? extends EObject> eChange) {
		return changeUnresolver.unresolve(eChange)
	}
	
	def protected <O extends EObject> withUuid(O eObject) {
		uuidResolver.registerEObject(eObject)
		return eObject
	}
	
	def protected List<EChange<Uuid>> unresolve(List<? extends EChange<? extends EObject>> eChanges) {
		return changeUnresolver.unresolve(eChanges)
	}
	
}
