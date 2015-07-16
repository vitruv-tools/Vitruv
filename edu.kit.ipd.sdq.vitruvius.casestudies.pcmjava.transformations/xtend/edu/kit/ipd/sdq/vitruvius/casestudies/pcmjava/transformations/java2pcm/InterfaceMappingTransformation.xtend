package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package

/**
 * Maps a JaMoPP interface to a PCM interface 
 * Triggered when a CUD operation on JaMoPP interface is detected.
 */
class InterfaceMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static final Logger logger = Logger.getLogger(InterfaceMappingTransformation.name)

	override getClassOfMappedEObject() {
		return Interface
	}

	/**
	 * Called when a Java-interface was added to the source code
	 * Determines whether the interface is architecture relevant or not
	 * An interface is architectural relevant, if 
	 * i) checking whether it is in the contracts package
	 * ii) asking the developer (not yet implemented)
	 */
	override createEObject(EObject eObject) {
		val Interface jaMoPPInterface = eObject as Interface
		try {
			val Package jaMoPPPackage = PCM2JaMoPPUtils::
				getContainingPackageFromCorrespondenceInstance(jaMoPPInterface, correspondenceInstance)
			var boolean createInterface = false
			if (null != jaMoPPPackage && jaMoPPPackage.name.equals("contracts")) {

				//i)
				createInterface = true
			} else {

				//ii)
				val String msg = "The created interface is not in the contracts packages. Should an architectural interface be created for the interface " +
					jaMoPPInterface.name + " ?"
				createInterface = super.modalTextYesNoUserInteracting(msg)
			}
			if (createInterface) {
				var OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface
				opInterface.setEntityName(jaMoPPInterface.name)
				val Repository repo = JaMoPP2PCMUtils.getRepository(correspondenceInstance)
				opInterface.setRepository__Interface(repo)
				return opInterface.toArray
			}

		} catch (Exception e) {
			logger.info(e)
		}
		return null;
	}

	/**
	 * called when an interface method has been created
	 * 
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects) {
		return JaMoPP2PCMUtils.
			createTransformationChangeResultForNewCorrespondingEObjects(newValue, newCorrespondingEObjects,
				correspondenceInstance)
	}

	/**
	 * Called when a Java-interface was removed. Also removes the corresponding PCM Interface (if there is one)
	 * Does not ask the developer whether the PCM interface should be removed also.
	 */
	override removeEObject(EObject eObject) {
		val Interface jaMoPPInterface = eObject as Interface
		val CompilationUnit jaMoPPCompilationUnit = jaMoPPInterface.containingCompilationUnit
		try {
			var EObject correspondingOpInterface = correspondenceInstance.
				claimUniqueCorrespondingEObjectByType(jaMoPPInterface, OperationInterface)
			var EObject correspondingOpInterface2CompilationUnit = correspondenceInstance.
				claimUniqueCorrespondingEObjectByType(jaMoPPCompilationUnit, OperationInterface)
			if (null == correspondingOpInterface && null == correspondingOpInterface2CompilationUnit) {
				return null
			}
			if (correspondingOpInterface != correspondingOpInterface2CompilationUnit) {
				logger.warn(
					"corresponding interface " + correspondingOpInterface +
						"is not the same interface as the interface corresponding to the compilation unit " +
						correspondingOpInterface2CompilationUnit)
				return null
			}
			EcoreUtil.remove(correspondingOpInterface)
			correspondenceInstance.removeAllCorrespondences(jaMoPPInterface)
		} catch (RuntimeException rte) {
			logger.info(rte)
		}
		return null
	}

	/**
	 * we do not really need the method deleteNonRootEObjectInList in InterfaceMappingTransformation because the deletion of the 
	 * object has already be done in removeEObject.
	 * We just return an empty TransformationChangeResult 
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		return JaMoPP2PCMUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceInstance)
	}

	override setCorrespondenceForFeatures() {
		var interfaceNameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("name")].iterator.next
		var opInterfaceNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.
			filter[attribute|attribute.name.equalsIgnoreCase("entityName")].iterator.next
		featureCorrespondenceMap.put(interfaceNameAttribute, opInterfaceNameAttribute)
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method createNonRootEObjectSingle should not be called for " + InterfaceMappingTransformation.simpleName +
				" transformation")
		return null
	}

}
