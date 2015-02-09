package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system

import de.uka.ipd.sdq.pcm.core.composition.ProvidedDelegationConnector
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.JaMoPP2PCMUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.Collection
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.members.Method
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.references.MethodCall
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.references.SelfReference
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.TypeReference

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
			val field = correspondenceInstance.claimUniqueCorrespondingEObjectByType(assemblyContext, Field)
			val Set<EObject> newEObjects = newHashSet()
			for (opSig : operationInterface.signatures__OperationInterface) {

				//get corresponding (interface) method and find or create a similar class method in the current class
				val correspondingMethods = correspondenceInstance.claimCorrespondingEObjectsByType(opSig, Method)
				for (correspondingMethod : correspondingMethods) {
					val methodInClassifier = findOrCreateMethodDeclarationInClassifier(correspondingMethod,
						field.containingConcreteClassifier)

					//create call statement
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
	
	override removeEObject(EObject eObject){
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
			if (classifierMethod.hasSameSignature(method) && classifierMethod instanceof ClassMethod) {
				return classifierMethod as ClassMethod;
			}
		}

		//no method found: create it in classifier
		val ClassMethod classMethod = MembersFactory.eINSTANCE.createClassMethod
		classMethod.name = method.name
		classMethod.typeReference = EcoreUtil.copy(method.typeReference)
		classMethod.modifiers.addAll(EcoreUtil.copyAll(classMethod.modifiers))
		classMethod.parameters.addAll(EcoreUtil.copyAll(method.parameters))
		return classMethod
	}

	/**
	 * Signatures are considered equal if methods have the same name, the same parameter types and the same return type
	 * We do not consider modifiers (e.g. public or private here)
	 */
	private def boolean hasSameSignature(Method method1, Method method2) {
		if (method1 == method2) {
			return true
		}
		if (!method1.name.equals(method1.name)) {
			return false
		}
		if (!method1.typeReference.hasSameTargetReference(method2.typeReference)) {
			return false
		}
		if (method1.parameters.size != method2.parameters.size) {
			return false
		}
		var int i = 0
		for (param1 : method1.parameters) {
			if (!hasSameTargetReference(param1.typeReference, method2.parameters.get(i).typeReference)) {
				return false
			}
			i++
		}
		return true
	}

	private def boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
		if (reference1 == reference2 || reference1.equals(reference2)) {
			return true
		}
		val target1 = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference1)
		val target2 = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference2)
		return target1 == target2 || target1.equals(target2)
	}

}
