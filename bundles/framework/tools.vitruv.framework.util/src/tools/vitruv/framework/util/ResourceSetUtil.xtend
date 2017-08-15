package tools.vitruv.framework.util

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.transaction.TransactionalEditingDomain
import org.eclipse.emf.transaction.TransactionalEditingDomain.Factory

class ResourceSetUtil {
	static extension Factory = TransactionalEditingDomain::Factory::INSTANCE
	static extension Registry = Resource::Factory::Registry::INSTANCE

	def static void addExistingFactoriesToResourceSet(ResourceSet resourceSet) {
		extensionToFactoryMap.entrySet.forEach [
			resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put(key, value)
		]
		if (resourceSet.resourceFactoryRegistry.extensionToFactoryMap.empty)
			resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
	}

	static def synchronized TransactionalEditingDomain getTransactionalEditingDomain(ResourceSet currentResourceSet) {
		if (null === getEditingDomain(currentResourceSet)) createEditingDomain(currentResourceSet)
		return getEditingDomain(currentResourceSet)
	}
}
