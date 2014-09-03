package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMMappingTransformationBase
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.modifiers.Modifier

/**
 * Triggered when a CUD operation on a Modifier is detected.
 */
class ModifierMappingTransformation extends JaMoPPPCMMappingTransformationBase {
	
	private static val Logger logger = Logger.getLogger(ModifierMappingTransformation.simpleName)
	
	override getClassOfMappedEObject() { 
		return Modifier
	}

	override setCorrespondenceForFeatures() {
	}
	
	override createEObject(EObject eObject) {
	}

	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
	}

	override removeEObject(EObject eObject) {
	}

	override deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
	}
	
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
	}
	
	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue, EObject[] newCorrespondingEObjects) {
	}
	
}