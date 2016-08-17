package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.system

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import java.util.Collection
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.references.MethodCall
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.references.SelfReference
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.statements.StatementsFactory
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.PCMJaMoPPUtils

class ProvidedDelegationConnectorMappingTransformation extends EmptyEObjectMappingTransformation {

	private val Logger logger = Logger.getLogger(ProvidedDelegationConnectorMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return ProvidedDelegationConnector
	}

	override setCorrespondenceForFeatures() {
	}

	/**
	 * called when a ProvidedDelegationConnector has been created.
	 * Delegates the call of the provided interface to the methods of the field of the assembly context
	 */
	override createEObject(EObject eObject) {
		val providedDelegationRole = eObject as ProvidedDelegationConnector
		val assemblyContext = providedDelegationRole.assemblyContext_ProvidedDelegationConnector
		val operationInterface = providedDelegationRole.innerProvidedRole_ProvidedDelegationConnector.
			providedInterface__OperationProvidedRole
		try {
			val field = blackboard.correspondenceInstance.getCorrespondingEObjectsByType(assemblyContext, Field).claimOne
			val Set<EObject> newEObjects = newHashSet()
			for (opSig : operationInterface.signatures__OperationInterface) {

				// get corresponding (interface) method and find or create a similar class method in the current class
				val correspondingMethods = blackboard.correspondenceInstance.
					getCorrespondingEObjectsByType(opSig, Method)
				for (correspondingMethod : correspondingMethods) {
					val methodInClassifier = findOrCreateMethodDeclarationInClassifier(correspondingMethod,
						field.containingConcreteClassifier)

					// create call statement
					val callMethodInFieldStatement = createCallMethodStatement(methodInClassifier, field,
						methodInClassifier.parameters)
					methodInClassifier.statements.add(callMethodInFieldStatement)
					newEObjects.add(callMethodInFieldStatement)
				}
			}
			return newEObjects
		} catch (RuntimeException re) {
			logger.trace("Could not find field for assembly Context " + assemblyContext.entityName + " Reason:" + re)
			return null
		}
	}

	override removeEObject(EObject eObject) {
		return null
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		logger.warn("method " + new Object() {
		}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
			"transformation")
		return new TransformationResult
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn("method " + new Object() {
		}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
			"transformation")
		return new TransformationResult
	}

	/**
	 * Create a method call for the method within the field
	 */
	private def createCallMethodStatement(ClassMethod methodToCall, Field field, Collection<Parameter> parameters) {
		val ExpressionStatement expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val SelfReference selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		selfReference.self = LiteralsFactory.eINSTANCE.createThis
		expressionStatement.expression = selfReference
		val IdentifierReference identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifierReference.target = field
		selfReference.next = identifierReference
		val MethodCall methodCall = ReferencesFactory.eINSTANCE.createMethodCall
		methodCall.target = methodToCall
		for (parameter : parameters) {
			val IdentifierReference paramRef = ReferencesFactory.eINSTANCE.createIdentifierReference
			paramRef.target = parameter
			methodCall.arguments.add(paramRef)
		}
		identifierReference.next = methodCall
		return expressionStatement
	}

	private def ClassMethod findOrCreateMethodDeclarationInClassifier(Method method, ConcreteClassifier classifier) {

		for (classifierMethod : classifier.methods) {
			if (PCMJaMoPPUtils.hasSameSignature(classifierMethod, method) && classifierMethod instanceof ClassMethod) {
				return classifierMethod as ClassMethod;
			}
		}

		// no method found: create it in classifier
		val ClassMethod classMethod = PCM2JaMoPPUtils.createClassMethod(method, true)
		return classMethod
	}
	
}
