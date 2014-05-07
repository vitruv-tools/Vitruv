package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil

class PackageMappingTransformation extends EObjectMappingTransformation {
	
	override getClassOfMappedEObject() {
		return Package
	}
	
	override addEObject(EObject eObject) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override removeEObject(EObject eObject) {
		val Package jaMoPPPackage = eObject as Package
		val correspondingObjects = correspondenceInstance.claimCorrespondingEObjects(jaMoPPPackage)
		for(correspondingObject : correspondingObjects){
			EcoreUtil.remove(correspondingObject)
			correspondenceInstance.removeAllCorrespondingInstances(correspondingObject)
		}
		return null
	}
	
	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val Package jaMoPPPackage = eObject as Package
		val correspondingEObjects = correspondenceInstance.claimCorrespondingEObjects(jaMoPPPackage)
		val EStructuralFeature esf = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
		for(correspondingEObject : correspondingEObjects){
			correspondingEObject.eSet(esf, newValue)
		}
		return correspondingEObjects.iterator.next
	}
	
	override updateEReference(EObject eObject, EReference affectedEReference, Object newValue) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override updateEContainmentReference(EObject eObject, EReference afffectedEReference, Object newValue) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override setCorrespondenceForFeatures() {
		var packageNameAttribute = ContainersFactory.eINSTANCE.createPackage.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("name")].iterator.next
		var opInterfaceNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("entityName")].iterator.next
		featureCorrespondenceMap.put(packageNameAttribute, opInterfaceNameAttribute)
	}
	
}