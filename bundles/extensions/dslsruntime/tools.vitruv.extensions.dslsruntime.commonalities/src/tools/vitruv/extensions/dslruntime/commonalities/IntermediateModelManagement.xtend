package tools.vitruv.extensions.dslruntime.commonalities

import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.nio.file.Files
import java.nio.file.Path
import java.util.Collections
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourceRemover

import static tools.vitruv.extensions.dslruntime.commonalities.CommonalitiesConstants.*

class IntermediateModelManagement {

	private new() {
	}

	// TODO get a directory in the VSUM
	@Lazy static val Path intermediateModelsFolder = Files.createTempDirectory('intermediatemodel')
	@Lazy static val ResourceSet intermediateModelsResourceSet = new ResourceSetImpl

	static val stagingUuidCounter = new AtomicInteger

	def private static checkIsNonRoot(EObject object, String providedName) {
		if (object.eClass.ESuperTypes.findFirst[name == INTERMEDIATE_MODEL_NONROOT_CLASS] === null) {
			throw new IllegalArgumentException('''The EObject ‹«object»› provided as «providedName» is not an intermediate model non root class!''')
		}
	}

	def private static checkIsRoot(EObject object) {
		if (!object.isRoot) {
			throw new IllegalArgumentException('''The EObject ‹«object»› provided as root is not an intermediate model root class!''')
		}
	}

	def package static getChildrenList(EObject root) {
		checkIsRoot(root)
		val containment = root.eClass.EAllContainments.findFirst[name == INTERMEDIATE_MODEL_ROOT_CLASS_CONTAINER_NAME]
		if (containment === null) {
			throw new IllegalStateException('''The intermediate model root ‹«root»› does not have the containment called “«INTERMEDIATE_MODEL_ROOT_CLASS_CONTAINER_NAME»”!''')
		}
		root.eGet(containment) as EList<EObject>
	}

	def package static isRoot(EObject root) {
		root.eClass.name == INTERMEDIATE_MODEL_ROOT_CLASS
	}

	def static void addNonRoot(Resource targetResource, EObject nonRoot) {
		var EObject root
		synchronized (targetResource) {
			if (targetResource.contents.isEmpty) {
				val intermediatePackage = nonRoot.eClass.EPackage
				val intermediateRootClass = intermediatePackage.EClassifiers.filter(EClass).filter [
					name == INTERMEDIATE_MODEL_ROOT_CLASS
				].head
				root = intermediatePackage.EFactoryInstance.create(intermediateRootClass)
				root.eAdapters += #[
					IntermediateModelNonRootIdAssigner.INSTANCE,
					IntermediateModelRootDisposer.INSTANCE,
					ResourceRemover.INSTANCE
				]
				targetResource.contents += root
			} else {
				root = targetResource.contents.get(0)
			}
		}
		root.childrenList += nonRoot
		targetResource.save(Collections.emptyMap())
	}

	def static assignStagingId(EObject nonRoot) {
		nonRoot.assignId(stagingUuidCounter.getAndIncrement())
	}

	def package static assignId(EObject nonRoot, int id) {
		checkIsNonRoot(nonRoot, 'non root')
		val uuidFeature = nonRoot.eClass.EAllStructuralFeatures.findFirst[name == INTERMEDIATE_MODEL_ID_ATTRIBUTE]
		if (uuidFeature === null) {
			throw new IllegalStateException('''The intermediate model non-root ‹«nonRoot»› does not have the id feature “«INTERMEDIATE_MODEL_ID_ATTRIBUTE»!”''')
		}
		nonRoot.eSet(uuidFeature,
			EcoreFactory.eINSTANCE.createFromString(EcorePackage.eINSTANCE.EString, String.valueOf(id)))
	}
}
