package tools.vitruv.extensions.dslruntime.commonalities

import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Root

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class IntermediateModelManagement {

	private new() {
	}

	// TODO get a directory in the VSUM
	@Lazy static val Path intermediateModelsFolder = Files.createTempDirectory('intermediatemodel')
	@Lazy static val ResourceSet intermediateModelsResourceSet = new ResourceSetImpl

	static val stagingUuidCounter = new AtomicInteger

	def static void addIntermediate(Resource targetResource, Intermediate nonRoot) {
		val root = getOrCreateRootIn(targetResource, nonRoot.eClass.EPackage)
		nonRoot.intermediateId = String.valueOf(root.nextId)
		root.intermediates += nonRoot
		targetResource.modified = true
	}

	def static void addResourceBridge(
		Resource targetResource,
		tools.vitruv.extensions.dslruntime.commonalities.resources.Resource intermediateResource,
		Intermediate intermediate
	) {
		val root = getOrCreateRootIn(targetResource, intermediate.eClass.EPackage)
		root.resourceBridges += intermediateResource
		targetResource.modified = true
	}

	def static getOrCreateRootIn(Resource targetResource, EPackage ePackage) {
		synchronized (targetResource) {
			if (targetResource.contents.isEmpty) {
				val rootClass = ePackage.EClassifiers.filter(EClass).findFirst [
					ESuperTypes.containsAny [it == IntermediateModelBasePackage.eINSTANCE.root]
				]
				val root = ePackage.EFactoryInstance.create(rootClass) as Root
				root.eAdapters += IntermediateModelRootDisposer.INSTANCE
				targetResource.contents += root
				return root
			}
			return targetResource.contents.get(0) as Root
		}
	}

	def static claimStagingId(Intermediate nonRoot) {
		nonRoot.intermediateId = String.valueOf(stagingUuidCounter.getAndIncrement())
	}

	def private static getNextId(Root root) {
		var int oldCounter
		synchronized (root) {
			oldCounter = root.intermediateIdCounter
			root.intermediateIdCounter = oldCounter + 1
		}
		return oldCounter
	}
}
	