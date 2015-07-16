package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.HashSet
import java.util.Set
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.Field
import org.emftext.language.java.types.TypeReference

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

		val fieldContainingClassifierCorrespondences = correspondenceInstance.
			getAllCorrespondingEObjects(field.containingConcreteClassifier)
		val Set<EObject> newCorrespondingEObjects = new HashSet
		val compositeDataTypes = fieldContainingClassifierCorrespondences.filter(typeof(CompositeDataType))
		if (!compositeDataTypes.nullOrEmpty) {

			//i)
			for (cdt : compositeDataTypes) {
				val InnerDeclaration innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration
				innerDeclaration.entityName = field.name
				innerDeclaration.datatype_InnerDeclaration = TypeReferenceCorrespondenceHelper.
					getCorrespondingPCMDataTypeForTypeReference(field.typeReference, correspondenceInstance,
						userInteracting, null, null)
				innerDeclaration.compositeDataType_InnerDeclaration = cdt
				newCorrespondingEObjects.add(innerDeclaration)
			}
		}

		val correspondingOperationRequiredRoles = field.checkAndAddOperationRequiredRolesCorrepondencesToField()
		if (null != correspondingOperationRequiredRoles) {
			newCorrespondingEObjects.addAll(correspondingOperationRequiredRoles)
		}
		val correspondingComposedEnitys = fieldContainingClassifierCorrespondences.filter(
			typeof(ComposedProvidingRequiringEntity))
		if (correspondingComposedEnitys.nullOrEmpty &&
			null != JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(field.typeReference)) {
			//new field is in a ComposedProvidingRequiringEntity
			val classifierOfField = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(field.typeReference)
			val correspondingComponents = correspondenceInstance.
				getCorrespondingEObjectsByType(classifierOfField, RepositoryComponent)
			if(!correspondingComponents.nullOrEmpty){
				val AssemblyContext assemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext
				assemblyContext.entityName = field.name
				assemblyContext.encapsulatedComponent__AssemblyContext = correspondingComponents.get(0)
				assemblyContext.parentStructure__AssemblyContext = correspondingComposedEnitys.get(0)
				newCorrespondingEObjects.addAll(assemblyContext)
			}
		}
		return newCorrespondingEObjects
	}

	override removeEObject(EObject eObject) {
		return correspondenceInstance.getAllCorrespondingEObjects(eObject)
	}

	/**
	 * if the field is renamed rename the corresponding objects on PCM side 
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		JaMoPP2PCMUtils.updateNameAsSingleValuedEAttribute(affectedEObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceInstance)
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
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		if (affectedReference.name.equals(PCMJaMoPPNamespace.JaMoPP.JAMOPP_REFERENCE_TYPE_REFERENCE) &&
			newValue instanceof TypeReference) {
			val newTypeReference = newValue as TypeReference

			//udpate InnerDeclaration
			val innerDecs = correspondenceInstance.getCorrespondingEObjectsByType(oldAffectedEObject, InnerDeclaration)
			if (!innerDecs.nullOrEmpty) {
				for (innerDec : innerDecs) {
					innerDec.datatype_InnerDeclaration = TypeReferenceCorrespondenceHelper.
						getCorrespondingPCMDataTypeForTypeReference(newTypeReference, correspondenceInstance,
							userInteracting, null, tcr)
					val oldTUID = correspondenceInstance.calculateTUIDFromEObject(oldAffectedEObject)
					tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, newValue, null)
					tcr.existingObjectsToSave.add(innerDec)
				}
			}

			//Remove all OperationRequiredRole for the field
			val operationRequiredRoles = correspondenceInstance.
				getCorrespondingEObjectsByType(oldAffectedEObject, OperationRequiredRole)
			for (operationRequiredRole : operationRequiredRoles) {
				val tuidToRemove = correspondenceInstance.calculateTUIDFromEObject(operationRequiredRole)
				tcr.addCorrespondenceToDelete(correspondenceInstance, tuidToRemove)
				EcoreUtil.delete(operationRequiredRole)
			}

			//add new OperationRequiredRoles that correspond to the field now
			if (newValue instanceof TypeReference) {
				val classifier = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(newValue as TypeReference)
				if (null != classifier) {
					val newField = newAffectedEObject as Field
					val newCorrespondingEObjects = newField.checkAndAddOperationRequiredRolesCorrepondencesToField()
					if (!newCorrespondingEObjects.nullOrEmpty) {
						for (newCorrspondingEObject : newCorrespondingEObjects) {
							tcr.addNewCorrespondence(correspondenceInstance, newCorrspondingEObject, newAffectedEObject,
								JaMoPP2PCMUtils.
									findMainParrentCorrepsondenceForPCMElement(newCorrspondingEObject,
										correspondenceInstance))
							tcr.existingObjectsToSave.add(newCorrspondingEObject)
						}
					}
				}
			}
		}
		return tcr
	}

	def private EObject[] checkAndAddOperationRequiredRolesCorrepondencesToField(Field field) {
		return JaMoPP2PCMUtils.checkAndAddOperationRequiredRole(field, correspondenceInstance, userInteracting)
	}

}
