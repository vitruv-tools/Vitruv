package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.DefaultEObjectMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils

class TypeReferenceMappingTransformation extends DefaultEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return TypeReference
	}

	/**
	 * called when a type Reference has been created.
	 * If it is a NamespaceClassifierReference of a ClassifierReference check whether 
	 * the reference is in the implements of a class. If yes--> check whether the interface has a corresponding 
	 * OperationInterface and the class has a corresponding component--> if yes: create an OperationProvidedRole
	 */
	override createEObject(EObject eObject) {
		if (implementsChanged(eObject)) {

			val jaMoPPClass = eObject.eContainer as Class

			var interfaceClassifier = JaMoPP2PCMUtils.getTargetClassifierFromImplementsReferenceAndNormalizeURI(
				eObject as TypeReference)
			if(null == interfaceClassifier){
				return null
			}
			val correspondingInterfaces = blackboard.correspondenceInstance.getCorrespondingEObjectsByType(interfaceClassifier,
				OperationInterface)
			if (correspondingInterfaces.nullOrEmpty) {
				return null
			}
			val operationInterface = correspondingInterfaces.get(0)
			val correspondingBasicComponents = blackboard.correspondenceInstance.
				getCorrespondingEObjectsByType(jaMoPPClass, BasicComponent)
			if (correspondingBasicComponents.nullOrEmpty) {
				return null
			}
			val basicComponent = correspondingBasicComponents.get(0)
			return createOperationProvidedRole(basicComponent, operationInterface).toArray
		}
		return null
	}

	/**
	 * if a implements reference has been removed remove the corresponding objects as well
	 */
	override removeEObject(EObject eObject) {
		if (implementsChanged(eObject)) {
			TransformationUtils.removeCorrespondenceAndAllObjects(eObject, blackboard)
		}
		return null
	}

	def private boolean implementsChanged(EObject eObject) {
		if (eObject instanceof NamespaceClassifierReference || eObject instanceof ClassifierReference) {
			val container = eObject.eContainer
			if (container instanceof Class) {
				val jaMoPPClass = eObject.eContainer as Class
				val referenceIsInImplements = jaMoPPClass.implements.contains(eObject)
				if (referenceIsInImplements) {
					return true
				}
			}
		}
		return false
	}

	def private createOperationProvidedRole(BasicComponent basicComponent, OperationInterface opInterface) {
		val operationProvidedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole
		operationProvidedRole.providedInterface__OperationProvidedRole = opInterface
		operationProvidedRole.providingEntity_ProvidedRole = basicComponent
		operationProvidedRole.entityName = basicComponent.entityName + "_provides_" + opInterface.entityName
		return operationProvidedRole
	}

}