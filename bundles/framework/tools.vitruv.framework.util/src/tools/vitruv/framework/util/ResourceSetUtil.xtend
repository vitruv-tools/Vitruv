package tools.vitruv.framework.util

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class ResourceSetUtil {
	def static withGlobalFactories(ResourceSet resourceSet) {
		resourceSet => [
			resourceFactoryRegistry.extensionToFactoryMap += Resource.Factory.Registry.INSTANCE.extensionToFactoryMap
			resourceFactoryRegistry.extensionToFactoryMap += "*" -> new XMIResourceFactoryImpl
		]
	}
}
