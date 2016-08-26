package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java

import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.packagemapping.javaimplementation.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.containers.Package
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
import org.palladiosimulator.pcm.core.entity.NamedElement
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils

/**
 * base class for RepositoryComponentMappingTransformation and SystemMappingTransformation
 */
abstract class ComposedProvidingRequiringEntityMappingTransformation extends EmptyEObjectMappingTransformation {

	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	def Package getParentPackage(EObject eObject)

	/**
	 * called when a ComposedProvidingRequiriungEntity has been created
	 * --> create a package, a compilation unit and a class for the entity
	 */
	override createEObject(EObject eObject) {
		val ComposedProvidingRequiringEntity composedEntity = eObject as ComposedProvidingRequiringEntity
		val Package parentPackage = getParentPackage(eObject)

		//create all elements
		val createdEObjects = PCM2JaMoPPUtils.createPackageCompilationUnitAndJaMoPPClass(composedEntity, parentPackage)

		return createdEObjects
	}

	override removeEObject(EObject eObject) {
		return null
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		PCM2JaMoPPUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceModel)
	}

	/**
	 * called when a AssemblyContext has been added
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		if ((affectedReference.name.equals(PCMNamespace.SYSTEM_ASSEMBLY_CONTEXTS__COMPOSED_STRUCTURE) ||
			affectedReference.name.equals(PCMNamespace.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMNamespace.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) &&
			newValue instanceof NamedElement) {
			PCM2JaMoPPUtils.
				handleAssemblyContextAddedAsNonRootEObjectInList(newAffectedEObject as ComposedProvidingRequiringEntity,
					newValue as NamedElement, newCorrespondingEObjects, correspondenceModel)
		} 
		return new TransformationResult
	}
	
	/**
	 * TODO: copied from BasicComponent: refactor 
	 * called when OperationProvidedRole has been removed from the current ComposedProvidingRequiringEntity
	 */
	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index) {

		//provided role removed - deletion of eobject should already be done in OperationProvidedRoleMappingTransformation - mark bc to save
		if (affectedReference.name.equals(PCMNamespace.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMNamespace.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
		}
		return new TransformationResult
	}

	/**
	 * TODO: copied from BasicComponent: refactor
	 * called when an OperationProvidedRole was has been inserted in the current ComposedProvidingRequiringEntity
	 */
	override insertNonRootEObjectInContainmentList(EObject oldAffectedEObject, EObject newAffectedEObject,
		EReference affectedReference, EObject newValue) {
		if (affectedReference.name.equals(PCMNamespace.COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY) ||
			affectedReference.name.equals(PCMNamespace.COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY)) {
			
		}
		return new TransformationResult
	}

}
