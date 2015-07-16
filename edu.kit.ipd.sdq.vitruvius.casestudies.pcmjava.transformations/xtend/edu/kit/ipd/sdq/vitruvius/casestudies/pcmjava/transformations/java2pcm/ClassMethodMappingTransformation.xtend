package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
import org.palladiosimulator.pcm.seff.SeffFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.ArrayList
import java.util.LinkedList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Method
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation

class ClassMethodMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return ClassMethod
	}
	
	override setCorrespondenceForFeatures() {
	}

	/**
	 * called when a ClassMethod has been added:
	 * if the class maps to a basic component and implements an interface and the new method matches to the interface method
	 * we create an emtpy SEFF for the method
	 */
	override createEObject(EObject eObject) {
		val classMethod = eObject as ClassMethod
		classMethod.createSEFFIfImplementsInterfaceMethod()
	}
	
	/**
	 * Called when the name of the method has been changed
	 * Check if the class method implements an interface an update/generate a new seff if necessary
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val oldAffectedEObject = EcoreUtil.copy(affectedEObject) as ClassMethod
		oldAffectedEObject.eSet(affectedAttribute, oldValue)
		checkAndUpdateCorrespondence(affectedEObject, oldAffectedEObject)
	}
	
	
	/**
     *  called when a parameter has been added
     *  Check if the class method implements an interface an update/generate a new seff if necessray  
     */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		checkAndUpdateCorrespondence(newAffectedEObject, oldAffectedEObject)
	}
	

	/**
	 * called when a parameter has been deleted/changed
	 * Check if the class method implements an interface an update/generate a new seff if necessray
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		checkAndUpdateCorrespondence(newAffectedEObject, oldAffectedEObject)
	}


	def private checkAndUpdateCorrespondence(EObject newAffectedEObject, EObject oldAffectedEObject) {
		val rdSEFFs = correspondenceInstance.getCorrespondingEObjectsByType(oldAffectedEObject, ResourceDemandingSEFF)
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		if(!rdSEFFs.nullOrEmpty){
			val eObjectsToRemove = removeEObject(oldAffectedEObject)
			for(eObjectToRemove : eObjectsToRemove){
				val tuid = correspondenceInstance.calculateTUIDFromEObject(eObjectToRemove)
				tcr.addCorrespondenceToDelete(correspondenceInstance, tuid)
				EcoreUtil.remove(eObjectToRemove)
			}
		}
		val newClassMethod = newAffectedEObject as ClassMethod
		val newSEFFs = newClassMethod.createSEFFIfImplementsInterfaceMethod
		if(!newSEFFs.nullOrEmpty){
			for(newSEFF : newSEFFs){
				var Correspondence parrentCorrespondence = null
				if(newSEFF instanceof ResourceDemandingSEFF){
					val newRDSEFF = newSEFF as ResourceDemandingSEFF
					val parrentCorrespondences = correspondenceInstance.getAllCorrespondences(newRDSEFF.basicComponent_ServiceEffectSpecification)
					if(!parrentCorrespondences.nullOrEmpty){
						parrentCorrespondence = parrentCorrespondences.get(0)
					}					
				}
				tcr.addNewCorrespondence(correspondenceInstance, newSEFF, newClassMethod, parrentCorrespondence)
				
			}			
		}
		return tcr
	}

	def private EObject[] createSEFFIfImplementsInterfaceMethod(ClassMethod classMethod) {
		val classifier = classMethod.containingConcreteClassifier
		val basicComponents = correspondenceInstance.getCorrespondingEObjectsByType(classifier, BasicComponent)
		if (basicComponents.nullOrEmpty) {
			return null
		}
		val implementingInterfaces = classifier.allSuperClassifiers
		if (implementingInterfaces.nullOrEmpty) {
			return null
		}
		val interfaceMethods = new LinkedList<Method>
		// check if one of the implmenenting interfaces has a correspondence to an OperationInterface
		// if yes: all methods in the Interface are potential methods, which are implmeneted by the new class method    
		for (interface : implementingInterfaces) {
			val opInterfaces = correspondenceInstance.getCorrespondingEObjectsByType(interface, OperationInterface)
			if (!opInterfaces.nullOrEmpty) {
				interfaceMethods.addAll(interface.methods)
			}
		}
		val equalMethods = interfaceMethods.filter [interfaceMethod|
			PCMJaMoPPUtils.hasSameSignature(interfaceMethod, classMethod)]
		if (equalMethods.nullOrEmpty) {
			return null
		}
		val returnSeffs = new ArrayList<ResourceDemandingSEFF>
		for (method : equalMethods) {
			val opSigs = correspondenceInstance.getCorrespondingEObjectsByType(method, OperationSignature)
			if (!opSigs.nullOrEmpty) {
				// create seff for first corresponding OpSig (note: there should be only one)
				val ResourceDemandingSEFF rdSEFF = SeffFactory.eINSTANCE.createResourceDemandingSEFF
				rdSEFF.describedService__SEFF = opSigs.get(0)
				rdSEFF.basicComponent_ServiceEffectSpecification = basicComponents.get(0)
				returnSeffs.add(rdSEFF)
			}
		}
		returnSeffs
	}

}