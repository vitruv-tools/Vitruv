package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory

class BasicComponentMappingTransformation extends EmptyEObjectMappingTransformation {

	//val private static Logger logger = Logger.getLogger(BasicComponentMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return BasicComponent
	}

	override createEObject(EObject eObject) {
		val BasicComponent basicComponent = eObject as BasicComponent

		var Package rootPackage = PCM2JaMoPPUtils.findCorrespondingPackageByName(
			basicComponent.repository__RepositoryComponent.entityName, blackboard.correspondenceInstance,
			basicComponent.repository__RepositoryComponent)

		//create all necessary elements
		val retEObjects = PCM2JaMoPPUtils.createPackageCompilationUnitAndJaMoPPClass(basicComponent, rootPackage)
		return retEObjects
	}
	
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		if (null == newCorrespondingEObjects) {
			return 
		}
		
		for (jaMoPPElement : newCorrespondingEObjects) {
			blackboard.correspondenceInstance.createAndAddEObjectCorrespondence(newValue, jaMoPPElement)
		}
	}
	
	override removeEObject(EObject eObject) {
		TransformationUtils.removeCorrespondenceAndAllObjects(eObject, blackboard)
		null
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		if (oldCorrespondingEObjectsToDelete.nullOrEmpty) {
			return 
		}
		for (oldEObject : oldCorrespondingEObjectsToDelete) {
			TransformationUtils.removeCorrespondenceAndAllObjects(oldEObject, blackboard)
		}
	}

	/**
	 * Called when a basic component is renamed. Following things are done in order to preserve conistency:
	 * 1) remove old package-info.java file and adds a new one in the new package
	 * 2) remove old compilation unit and move it to the new package with the new name + Impl
	 * 3) rename the classifier in the compilaiton unit accordingly
	 * 
	 */
	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		PCM2JaMoPPUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, blackboard)
	}

	override setCorrespondenceForFeatures() {
		var basicComponentNameAttribute = RepositoryFactory.eINSTANCE.createBasicComponent.eClass.getEAllAttributes.
			filter[attribute|attribute.name.equalsIgnoreCase(PCMJaMoPPNamespace.PCM::PCM_ATTRIBUTE_ENTITY_NAME)].
			iterator.next
		var classNameAttribute = ClassifiersFactory.eINSTANCE.createClass.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(PCMJaMoPPNamespace.JaMoPP::JAMOPP_ATTRIBUTE_NAME)].iterator.next
		var packageNameAttribute = ContainersFactory.eINSTANCE.createPackage.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(PCMJaMoPPNamespace.JaMoPP::JAMOPP_ATTRIBUTE_NAME)].iterator.next
		featureCorrespondenceMap.put(basicComponentNameAttribute, classNameAttribute)
		featureCorrespondenceMap.put(basicComponentNameAttribute, packageNameAttribute)
	}

	/**
	 * called when OperationProvidedRole has been removed from the current basic component
	 */
	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index) {

		//provided role removed - deletion of eobject should already be done in OperationProvidedRoleMappingTransformation - mark bc to save
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			return
		}
		return 
	}

	/**
	 * called when an OperationProvidedRole was has been inserted in the current basic component
	 */
	override insertNonRootEObjectInContainmentList(EObject oldAffectedEObject, EObject newAffectedEObject,
		EReference affectedReference, EObject newValue) {
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			return
		}
	}

	/**
	 * called when a OperationProvidedRole has been removed and the reference to it is now unset
	 */
	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			if (null != affectedEObject) {
				return
			}
		}
		return 
	}
	
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return 
	}
}
