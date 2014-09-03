package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMMappingTransformationBase
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.Set
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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import org.emftext.language.java.members.InterfaceMethod

class OperationInterfaceMappingTransformation extends JaMoPPPCMMappingTransformationBase {

	val private static Logger logger = Logger.getLogger(OperationInterfaceMappingTransformation.simpleName)

	override Class<?> getClassOfMappedEObject() {
		return OperationInterface
	}

	override void setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
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

	/**
	 * Called when a signature was added.
	 * Add Method (which is in newCorrespondingEObjects) to the Interface corresponding to the OperationInterface 
	 */
	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue,
			int index, EObject[] newMethods) {
		if(newMethods.nullOrEmpty || (false == newMethods.forall[method| method instanceof InterfaceMethod])){
			throw new RuntimeException("unexpeceted value in newMethods parameter " + newMethods.size + " (expected 1):" + newMethods)
		}
		val Interface jaMoPPIf = correspondenceInstance.getCorrespondingEObjectsByType(affectedEObject, Interface).get(0)
		for(eObject : newMethods){
			val InterfaceMethod newMethod = eObject as InterfaceMethod;
			jaMoPPIf.methods.add(index, newMethod);
		}
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(jaMoPPIf.toArray)
	}
	
	/**
	 * called when a signature was removed.
	 * Remove method from corresponding interface
	 */
	override deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldMethods) {
		if(oldMethods.nullOrEmpty || (false == oldMethods.forall[method|method instanceof InterfaceMethod])){
			throw new RuntimeException("unexpeceted value in oldMethods parameter " + oldMethods.size + " (expected 1):" + oldMethods)
		}
		val Interface jaMoPPIf = correspondenceInstance.getCorrespondingEObjectsByType(affectedEObject, Interface).get(0)
		for(eObject : oldMethods){
			val InterfaceMethod oldMethod = eObject as InterfaceMethod;
			jaMoPPIf.methods.remove(oldMethod)
		}
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(jaMoPPIf.toArray)
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

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		var Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(eObject, affectedAttribute, featureCorrespondenceMap, correspondenceInstance);
		if (correspondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult;
		}
		return PCM2JaMoPPUtils.updateNameAttribute(correspondingEObjects, newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance)

	//TODO: Code refactoring anstossen
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

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method createNonRootEObjectSingle should not be called for " +
				OperationInterfaceMappingTransformation.simpleName + " transformation")
		return null
	}
}
