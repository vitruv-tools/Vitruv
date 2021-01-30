package tools.vitruv.framework.util

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.transaction.TransactionalEditingDomain

@Utility
class ResourceSetUtil {
	def static withGlobalFactories(ResourceSet resourceSet) {
		resourceSet => [
			resourceFactoryRegistry.extensionToFactoryMap += Resource.Factory.Registry.INSTANCE.extensionToFactoryMap
			resourceFactoryRegistry.extensionToFactoryMap += "*" -> new XMIResourceFactoryImpl
		]
	}

	def static getRequiredTransactionalEditingDomain(ResourceSet resourceSet) {
		resourceSet.transactionalEditingDomain ?:
			synchronized (resourceSet) {
				resourceSet.transactionalEditingDomain ?:
					TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resourceSet)
			}
	}

	def static getTransactionalEditingDomain(ResourceSet resourceSet) {
		TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(resourceSet)
	}
}
