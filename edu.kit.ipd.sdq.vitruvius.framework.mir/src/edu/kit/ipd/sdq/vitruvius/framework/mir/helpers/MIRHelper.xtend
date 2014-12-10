package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassOrFeature
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeatureCall
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier

class MIRHelper {
	static def dispatch EStructuralFeature getStructuralFeature(ClassOrFeature ref) {
		val feature = ref.referencedFeature?.feature
		val result = (if (feature == null) null else ref.getStructuralFeature)
		
		return result
	}
	
	static def dispatch EStructuralFeature getStructuralFeature(NamedFeatureCall ref) {
		val result = ref.tail
		
		return result
	}
	
	static def dispatch EClassifier getType(ClassOrFeature ref) {
		val feature = ref.referencedFeature?.feature
		val result = (if (feature == null) null else ref.getType)
		
		return result
	}
	
	static def dispatch EClassifier getType(NamedFeatureCall ref) {
		val result = (if (ref.type == null) (ref.tail.EType) else (ref.type))
		
		return result
	}
}
	
	