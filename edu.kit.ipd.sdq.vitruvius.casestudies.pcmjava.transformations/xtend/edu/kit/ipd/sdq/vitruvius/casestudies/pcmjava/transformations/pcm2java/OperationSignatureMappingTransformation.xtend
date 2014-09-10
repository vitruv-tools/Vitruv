package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.OperationSignature
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.TypesFactory

class OperationSignatureMappingTransformation extends EmptyEObjectMappingTransformation {

	private static val Logger logger = Logger.getLogger(OperationSignatureMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return OperationSignatureMappingTransformation
	}

	/**
	 * creates an IntetfaceMethod for each signature that is created
	 * Assumptions:
	 * 		- the OperationSignature does not have an return type yet
	 * 		- the OperationSignature does not have any parameters yet
	 */
	override createEObject(EObject eObject) {
		val OperationSignature opSig = eObject as OperationSignature
		if (null != opSig.returnType__OperationSignature || !opSig.parameters__OperationSignature.nullOrEmpty) {
			throw new RuntimeException(
				"Operation signature either has return type or parameters directly after " +
					"creating - should not happen" + opSig)
		}
		var InterfaceMethod ifMethod = MembersFactory.eINSTANCE.createInterfaceMethod
		ifMethod.setName(opSig.entityName)
		ifMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid)
		return ifMethod.toArray
	}

	/**
	 * Returns all corresponding eObjects that are corresponding to the operation signature,
	 * e.g the InterfaceMethod --> the deletion has do be done in the next step
	 */
	override removeEObject(EObject eObject) {
		val correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		correspondenceInstance.removeAllCorrespondences(eObject)
		return correspondingEObjects

	}

	/**
	 * called when a parameter was added
	 */
	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingParameter) {
		val Set<InterfaceMethod> jaMoPPIfMethods = correspondenceInstance.
			getCorrespondingEObjectsByType(affectedEObject, InterfaceMethod)
		if (jaMoPPIfMethods.nullOrEmpty) {
			logger.warn(
				"No corresponding InterfaceMethod was found for OperationSignature: " + affectedEObject +
					" Returning empty transformation result")
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val parrentCorrespondence = correspondenceInstance.getAllCorrespondences(affectedEObject)
		for (interfaceMethod : jaMoPPIfMethods) {
			interfaceMethod.parameters.add(index, newCorrespondingParameter.get(0) as Parameter)
			correspondenceInstance.createAndAddEObjectCorrespondence(newValue, newCorrespondingParameter.get(0),
				parrentCorrespondence.get(0))
		}
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(jaMoPPIfMethods)
	}

	/**
	 * called when a parameter was removed
	 */
	override deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		val jaMoPPIfMethod = correspondenceInstance.getAllCorrespondingEObjects(affectedEObject)
		for (correspondingEObject : oldCorrespondingEObjectsToDelete) {
			EcoreUtil.delete(correspondingEObject, true)
		}
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(jaMoPPIfMethod)
	}

	/**
	 * update the name attribute
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		var Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedAttribute, featureCorrespondenceMap,
				correspondenceInstance);
		if (correspondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult;
		}
		return PCM2JaMoPPUtils.updateNameAttribute(correspondingEObjects, newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance)
	}

	/**
	 * set correspondence for the name attribute
	 */
	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

}
