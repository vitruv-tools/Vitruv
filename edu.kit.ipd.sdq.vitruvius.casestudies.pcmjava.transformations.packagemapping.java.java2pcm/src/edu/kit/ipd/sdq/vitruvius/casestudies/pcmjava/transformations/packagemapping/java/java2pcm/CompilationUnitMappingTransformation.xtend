package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.palladiosimulator.pcm.system.System

import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.java2pcm.JaMoPP2PCMUtils

class CompilationUnitMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static Logger logger = Logger.getLogger(CompilationUnitMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return CompilationUnit
	}

	/**
	 * We do not need this method. 
	 * After a creation of a compilation unit the creation method for a class or an interface is executed
	 * @see ClassMappingTransformation We handle the creation of a classes or interfaces in the specific transformations.
	 */
	override createEObject(EObject eObject) {
		logger.trace("Compilation unit " + eObject + " created. Currently nothing is done for compilation unit")
		return null
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		logger.trace(
			"Compilation unit " + newRootEObject +
				" created as root EObject. Currently nothing is done for compilation unit")
		return new TransformationResult
	}

	/**
	 * The method is called when a class or interface has been created within the compilation unit 
	 * that has corresponding PCM objects (i.e. basic components)
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		val transformationResult = new TransformationResult
		if (newValue instanceof Class || newValue instanceof Interface) {

			// if it is a class that should correspond to a system and the system already has a container 
			//do not mark the system as new objet to create.
			if (!newCorrespondingEObjects.nullOrEmpty) {
				val systems = newCorrespondingEObjects.filter(typeof(System))
				if (!systems.nullOrEmpty) {
					for (system : systems) {
						if (null == system.eResource) {
							PCMJaMoPPUtils.addRootChangeToTransformationResult(system, blackboard, PCMJaMoPPUtils.getSourceModelVURI(newAffectedEObject), transformationResult)
						} else {
							//do nothing, cause save is done later
						}
						blackboard.correspondenceModel.createAndAddCorrespondence(system, newValue)
					}
				}
			}
			JaMoPP2PCMUtils.
				createNewCorrespondingEObjects(newValue, newCorrespondingEObjects,
					blackboard, transformationResult)
		}
		transformationResult
	}

	override setCorrespondenceForFeatures() {
	}
	
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		PCMJaMoPPUtils.removeCorrespondenceAndAllObjects(oldValue, oldAffectedEObject, blackboard)
		return new TransformationResult 
	}

}
