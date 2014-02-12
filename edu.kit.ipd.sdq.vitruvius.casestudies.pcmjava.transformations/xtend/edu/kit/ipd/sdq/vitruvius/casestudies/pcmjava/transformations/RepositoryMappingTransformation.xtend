package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import de.uka.ipd.sdq.pcm.repository.Repository
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI

class RepositoryMappingTransformation extends EObjectMappingTransformation {
	
	override getClassOfMappedEObject() {
		return typeof(Repository)
	}
	
	//TODO: write test cases + how will the models be saved?
	override addEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage
		jaMoPPPackage.name = repository.entityName
		val VURI vuri = VURI.getInstance("src/" + jaMoPPPackage.name)
		TransformationUtils.saveRootEObject(jaMoPPPackage, vuri)
		return jaMoPPPackage
	}
	
	override removeEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		return null
	}
	
	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val Repository repository = eObject as Repository
		return null
	}
	
	override updateEReference(EObject eObject, EReference affectedEReference, EObject newValue) {
		val Repository repository = eObject as Repository
		return null
	}
	
	override updateEContainmentReference(EObject eObject, EReference afffectedEReference, EObject newValue) {
		val Repository repository = eObject as Repository
		return null
	}
	
}