package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.transformations.system

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.packagemapping.javaimplementation.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.parameters.Parameter
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
import edu.kit.ipd.sdq.vitruvius.framework.command.TransformationResult

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils

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
			val field = correspondenceModel.getCorrespondingEObjectsByType(assemblyContext, Field).claimOne
			val parameters = correspondenceModel.getCorrespondingEObjectsByType(
				requiredDelegationConnector.innerRequiredRole_RequiredDelegationConnector, Parameter)
			if (parameters.nullOrEmpty) {
				return null
			}
			val constructorCallForField = correspondenceModel.
				getCorrespondingEObjectsByType(assemblyContext, NewConstructorCall).claimOne
			var parametersToUse = emptyList
			val correspondingConstructors = correspondenceModel.
				getCorrespondingEObjectsByType(assemblyContext, Constructor)
			if (null != correspondingConstructors) {
				parametersToUse = correspondingConstructors.get(0).parameters
			}
			val fieldsToUseAsPossibleParameters = (field.containingConcreteClassifier as Class).fields

			PCM2JaMoPPUtils.updateArgumentsOfConstructorCall(field, fieldsToUseAsPossibleParameters, parametersToUse,
				constructorCallForField)
			return field.toList
		} catch (RuntimeException re) {
			logger.trace(
				"Could not generate a corresponding object for required delegation role" + requiredDelegationConnector +
					" Reason:" + re)
		}
		return null
	}

	override removeEObject(EObject eObject) {
		return null
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return new TransformationResult
	}
	
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return new TransformationResult
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return new TransformationResult
	}

}
