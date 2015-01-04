package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import org.eclipse.emf.ecore.EStructuralFeature
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import org.eclipse.emf.ecore.EClassifier
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement

class MIRHelper {
	static def dispatch EClassifier getTypeRecursive(TypedElement element) {
		null
	}
	
	static def dispatch EClassifier getTypeRecursive(NamedEClass namedEClass) {
		namedEClass.representedEClass
	}
	
	static def dispatch EClassifier getTypeRecursive(FeatureCall featureCall) {
		featureCall.type ?: featureCall.tail?.EType ?: featureCall.ref.getTypeRecursive
	}
	
	static def dispatch EClassifier getTypeRecursive(TypedElementRef typedElementRef) {
		typedElementRef.ref?.getTypeRecursive
	}
	
	static def dispatch EStructuralFeature getStructuralFeature(NamedEClass call) {
		return null
	}
	
	static def dispatch EStructuralFeature getStructuralFeature(TypedElement el) {
		return null
	} 
	
	static def dispatch EStructuralFeature getStructuralFeature(FeatureCall call) {
		return call.tail
	}
	
	static def dispatch EStructuralFeature getStructuralFeature(TypedElementRef ref) {
		return ref.ref.getStructuralFeature
	}
}
	
	