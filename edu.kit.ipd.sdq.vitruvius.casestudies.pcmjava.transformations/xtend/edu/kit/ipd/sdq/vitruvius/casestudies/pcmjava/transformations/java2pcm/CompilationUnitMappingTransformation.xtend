package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.palladiosimulator.pcm.system.System

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
	}

	/**
	 * The method is called when a class or interface has been created within the compilation unit 
	 * that has corresponding PCM objects (i.e. basic components)
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		if (newValue instanceof Class || newValue instanceof Interface) {

			// if it is a class that should correspond to a system and the system already has a container 
			//do not mark the system as new objet to create.
			if (!newCorrespondingEObjects.nullOrEmpty) {
				val systems = newCorrespondingEObjects.filter(typeof(System))
				if (!systems.nullOrEmpty) {
					for (system : systems) {
						if (null == system.eResource) {
							PCMJaMoPPUtils.saveEObject(system, blackboard, PCMJaMoPPUtils.getSourceModelVURI(newAffectedEObject))
						} else {
							TransformationUtils.saveNonRootEObject(system)
						}
						blackboard.correspondenceInstance.createAndAddEObjectCorrespondence(system, newValue)
					}
				}
			}
			JaMoPP2PCMUtils.
				createNewCorrespondingEObjects(newValue, newCorrespondingEObjects,
					blackboard)
		}
	}

	override setCorrespondenceForFeatures() {
	}

}
