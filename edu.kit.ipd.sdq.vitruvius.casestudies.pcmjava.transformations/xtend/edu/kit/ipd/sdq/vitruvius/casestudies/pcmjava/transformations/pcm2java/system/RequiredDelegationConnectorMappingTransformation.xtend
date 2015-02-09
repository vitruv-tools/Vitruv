package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system

import de.uka.ipd.sdq.pcm.core.composition.RequiredDelegationConnector
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.parameters.Parameter

class RequiredDelegationConnectorMappingTransformation extends EmptyEObjectMappingTransformation {

	private val Logger logger = Logger.getLogger(RequiredDelegationConnectorMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return RequiredDelegationConnector
	}

	override setCorrespondenceForFeatures() {
	}

	/**
	 * called when a RequiredDelegationConnector has been created.
	 * update the constructor call for the field
	 */
	override createEObject(EObject eObject) {
		val requiredDelegationConnector = eObject as RequiredDelegationConnector
		val assemblyContext = requiredDelegationConnector.assemblyContext_RequiredDelegationConnector
		try {
			val field = correspondenceInstance.claimUniqueCorrespondingEObjectByType(assemblyContext, Field)
			val parameters = correspondenceInstance.getCorrespondingEObjectsByType(
				requiredDelegationConnector.innerRequiredRole_RequiredDelegationConnector, Parameter)
			if (parameters.nullOrEmpty) {
				return null
			}
			val constructorCallForField = correspondenceInstance.
				claimUniqueCorrespondingEObjectByType(assemblyContext, NewConstructorCall)
			var parametersToUse = emptyList
			val correspondingConstructors = correspondenceInstance.
				getCorrespondingEObjectsByType(assemblyContext, Constructor)
			if (null != correspondingConstructors) {
				parametersToUse = correspondingConstructors.get(0).parameters
			}
			val fieldsToUseAsPossibleParameters = (field.containingConcreteClassifier as Class).fields

			PCM2JaMoPPUtils.updateArgumentsOfConstructorCall(field, fieldsToUseAsPossibleParameters, parametersToUse,
				constructorCallForField)
			return field.toArray
		} catch (RuntimeException re) {
			logger.trace(
				"Could not generate a corresponding object for required delegation role" + requiredDelegationConnector +
					" Reason:" + re)
		}
		return null
	}

	override removeEObject(EObject eObject) {
		return correspondenceInstance.getAllCorrespondingEObjects(eObject)
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return TransformationUtils.createEmptyTransformationChangeResult
	}

}
