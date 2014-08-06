package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMMappingTransformationBase
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package

class OperationInterfaceMappingTransformation extends JaMoPPPCMMappingTransformationBase {

	val private static Logger logger = Logger.getLogger(OperationInterfaceMappingTransformation.simpleName)

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

	override createEObject(EObject eObject) {
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

		return #[correspondingInterface, correspondingCompilationUnit]
	}

	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		return null;
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
	
	override deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		if(!featureCorrespondenceMap.containsKey(affectedAttribute)){
			logger.info("no feature correspondence found for affected Attribute: " + affectedAttribute)
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		var correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		if(null == correspondingEObjects){
			logger.info("No corresponding objects found for " + eObject)
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		
		var transformationChangeResult = new TransformationChangeResult
		for(EObject correspondingObject : correspondingEObjects){
			// compilationUnit was renamed: Delete old one and save new one
			val oldObject = EcoreUtil.copy(correspondingObject)
			if(correspondingObject instanceof CompilationUnit){
				transformationChangeResult.existingObjectsToDelete.add(oldObject)
			}
			correspondingObject.eClass.eSet(featureCorrespondenceMap.get(affectedAttribute), newValue)
			correspondenceInstance.update(oldObject, correspondingObject)
			if(correspondingObject instanceof CompilationUnit){
				transformationChangeResult.newRootObjectsToSave.add(correspondingObject)
			}
//			else{
//				transformationChangeResult.existingObjectsToSave.add(correspondingObject)
//			}
		}
		//TODO: Code refactoring anstossen
		return transformationChangeResult		
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

	
	
	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue, EObject[] newCorrespondingEObjects) {
		logger.warn("method createNonRootEObjectSingle should not be called for " + OperationInterfaceMappingTransformation.simpleName + " transformation")
		return null
	}
}
