package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Method
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.DefaultEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils

class SEFFMappingTransformation extends DefaultEObjectMappingTransformation {

	val private static Logger logger = Logger.getLogger(SEFFMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return ResourceDemandingSEFF
	}

	override setCorrespondenceForFeatures() {
	}

	/**
	 * create class method in the basic component in which the seff is contained.
	 */
	override createEObject(EObject eObject) {
		val seff = eObject as ResourceDemandingSEFF
		return seff.checkSEFFAndCreateCorrespondences
	}

	override removeEObject(EObject eObject) {
		return null
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val transformationResult = new TransformationResult
		// a RDSEFF does not have an entity name - hence we do nothing here 
		return transformationResult
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val transformationResult = new TransformationResult
		if (oldValue == newValue) {
			return transformationResult
		}
		val signatureAffected = oldValue instanceof OperationSignature || newValue instanceof OperationSignature
		if (!signatureAffected) {
			return transformationResult
		}
		removeEObject(affectedEObject)

		val affectedSEFF = affectedEObject as ResourceDemandingSEFF
		val newEObjects = affectedSEFF.checkSEFFAndCreateCorrespondences
		for (newCorrespondingEObject : newEObjects) {
			blackboard.correspondenceInstance.createAndAddCorrespondence(affectedSEFF, newCorrespondingEObject)
		}
		transformationResult
	}

	private def EObject[] checkSEFFAndCreateCorrespondences(ResourceDemandingSEFF seff) {
		val basicComponent = seff.basicComponent_ServiceEffectSpecification
		if (null == basicComponent) {
			return null
		}
		val signature = seff.describedService__SEFF
		if (null == signature) {
			return null
		}
		val sigIsOpSig = signature instanceof OperationSignature
		if (!sigIsOpSig) {
			return null
		}
		val correspondingClasses = blackboard.correspondenceInstance.getCorrespondingEObjectsByType(basicComponent,
			ConcreteClassifier)
		if (!correspondingClasses.isNullOrEmpty) {
			// create method
			val correspondingMethods = blackboard.correspondenceInstance.
				getCorrespondingEObjectsByType(signature, Method)
			if (correspondingMethods.nullOrEmpty) {
				logger.info("No corresponding method for seffs operation signature " + signature + " found")
				return null
			}
			val correspondingInterfaceMethod = correspondingMethods.get(0)
			val ClassMethod classMethod = PCM2JaMoPPUtils.createClassMethod(correspondingInterfaceMethod, true)
			val correspondingClass = correspondingClasses.get(0)
			var ClassMethod correspondinClassgMethod = correspondingClass.findMethodInClass(classMethod)
			if (null == correspondinClassgMethod) {
				correspondingClass.members.add(classMethod)
				correspondinClassgMethod = classMethod
			}else{
				correspondinClassgMethod.name = correspondingInterfaceMethod.name
			}
			return correspondinClassgMethod.toList
		} else {
			// warn user
			userInteracting.showMessage(UserInteractionType.MODELESS,
				"Could not find a corresponding class for the SEFF's component " + basicComponent +
					". No corresponding method for the SEFF created.")
				}
				null
			}

			def ClassMethod findMethodInClass(ConcreteClassifier concreteClassifier, ClassMethod method) {
				for (Method currentMethod : concreteClassifier.methods) {
					if (currentMethod instanceof ClassMethod && currentMethod.name.equals(method.name) &&
						currentMethod.typeParameters.size == method.typeParameters.size) {
						// todo: finish check by comparing type reference and type of each parameter 
						return currentMethod as ClassMethod
					}
				}
				null
			}

		}