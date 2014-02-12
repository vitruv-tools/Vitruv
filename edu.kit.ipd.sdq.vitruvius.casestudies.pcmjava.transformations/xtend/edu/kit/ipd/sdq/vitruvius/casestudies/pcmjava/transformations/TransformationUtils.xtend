package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EcoreResourceBridge
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

class TransformationUtils {
	val private static ResourceSet resourceSet = new ResourceSetImpl
	private new() {
		
	}
	
	def static saveRootEObject(EObject rootEObject, VURI path){
		val Resource resource = resourceSet.createResource(path.EMFUri) 
		EcoreResourceBridge.saveEObjectAsOnlyContent(rootEObject,resource)
	}
}