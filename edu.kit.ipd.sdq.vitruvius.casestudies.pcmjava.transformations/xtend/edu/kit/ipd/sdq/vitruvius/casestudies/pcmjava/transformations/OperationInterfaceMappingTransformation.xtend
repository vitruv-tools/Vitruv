package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package

class OperationInterfaceMappingTransformation extends EObjectMappingTransformation{
	
	override Class<?> getClassOfMappedEObject() {
		return typeof(OperationInterface)
	}
	
	override EObject addEObject(EObject eObject){
		val OperationInterface operationInterface = eObject as OperationInterface
		var Interface correspondingInterface = ClassifiersFactory.eINSTANCE.createInterface
		correspondingInterface.setName(operationInterface.entityName)
		
		var CompilationUnit correspondingCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
		correspondingCompilationUnit.name = operationInterface.entityName + ".java"
		correspondingCompilationUnit.classifiers.add(correspondingInterface)
		
		var Package containingPackage = null
		containingPackage.compilationUnits.add(correspondingCompilationUnit)		
		
		return null	
	}
	
	override EObject removeEObject(EObject eObject){
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