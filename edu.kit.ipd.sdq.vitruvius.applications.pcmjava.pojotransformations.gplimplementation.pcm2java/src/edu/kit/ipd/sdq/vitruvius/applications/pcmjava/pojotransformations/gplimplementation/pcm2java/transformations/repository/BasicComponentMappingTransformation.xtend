package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository

import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.domains.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.PCMJaMoPPUtils

class BasicComponentMappingTransformation extends EmptyEObjectMappingTransformation {

	//val private static Logger logger = Logger.getLogger(BasicComponentMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return BasicComponent
	}

	override createEObject(EObject eObject) {
		val BasicComponent basicComponent = eObject as BasicComponent

		var Package rootPackage = PCM2JaMoPPUtils.findCorrespondingPackageByName(
			basicComponent.repository__RepositoryComponent.entityName, correspondenceModel,
			basicComponent.repository__RepositoryComponent)

		//create all necessary elements
		val retEObjects = PCM2JaMoPPUtils.createPackageCompilationUnitAndJaMoPPClass(basicComponent, rootPackage)
		return retEObjects
	}
	
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		val transformationResult = new TransformationResult
		if (null == newCorrespondingEObjects) {
			return transformationResult
		}
		for (jaMoPPElement : newCorrespondingEObjects) {
			correspondenceModel.createAndAddCorrespondence(newValue.toList, jaMoPPElement.toList)
		}
		transformationResult
	}
	
	override removeEObject(EObject eObject) {
		return null
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		PCMJaMoPPUtils.deleteNonRootEObjectInList(oldAffectedEObject, oldValue, correspondenceModel)
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
		return PCM2JaMoPPUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceModel)
	}

	override setCorrespondenceForFeatures() {
		var basicComponentNameAttribute = RepositoryFactory.eINSTANCE.createBasicComponent.eClass.getEAllAttributes.
			filter[attribute|attribute.name.equalsIgnoreCase(PCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME)].
			iterator.next
		var classNameAttribute = ClassifiersFactory.eINSTANCE.createClass.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPNamespace.JAMOPP_ATTRIBUTE_NAME)].iterator.next
		var packageNameAttribute = ContainersFactory.eINSTANCE.createPackage.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPNamespace.JAMOPP_ATTRIBUTE_NAME)].iterator.next
		featureCorrespondenceMap.put(basicComponentNameAttribute, classNameAttribute)
		featureCorrespondenceMap.put(basicComponentNameAttribute, packageNameAttribute)
	}

	/**
	 * called when OperationProvidedRole has been removed from the current basic component
	 */
	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index) {
		val transformationResult = new TransformationResult
		//provided role removed - deletion of eobject should already be done in OperationProvidedRoleMappingTransformation - mark bc to save
		if (affectedReference.name.equals(PCMNamespace.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMNamespace.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			return transformationResult
		}
		return transformationResult
	}

	/**
	 * called when an OperationProvidedRole was has been inserted in the current basic component
	 */
	override insertNonRootEObjectInContainmentList(EObject oldAffectedEObject, EObject newAffectedEObject,
		EReference affectedReference, EObject newValue) {
		val transformationResult = new TransformationResult
		if (affectedReference.name.equals(PCMNamespace.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMNamespace.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			return transformationResult
		}
	}

	/**
	 * called when a OperationProvidedRole has been removed and the reference to it is now unset
	 */
	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {
		val transformationResult = new TransformationResult
		if (affectedReference.name.equals(PCMNamespace.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMNamespace.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			if (null != affectedEObject) {
				return transformationResult
			}
		}
		return transformationResult
	}
	
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return new TransformationResult
	}
}
