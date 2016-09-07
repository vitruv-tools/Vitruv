package tools.vitruvius.dsls.mapping.generator

import java.util.Collections
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl

class XMIDumper {
	static def void dump(EObject eObject, Resource res, String fileExtension) {
		val resourcePath = res.URI
		
		val reg = Resource.Factory.Registry.INSTANCE
		val m = reg.extensionToFactoryMap
		val xmifactory = new XMIResourceFactoryImpl

		// dump parsed mir file into xmi for debugging purposes
		m.put(
			fileExtension,
			xmifactory
		)
		val mirXMIResourceSet = new ResourceSetImpl
		val mirXMIResource = mirXMIResourceSet.createResource(
			resourcePath.trimFileExtension.appendFileExtension(fileExtension));
		mirXMIResource.contents.add(EcoreUtil.copy(eObject))
		mirXMIResource.save(Collections.EMPTY_MAP)
	}
}