package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.HashSet
import java.util.Set
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.members.Field
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory

class FieldMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return Field
	}

	override setCorrespondenceForFeatures() {
		JaMoPP2PCMUtils.addName2EntityNameCorrespondence(featureCorrespondenceMap)
	}

	/**
	 * Called when a field has been created. The following correspondences for a field are possible:
	 * i) InnerDeclaration, if the field has been created in a class that corresponds to a 
	 *    composite datatype. If the type of the field corresponds to a Component or interface 
	 *    type we display a warning  
	 * ii) OperationRequiredRole, 
	 * 	   a) the type of the field corresponds to a an OperationInterface
	 *     b) if the type of the field is from class that corresponds to 
	 * 	      a repository component require all interfaces the repository component provides
	 * iii) InnerDeclaration, OperationRequiredRole: If i) and ii) are true
	 * iv) AssemblyContext: If the field is in a class that represents a ComposedProvidingRequiringEntity
	 * 	   (a CompositeComponent or a System) it can either correspond to an assemblyContext or an OperationRequiredRole
	 */
	@Override
	override createEObject(EObject eObject) {
		val field = eObject as Field

		val fieldContainingClassifierCorrespondences = blackboard.correspondenceInstance.getAllCorrespondingEObjects(
			field.containingConcreteClassifier)
		val Set<EObject> newCorrespondingEObjects = new HashSet
		val compositeDataTypes = fieldContainingClassifierCorrespondences.filter(typeof(CompositeDataType))
		if (!compositeDataTypes.nullOrEmpty) {

			// i)
			for (cdt : compositeDataTypes) {
				val InnerDeclaration innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration
				innerDeclaration.entityName = field.name
				innerDeclaration.datatype_InnerDeclaration = TypeReferenceCorrespondenceHelper.
					getCorrespondingPCMDataTypeForTypeReference(field.typeReference, blackboard.correspondenceInstance,
						userInteracting, null)
				innerDeclaration.compositeDataType_InnerDeclaration = cdt
				newCorrespondingEObjects.add(innerDeclaration)
			}
		}

		val correspondingOperationRequiredRoles = field.checkAndAddOperationRequiredRolesCorrepondencesToField()
		if (null != correspondingOperationRequiredRoles) {
			newCorrespondingEObjects.addAll(correspondingOperationRequiredRoles)
		}
		val correspondingComposedProvidingRequiringEntitys = fieldContainingClassifierCorrespondences.filter(
			typeof(ComposedProvidingRequiringEntity))
		if (!correspondingComposedProvidingRequiringEntitys.nullOrEmpty &&
			null != JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(field.typeReference)) {
			// new field is in a ComposedProvidingRequiringEntity
			val classifierOfField = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(field.typeReference)
			val correspondingComponents = blackboard.correspondenceInstance.getCorrespondingEObjectsByType(classifierOfField,
				RepositoryComponent)
			if (!correspondingComponents.nullOrEmpty) {

				val AssemblyContext assemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext
				assemblyContext.entityName = field.name
				assemblyContext.encapsulatedComponent__AssemblyContext = correspondingComponents.get(0)
				assemblyContext.parentStructure__AssemblyContext = correspondingComposedProvidingRequiringEntitys.get(0)
				newCorrespondingEObjects.addAll(assemblyContext)
			}
		}
		return newCorrespondingEObjects
	}

	override removeEObject(EObject eObject) {
		TransformationUtils.removeCorrespondenceAndAllObjects(eObject, blackboard)
		return null
	}

	/**
	 * if the field is renamed rename the corresponding objects on PCM side 
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val transformationResult = new TransformationResult
		JaMoPP2PCMUtils.updateNameAsSingleValuedEAttribute(affectedEObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, blackboard, transformationResult)
		return transformationResult
	}

	/**
	 * called when the type has been changed
	 * If the field corresponds to a InnerDeclaration: change type of InnerDeclaration
	 * If the field corresponds to a OperationRequiredRole: check whether the new type also corresponds 
	 * to a OperationRequiredRole and change it accordingly
	 * TODO:If the field corresponds to an AssemblyContext: also update the TypeReference or 
	 * even remove the AssemblyContext if the new TypeReference does not correspond to a component 
	 */
	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {
		val transformationResult = new TransformationResult
		if (affectedReference.name.equals(PCMJaMoPPNamespace.JaMoPP.JAMOPP_REFERENCE_TYPE_REFERENCE) &&
			newValue instanceof TypeReference) {
			val newTypeReference = newValue as TypeReference

			// udpate InnerDeclaration
			val innerDecs = blackboard.correspondenceInstance.getCorrespondingEObjectsByType(oldAffectedEObject, InnerDeclaration)
			if (!innerDecs.nullOrEmpty) {
				for (innerDec : innerDecs) {
					innerDec.datatype_InnerDeclaration = TypeReferenceCorrespondenceHelper.
						getCorrespondingPCMDataTypeForTypeReference(newTypeReference, blackboard.correspondenceInstance,
							userInteracting, null)
					blackboard.correspondenceInstance.updateTUID(oldAffectedEObject, newValue)
				}
			}

			// Remove all OperationRequiredRole for the field
			val operationRequiredRoles = blackboard.correspondenceInstance.getCorrespondingEObjectsByType(oldAffectedEObject,
				OperationRequiredRole)
			for (operationRequiredRole : operationRequiredRoles) {
				TransformationUtils.removeCorrespondenceAndAllObjects(operationRequiredRole, blackboard)
			}

			// add new OperationRequiredRoles that correspond to the field now
			if (newValue instanceof TypeReference) {
				val classifier = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(newValue as TypeReference)
				if (null != classifier) {
					val newField = newAffectedEObject as Field
					val newCorrespondingEObjects = newField.checkAndAddOperationRequiredRolesCorrepondencesToField()
					if (!newCorrespondingEObjects.nullOrEmpty) {
						for (newCorrspondingEObject : newCorrespondingEObjects) {
							blackboard.correspondenceInstance.createAndAddCorrespondence(newCorrspondingEObject, newAffectedEObject)
						}
					}
				}
			}
		}
		return transformationResult
	}

	def private EObject[] checkAndAddOperationRequiredRolesCorrepondencesToField(Field field) {
		return JaMoPP2PCMUtils.checkAndAddOperationRequiredRole(field, blackboard.correspondenceInstance, userInteracting)
	}

}
