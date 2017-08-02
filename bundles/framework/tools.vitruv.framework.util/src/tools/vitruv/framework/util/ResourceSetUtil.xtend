package tools.vitruv.framework.util

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry

class ResourceSetUtil {
	static extension Registry = Resource::Factory::Registry::INSTANCE

	def static void addExistingFactoriesToResourceSet(ResourceSet resourceSet) {
		extensionToFactoryMap.entrySet.forEach [
			resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put(key, value)
		]
		if (resourceSet.resourceFactoryRegistry.extensionToFactoryMap.empty)
			resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
	}
}
