package tools.vitruv.extensions.dslruntime.commonalities

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Root

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class IntermediateModelManagement {

	private new() {
	}

	static def claimIntermediateId(Intermediate intermediate) {
		intermediate.setIntermediateId(EcoreUtil.generateUUID());
	}

	static def void addIntermediate(Resource targetResource, Intermediate intermediate) {
		val root = getOrCreateRootIn(targetResource, intermediate.eClass.EPackage)
		root.intermediates += intermediate
	}

	static def void addResourceBridge(
		Resource targetResource,
		tools.vitruv.extensions.dslruntime.commonalities.resources.Resource intermediateResource,
		Intermediate intermediate
	) {
		val root = getOrCreateRootIn(targetResource, intermediate.eClass.EPackage)
		root.resourceBridges += intermediateResource
	}

	static def getOrCreateRootIn(Resource targetResource, EPackage ePackage) {
		synchronized (targetResource) {
			if (targetResource.contents.isEmpty) {
				val rootClass = ePackage.EClassifiers.filter(EClass).findFirst [
					ESuperTypes.containsAny[it == IntermediateModelBasePackage.eINSTANCE.root]
				]
				val root = ePackage.EFactoryInstance.create(rootClass) as Root
				root.eAdapters += IntermediateModelRootDisposer.INSTANCE
				targetResource.contents += root
				targetResource.modified = true
				return root
			}
			return targetResource.contents.get(0) as Root
		}
	}
}
