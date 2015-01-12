package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.containers.CompilationUnit
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence

class CompilationUnitMappingTransformation extends EmptyEObjectMappingTransformation {
	
	val private static Logger logger = Logger.getLogger(CompilationUnitMappingTransformation.simpleName)
	
	override getClassOfMappedEObject() {
		return CompilationUnit
	}
	
	/**
	 * We do not need this method. 
	 * After a creation of a compilation unit the creation method for a class or an interface is executed
	 * @see ClassMappingTransformation. We handle the creation of a classes or interfaces in the specific transformations.
	 */
	override createEObject(EObject eObject) {
		logger.trace("Compilation unit " + eObject + " created. Currently nothing is done for compilation unit")
		return null
	}
	
	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		logger.trace("Compilation unit " + newRootEObject + " created as root EObject. Currently nothing is done for compilation unit")
		return TransformationUtils.createEmptyTransformationChangeResult
	}
	
	/**
	 * The method is called when a class or interface has been created within the compilation unit 
	 * that has corresponding PCM objects (i.e. basic components)
	 */
	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects) {
		if(newCorrespondingEObjects.nullOrEmpty){
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val tcr = new TransformationChangeResult
		val parrentCorrespondences = correspondenceInstance.getAllCorrespondences(affectedEObject)
		var Correspondence parrentCorrespondence = null
		if(!parrentCorrespondences.nullOrEmpty){
			parrentCorrespondence = parrentCorrespondences.get(0)
		}
		for(correspondingEObject : newCorrespondingEObjects){
			tcr.addNewCorrespondence(this.correspondenceInstance, correspondingEObject, newValue, parrentCorrespondence)
		}
		return tcr
	}
	
	override setCorrespondenceForFeatures() {
	}
	
}