package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import de.uka.ipd.sdq.pcm.repository.Repository
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import java.util.Collection
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package

class RepositoryMappingTransformation extends EObjectMappingTransformation {
	
	val private static final Logger logger = Logger.getLogger(RepositoryMappingTransformation.name)
	
	override getClassOfMappedEObject() {
		return typeof(Repository)
	}
	
	//TODO: write test cases + how will the models be saved?
	override addEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage
		jaMoPPPackage.name = repository.entityName
		val EObjectCorrespondence eObjectCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
		eObjectCorrespondence.setElementA(repository)
		eObjectCorrespondence.setElementB(jaMoPPPackage)
		correspondenceInstance.addCorrespondence(eObjectCorrespondence)
		return jaMoPPPackage
	}
	
	override removeEObject(EObject eObject) {
		val Repository repository = eObject as Repository
		return null
	}
	
	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val Repository repository = eObject as Repository
		val Object structuralFeature = repository.eGet(affectedAttribute)
		val Collection<Correspondence> correspondences = correspondenceInstance.getAllCorrespondences(repository)
		if(0 == correspondences.size ){
			logger.warn( "no correspondences found for attribute " + affectedAttribute 
				+ ". Should not happen for EAttribute in object" + repository )
			return null
		}
		for(Correspondence c : correspondences){
			
		}
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