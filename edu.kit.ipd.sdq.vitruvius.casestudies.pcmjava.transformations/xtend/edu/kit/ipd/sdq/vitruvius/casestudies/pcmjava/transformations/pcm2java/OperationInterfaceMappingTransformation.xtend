package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import java.util.Map
import org.eclipse.emf.common.util.BasicEList 
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.JavaPackage
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.emftext.language.refactoring.rolemapping.RoleMapping
import org.emftext.refactoring.interpreter.IRefactorer
import org.emftext.refactoring.interpreter.RefactorerFactory
import org.emftext.refactoring.registry.rolemapping.IRoleMappingRegistry
import org.modelrefactoring.jamopp.test.valueprovider.TestValueProviderFactory

class OperationInterfaceMappingTransformation extends edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation {

	override Class<?> getClassOfMappedEObject() {
		return typeof(OperationInterface)
	}

	override void setCorrespondenceForFeatures() {
		var opInterfaceNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("entityName")].iterator.next
		var interfaceNameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("name")].iterator.next
		featureCorrespondenceMap.put(opInterfaceNameAttribute, interfaceNameAttribute)
	}

	override addEObject(EObject eObject) {
		val OperationInterface operationInterface = eObject as OperationInterface

		// create Interface and compilation unit that contains the java interface
		var Interface correspondingInterface = ClassifiersFactory.eINSTANCE.createInterface
		correspondingInterface.setName(operationInterface.entityName)
		var CompilationUnit correspondingCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
		correspondingCompilationUnit.name = operationInterface.entityName + ".java"
		correspondingCompilationUnit.classifiers.add(correspondingInterface)

		// add compilation unit to package, which correspondent to the repository-package
		val containingPackage = correspondenceInstance.
			claimCorrespondingEObjectByTypeIfUnique(operationInterface.repository__Interface, Package)
		containingPackage.compilationUnits.add(correspondingCompilationUnit)
		correspondingCompilationUnit.namespaces.addAll(containingPackage.namespaces)

		//add new correspondence to correspondenceInstance
		val parrentCorrespondence = correspondenceInstance.
			getCorrespondeceForEObjectIfUnique(operationInterface.repository__Interface);
		val EObjectCorrespondence eObjectCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
		eObjectCorrespondence.setElementA(operationInterface)
		eObjectCorrespondence.setElementB(correspondingInterface)
		eObjectCorrespondence.setParent(parrentCorrespondence)
		correspondenceInstance.addSameTypeCorrespondence(eObjectCorrespondence)
		val EObjectCorrespondence eObjectCorrespondence4CompilationUnit = CorrespondenceFactory.eINSTANCE.
			createEObjectCorrespondence
		eObjectCorrespondence4CompilationUnit.setElementA(operationInterface)
		eObjectCorrespondence4CompilationUnit.setElementB(correspondingCompilationUnit)
		eObjectCorrespondence.setParent(parrentCorrespondence)
		correspondenceInstance.addSameTypeCorrespondence(eObjectCorrespondence4CompilationUnit)
		return correspondingCompilationUnit
	}

	override removeEObject(EObject eObject) {
		val OperationInterface operationInterface = eObject as OperationInterface
		val correspondingObjects = correspondenceInstance.claimCorrespondingEObjects(operationInterface)
		for (correspondingObject : correspondingObjects) {
			EcoreUtil.remove(correspondingObject)
		}
		correspondenceInstance.removeAllCorrespondingInstances(operationInterface)
		return null 
	}

	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val OperationInterface operationInterface = eObject as OperationInterface
		val EStructuralFeature affectedInterfaceFeature = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
		
		val jaMoPPInterfaceCompilationUnit = correspondenceInstance.
			claimCorrespondingEObjectByTypeIfUnique(operationInterface, CompilationUnit)
		jaMoPPInterfaceCompilationUnit.eSet(affectedInterfaceFeature, newValue + ".java")
				val Interface jaMoPPInterface = correspondenceInstance.claimCorrespondingEObjectByTypeIfUnique(operationInterface, Interface)
		
		val Map<String, RoleMapping> roleMappings = IRoleMappingRegistry.INSTANCE.
			getRoleMappingsForUri(JavaPackage.eNS_URI);
		jaMoPPInterface.eSet(affectedInterfaceFeature, newValue )
		val IRefactorer refactorer = RefactorerFactory.eINSTANCE.getRefactorer(jaMoPPInterface.eResource,
			roleMappings.get("RenameElement"))
		
		val jaMoPPValueProviderFactory = new TestValueProviderFactory()
		jaMoPPValueProviderFactory.setNewMethodName(newValue.toString)
		val BasicEList<EObject> elementToRefactor = new BasicEList<EObject>();
		elementToRefactor.add(jaMoPPInterface)
		refactorer.setValueProviderFactory(jaMoPPValueProviderFactory)
		refactorer.setInput(elementToRefactor)
		val EObject refactoredEObj = refactorer.refactor()
		for( resourceToSave : refactorer.resourcesToSave){
			resourceToSave.save(null)
		}
		return refactoredEObj
	}

	override updateEReference(EObject eObject, EReference affectedEReference, Object newValue) {
		val OperationInterface operationInterface = eObject as OperationInterface
		return null
	} 

	override updateEContainmentReference(EObject eObject, EReference afffectedEReference, Object newValue) {

		return null
	}

}
