package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.TransformationUtils

class OperationInterfaceMappingTransformation extends edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation {

	override Class<?> getClassOfMappedEObject() {
		return typeof(OperationInterface)
	}

	override void setCorrespondenceForFeatures() {
		var opInterfaceNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::PCM_ATTRIBUTE_ENTITY_NAME)].iterator.next
		var interfaceNameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::JAMOPP_ATTRIBUTE_NAME)].iterator.next
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
			claimUniqueCorrespondingEObjectByType(operationInterface.repository__Interface, Package)
		containingPackage.compilationUnits.add(correspondingCompilationUnit)
		correspondingCompilationUnit.namespaces.addAll(containingPackage.namespaces)

		//add new correspondence to correspondenceInstance
		val parrentCorrespondence = correspondenceInstance.
			claimUniqueOrNullCorrespondenceForEObject(operationInterface.repository__Interface);
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
		return TransformationUtils.createTransformationChangeResultForNewRootEObjects(correspondingCompilationUnit.toArray)
	}

	override removeEObject(EObject eObject) {
		val OperationInterface operationInterface = eObject as OperationInterface
		val correspondingObjects = correspondenceInstance.claimCorrespondingEObjects(operationInterface)
		for (correspondingObject : correspondingObjects) {
			//TODO: check wheather the CompilationUnit is deleted
			EcoreUtil.remove(correspondingObject)
		}
		correspondenceInstance.removeAllCorrespondences(operationInterface)
		return null 
	}

	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val OperationInterface operationInterface = eObject as OperationInterface
		val EStructuralFeature affectedInterfaceFeature = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
		
		val jaMoPPInterfaceCompilationUnit = correspondenceInstance.
			claimUniqueCorrespondingEObjectByType(operationInterface, CompilationUnit)
		val oldCompilationUnit = EcoreUtil.copy(jaMoPPInterfaceCompilationUnit)
		jaMoPPInterfaceCompilationUnit.eSet(affectedInterfaceFeature, newValue + ".java")
		val Interface jaMoPPInterface = correspondenceInstance.claimUniqueCorrespondingEObjectByType(operationInterface, Interface)
		jaMoPPInterface.eSet(affectedInterfaceFeature, newValue)
		//TODO: Code refactoring anstossen
		return TransformationUtils.createTransformationChangeResult(jaMoPPInterfaceCompilationUnit.toArray, oldCompilationUnit.toArray, null)
/* 		val Map<String, RoleMapping> roleMappings = IRoleMappingRegistry.INSTANCE.
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
		return refactoredEObj.toArray*/
	}

	override updateEReference(EObject eObject, EReference affectedEReference, Object newValue) {
		val OperationInterface operationInterface = eObject as OperationInterface
		return null
	} 

	override updateEContainmentReference(EObject eObject, EReference afffectedEReference, Object newValue) {

		return null
	}

}
