package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import org.eclipse.emf.ecore.EStructuralFeature
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import org.eclipse.emf.ecore.EClassifier
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement
import java.util.List
import java.util.ArrayList
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.EClassifierFeature

class MIRHelper {
	static def List<FeatureCall> collectFeatureCalls(TypedElement fc) {
		val result = new ArrayList<FeatureCall>()
		
		var TypedElement iter = fc;
		while (iter != null) {
			if (iter instanceof FeatureCall) {
				result += iter
				iter = iter.ref
			} else if (iter instanceof TypedElementRef) {
				iter = null
			} else {
				iter = null
			}
		}
		
		result
	}
	
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
	
	static def getMIR(Resource input) {
		val mirFiles = input.contents.filter(typeof(MIRFile))
		
		if (mirFiles.length != 1)
			throw new IllegalArgumentException("input resource contains more than one MIRFile model instance")
		
		return mirFiles.get(0);
	}
	
	static def getProjectName(MIRFile mir) {
		return mir.generatedPackage
	}
}
	
	