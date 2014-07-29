package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap
import org.eclipse.emf.ecore.EStructuralFeature
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance

abstract class EObjectMappingTransformation {

	var protected ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap;
	
	new (){
		featureCorrespondenceMap = new ClaimableHashMap<EStructuralFeature, EStructuralFeature>();
	}

	var protected CorrespondenceInstance correspondenceInstance

	def Class<?> getClassOfMappedEObject()

	def EObject[] addEObject(EObject eObject)

	def EObject[] removeEObject(EObject eObject)

	def EObject[] updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue)

	def EObject[] updateEReference(EObject eObject, EReference affectedEReference, Object newValue)

	def EObject[] updateEContainmentReference(EObject eObject, EReference afffectedEReference, Object newValue)
	
	def void setCorrespondenceForFeatures()

	def void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
		this.correspondenceInstance = correspondenceInstance
		if(null != correspondenceInstance){
			setCorrespondenceForFeatures()
		}
	}
	
	def protected EObject[] toArray(EObject eObject){
		if(null == eObject){
			return null
		}
		return #{eObject}
	}
}
