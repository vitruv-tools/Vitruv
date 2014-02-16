package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package

class OperationInterfaceMappingTransformation extends EObjectMappingTransformation {

	override Class<?> getClassOfMappedEObject() {
		return typeof(OperationInterface)
	}
	
	override void setCorrespondenceForFeatures(){
		
	}

	override EObject addEObject(EObject eObject) {
		val OperationInterface operationInterface = eObject as OperationInterface
		
		// create Interface and compilation unit that contains the java interface
		var Interface correspondingInterface = ClassifiersFactory.eINSTANCE.createInterface
		correspondingInterface.setName(operationInterface.entityName)
		var CompilationUnit correspondingCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
		correspondingCompilationUnit.name = operationInterface.entityName + ".java"
		correspondingCompilationUnit.classifiers.add(correspondingInterface)

		// add compilation unit to package, which correspondent to the repository-package 
		val containingPackage = TransformationUtils.findCorrespondingEObjectIfUnique(correspondenceInstance,
			operationInterface.repository__Interface) as Package
		containingPackage.compilationUnits.add(correspondingCompilationUnit)

		//add new correspondence to correspondenceInstance
		val parrentCorrespondence = TransformationUtils.
			findParentCorrespondenceForEObject(correspondenceInstance, operationInterface.repository__Interface)
		val EObjectCorrespondence eObjectCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
		eObjectCorrespondence.setElementA(operationInterface)
		eObjectCorrespondence.setElementB(correspondingInterface)
		eObjectCorrespondence.setParent(parrentCorrespondence)
		correspondenceInstance.addCorrespondence(eObjectCorrespondence)
		val EObjectCorrespondence eObjectCorrespondence4CompilationUnit = CorrespondenceFactory.eINSTANCE.
			createEObjectCorrespondence
		eObjectCorrespondence4CompilationUnit.setElementA(operationInterface)
		eObjectCorrespondence4CompilationUnit.setElementB(correspondingCompilationUnit)
		eObjectCorrespondence.setParent(parrentCorrespondence)
		correspondenceInstance.addCorrespondence(eObjectCorrespondence4CompilationUnit)
		return correspondingInterface
	}

	override EObject removeEObject(EObject eObject) {
		val OperationInterface operationInterface = eObject as OperationInterface
		return null
	}

	override EObject updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val OperationInterface operationInterface = eObject as OperationInterface
		return null
	}

	override EObject updateEReference(EObject eObject, EReference affectedEReference, EObject newValue) {
		val OperationInterface operationInterface = eObject as OperationInterface
		return null
	}

	override EObject updateEContainmentReference(EObject eObject, EReference afffectedEReference, EObject newValue) {

		return null
	}

}
