package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.ArrayList
import java.util.List
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
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.modifiers.ModifiersFactory

class OperationInterfaceMappingTransformation extends EmptyEObjectMappingTransformation {

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
		correspondingInterface.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		var CompilationUnit correspondingCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
		correspondingCompilationUnit.name = operationInterface.entityName + "." +
			PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION
		correspondingCompilationUnit.classifiers.add(correspondingInterface)

		// add compilation unit to contracts package, which correspondent to the repository
		val packages = correspondenceInstance.getCorrespondingEObjectsByType(operationInterface.repository__Interface,
			Package)
		for (containingPackage : packages) {
			if (containingPackage.name.equalsIgnoreCase("contracts")) {
				containingPackage.compilationUnits.add(correspondingCompilationUnit)
				correspondingCompilationUnit.namespaces.addAll(containingPackage.namespaces)
				correspondingCompilationUnit.namespaces.add(containingPackage.name)
				return #[correspondingInterface, correspondingCompilationUnit]
			}
		}

		//package with name "contracts" not found --> ask user
		val List<String> stringList = new ArrayList<String>()
		for (candidate : packages) {
			stringList.add(candidate.name)
		}
		var int selection = userInteracting.selectFromMessage(UserInteractionType.MODAL,
			"No explicit contracts package found. Select a package to which the new interface '" +
				correspondingInterface.name + "' should be added", stringList)
		packages.get(selection).compilationUnits.add(correspondingCompilationUnit)
		correspondingCompilationUnit.namespaces.addAll(packages.get(selection).namespaces)
		return #[correspondingInterface, correspondingCompilationUnit]
	}

	/**
	 * Called when a signature was added.
	 * Add Method (which is in newCorrespondingEObjects) to the Interface corresponding to the OperationInterface 
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffecteEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newMethods) {
		if (newMethods.nullOrEmpty || (false == newMethods.forall[method|method instanceof InterfaceMethod])) {
			throw new RuntimeException(
				"unexpeceted value in newMethods parameter " + newMethods.size + " (expected 1):" + newMethods)
		}
		val Interface jaMoPPIf = correspondenceInstance.getCorrespondingEObjectsByType(newAffectedEObject, Interface).
			get(0)
		val parrentCorrespondence = correspondenceInstance.getAllCorrespondences(newAffectedEObject).get(0)
		val transformationResult = TransformationUtils.
			createTransformationChangeResultForEObjectsToSave(jaMoPPIf.toArray)
		for (eObject : newMethods) {
			val InterfaceMethod newMethod = eObject as InterfaceMethod;
			jaMoPPIf.members.add(newMethod)

			//this code does not work, because adding a method to interface methods does not cause an update of the resource.
			//I guess only members list is a containment list 
			//jaMoPPIf.methods.add(index, newMethod);
			transformationResult.addNewCorrespondence(correspondenceInstance, newValue, newMethod, parrentCorrespondence)
		}
		return transformationResult
	}

	/**
	 * called when a signature was removed.
	 * Remove method from corresponding interface
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldMethods) {
		if (oldMethods.nullOrEmpty || (false == oldMethods.forall[method|method instanceof InterfaceMethod])) {
			throw new RuntimeException(
				"unexpeceted value in oldMethods parameter " + oldMethods.size + " (expected 1):" + oldMethods)
		}
		val Interface jaMoPPIf = correspondenceInstance.getCorrespondingEObjectsByType(newAffectedEObject, Interface).
			get(0)
		for (eObject : oldMethods) {
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
		val cu = correspondingEObjects.filter(typeof(CompilationUnit)).get(0)
		val tcr = new TransformationChangeResult
		PCM2JaMoPPUtils.handleJavaRootNameChange(cu, affectedAttribute, newValue, tcr, correspondenceInstance, false)
		return tcr

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
