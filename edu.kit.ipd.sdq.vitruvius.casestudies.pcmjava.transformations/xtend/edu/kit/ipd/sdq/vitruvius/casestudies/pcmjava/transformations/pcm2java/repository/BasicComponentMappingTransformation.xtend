package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.BasicComponent
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package

class BasicComponentMappingTransformation extends EmptyEObjectMappingTransformation {

	//val private static Logger logger = Logger.getLogger(BasicComponentMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return BasicComponent
	}

	override createEObject(EObject eObject) {
		val BasicComponent basicComponent = eObject as BasicComponent

		var Package rootPackage = PCM2JaMoPPUtils.findCorrespondingPackageByName(
			basicComponent.repository__RepositoryComponent.entityName, correspondenceInstance,
			basicComponent.repository__RepositoryComponent)

		//create all necessary elements
		val retEObjects = PCM2JaMoPPUtils.createPackageCompilationUnitAndJaMoPPClass(basicComponent, rootPackage)
		return retEObjects
	}
	
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		if (null == newCorrespondingEObjects) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val BasicComponent basicComponent = newAffectedEObject as BasicComponent
		val parentCorrespondences = correspondenceInstance.getAllCorrespondences(basicComponent)
		var Correspondence parentCorrespondence = null
		if (!parentCorrespondences.nullOrEmpty) {
			parentCorrespondence = parentCorrespondences.get(0)
		}
		val rootObjectsToSave = newCorrespondingEObjects.filter(typeof(JavaRoot))
		val nonRootEObjectsToSave = newCorrespondingEObjects.filter[correspondingEObject|
			false == correspondingEObject instanceof JavaRoot]
		val transformationResult = TransformationUtils.createTransformationChangeResult(rootObjectsToSave, null,
			nonRootEObjectsToSave)
		for (jaMoPPElement : newCorrespondingEObjects) {
			transformationResult.addNewCorrespondence(correspondenceInstance, newValue, jaMoPPElement,
				parentCorrespondence)
		}
		return transformationResult
	}

	override removeEObject(EObject eObject) {
		return correspondenceInstance.getAllCorrespondingEObjects(eObject)
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		if (oldCorrespondingEObjectsToDelete.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		for (oldEObject : oldCorrespondingEObjectsToDelete) {
			val tuidToRemove = correspondenceInstance.calculateTUIDFromEObject(oldEObject)
			tcr.addCorrespondenceToDelete(correspondenceInstance, tuidToRemove)
			if (null != oldEObject.eContainer) {
				tcr.existingObjectsToSave.add(oldEObject.eContainer)
			}
			EcoreUtil.remove(oldEObject)
		}
		return tcr
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
			featureCorrespondenceMap, correspondenceInstance)
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
			return TransformationUtils.createTransformationChangeResultForEObjectsToSave(affectedEObject.toArray)
		}
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	/**
	 * called when an OperationProvidedRole was has been inserted in the current basic component
	 */
	override insertNonRootEObjectInContainmentList(EObject oldAffectedEObject, EObject newAffectedEObject,
		EReference affectedReference, EObject newValue) {
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			if (null != newAffectedEObject) {
				return TransformationUtils.createTransformationChangeResultForEObjectsToSave(newAffectedEObject.toArray)
			}
		}
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	/**
	 * called when a OperationProvidedRole has been removed and the reference to it is now unset
	 */
	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMJaMoPPNamespace.PCM.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			if (null != affectedEObject) {

				// no deletion of old values here because the deletion should have been done in OperationProvidedRoleMapping already
				return TransformationUtils.createTransformationChangeResultForEObjectsToSave(affectedEObject.toArray)
			}
		}
		return TransformationUtils.createEmptyTransformationChangeResult
	}
}
