package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.OperationSignature
import de.uka.ipd.sdq.pcm.seff.ResourceDemandingSEFF
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.DefaultEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.ModifiersFactory

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
		return correspondenceInstance.getAllCorrespondences(eObject)
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		if (oldValue == newValue) {
			return tcr
		}
		val signatureAffected = oldValue instanceof OperationSignature || newValue instanceof OperationSignature
		if (!signatureAffected) {
			return tcr
		}
		val eObjectsToRemove = removeEObject(affectedEObject)
		if (!eObjectsToRemove.nullOrEmpty) {
			for (eObjectToRemove : eObjectsToRemove) {
				val oldTUID = correspondenceInstance.calculateTUIDFromEObject(eObjectToRemove)
				tcr.addCorrespondenceToDelete(correspondenceInstance, oldTUID)
				EcoreUtil.remove(eObjectToRemove)
			}
		}
		val affectedSEFF = affectedEObject as ResourceDemandingSEFF
		val newEObjects = affectedSEFF.checkSEFFAndCreateCorrespondences
		var Correspondence parrentCorrespondence = null
		val parrentCorrespondences = correspondenceInstance.getAllCorrespondences(
			affectedSEFF.basicComponent_ServiceEffectSpecification)
		if (!parrentCorrespondences.nullOrEmpty) {
			parrentCorrespondence = parrentCorrespondences.get(0)
		}
		for (newCorrespondingEObject : newEObjects) {
			tcr.addNewCorrespondence(correspondenceInstance, affectedSEFF, newCorrespondingEObject,
				parrentCorrespondence)
		}
		return tcr
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
		val correspondingClasses = correspondenceInstance.getCorrespondingEObjectsByType(basicComponent,
			ConcreteClassifier)
		if (!correspondingClasses.isNullOrEmpty) {
			// create method
			val correspondingMethods = correspondenceInstance.getCorrespondingEObjectsByType(signature, Method)
			if (correspondingMethods.nullOrEmpty) {
				logger.info("No corresponding method for seffs operation signature " + signature + " found")
				return null
			}
			val correspondingInterfaceMethod = correspondingMethods.get(0)
			val ClassMethod classMethod = MembersFactory.eINSTANCE.createClassMethod
			classMethod.name = correspondingInterfaceMethod.name
			classMethod.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
			classMethod.typeReference = EcoreUtil.copy(correspondingInterfaceMethod.typeReference)
			classMethod.parameters.addAll(EcoreUtil.copyAll(correspondingInterfaceMethod.parameters))
			val correspondingClass = correspondingClasses.get(0)
			var ClassMethod correspondinClassgMethod = correspondingClass.findMethodInClass(classMethod)
			if (null == correspondinClassgMethod) {
				correspondingClass.members.add(classMethod)
				correspondinClassgMethod = classMethod
			}
			return correspondinClassgMethod.toArray
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