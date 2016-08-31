package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.transformations

import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.packagemapping.javaimplementation.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils

class OperationProvidedRoleMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static final Logger logger = Logger.getLogger(OperationProvidedRoleMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return OperationProvidedRole;
	}

	override setCorrespondenceForFeatures() {
	}

	/**
	 * called when a Operation provided role has been created.
	 * Look for the main class implementing the component of the operation provided role and add an 
	 * implements relation to the Interface that corresponds to the OperationInterface of the OperationProvidedRole.
	 */
	override createEObject(EObject eObject) {
		val OperationProvidedRole opr = eObject as OperationProvidedRole
		val opInterface = opr.providedInterface__OperationProvidedRole
		val providingEntity = opr.providingEntity_ProvidedRole
		if (null == opInterface) {
			logger.warn("operation interface is null. Can not synchronize creation of opeation provided role: " + opr)
			return null
		}
		if (null == providingEntity) {
			logger.warn("Basic component is null. Can not synchronize creation of opeation provided role: " + opr)
			return null
		}
		val jaMoPPClass = correspondenceModel.
			getCorrespondingEObjectsByType(providingEntity, Class).claimOne
		val jaMoPPInterface = correspondenceModel.
			getCorrespondingEObjectsByType(opInterface, Interface).claimOne
		val namespaceClassifierRef = PCM2JaMoPPUtils.createNamespaceClassifierReference(jaMoPPInterface)
		jaMoPPClass.implements.add(namespaceClassifierRef)
		val classifierImport = PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(jaMoPPClass, jaMoPPInterface)
		return #[namespaceClassifierRef, classifierImport]
	}

	override removeEObject(EObject eObject) {
		return null
	}

	/**
	 * called when a operation provided role has been changed.
	 * If the implementing component or the interface has been changed (which is always the case):
	 * Delete the old OperationProvidedRole if oldValue is not null and create a new one
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		if (oldValue == newValue) {
			logger.debug("oldValue == newValue (value: )" + oldValue + ". Nothing has to be done here.")
			return new TransformationResult
		}
		if (null != oldValue) {
			val EObject[] oldEObjects = removeEObject(affectedEObject)
			for (oldEObject : oldEObjects) {
				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(oldEObject.toSet)
				EcoreUtil.remove(oldEObject)
			}
 	 	}
 	 	// if the new value already has a correspondence we do not need to create newEObjects
		if (!this.correspondenceModel.hasCorrespondences(newValue.toList)) {
			val EObject[] newEObjects = createEObject(affectedEObject)
			if (null != newEObjects) {
				for (newEObject : newEObjects) {
					correspondenceModel.createAndAddCorrespondence(newEObject.toList, affectedEObject.toList)
				}
			}
			return new TransformationResult
		}
	}

	/**
	 * called when the name or ID of a OperationProvidedRole has been changed - has no effect to the code
	 * but must be overwritten here in order to not get an exception from the super class
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
	}

}
