package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

abstract class EObjectMappingTransformation {

	var protected ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap;
	
	new (){
		featureCorrespondenceMap = new ClaimableHashMap<EStructuralFeature, EStructuralFeature>();
	}

	var protected CorrespondenceInstance correspondenceInstance

	def Class<?> getClassOfMappedEObject()

	def TransformationChangeResult addEObject(EObject eObject)

	def TransformationChangeResult removeEObject(EObject eObject)

	def TransformationChangeResult updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue)

	def TransformationChangeResult updateEReference(EObject eObject, EReference affectedEReference, Object newValue)

	def TransformationChangeResult updateEContainmentReference(EObject eObject, EReference afffectedEReference, Object newValue)
	
	def void setCorrespondenceForFeatures()

	def void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
		this.correspondenceInstance = correspondenceInstance
		if(null != correspondenceInstance){
			setCorrespondenceForFeatures()
		}
	}
	
	def protected toArray(EObject eObject){
		if(null == eObject){
			return null
		}
		#[eObject]
	}
}
