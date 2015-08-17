package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.validation.MIRValidator

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.EMFHelper.*
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping

class MIRHelper {
	static def List<EObject> createParameterList(EObject context) {
		var containerHierarchy = EMFHelper.getContainerHierarchy(context, true)

		val result = new ArrayList<EObject>()

		for (EObject o : containerHierarchy) {
			if (o instanceof ClassMapping) {
				result += o.mappedElements.filter(NamedEClass).filterNull.filter[it.name != null]
			} else if (o instanceof FeatureMapping) {
				result += o.mappedElements.map[MIRHelper.collectFeatureCalls(it)].flatten.filter[it.name != null]
			}
		}

		result.reverse
	}
	
	/**
	 * Returns null if valid. If the Mapping is not valid, a fitting error id is returned
	 * @see MIRValidator
	 */
	static def dispatch String getErrorID(FeatureMapping featureMapping) {
		val elementsDataType = featureMapping.mappedElements.map [typeRecursive.isEDataType]
		
		val allEDataType = elementsDataType.forall[it]
		val allEClass = elementsDataType.forall[!it]
		
		if (!allEDataType && !allEClass) {
			return MIRValidator.MIXED_FEATURES
		} else if (allEDataType && (featureMapping.constraints != null)) {
			return MIRValidator.EDATATYPES_AND_CONSTRAINTS
		} else {
			return null
		}
	}
	
	static def dispatch String getErrorID(ClassMapping classMapping) {
		return null
	}
	
	static def boolean isValid(Mapping mapping) {
		return (mapping.errorID == null)
	}
	
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
	
	static def dispatch String tryGetName(EObject element) {
		return null
	}

	static def dispatch String tryGetName(TypedElement element) {
		return null
	}
	
	static def dispatch String tryGetName(NamedEClass element) {
		return element.name
	}
	
	static def dispatch String tryGetName(FeatureCall element) {
		return element.name
	}
	
	static def dispatch EClassifier getTypeRecursive(EObject element) {
		null
	}
	
	static def dispatch EClassifier getTypeRecursive(TypedElement element) {
		null
	}
	
	static def dispatch EClassifier getTypeRecursive(NamedEClass namedEClass) {
		namedEClass.type
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
			throw new IllegalArgumentException("input resource contains more than one MIRFile model instance (" + mirFiles.length + ")")
		
		return mirFiles.get(0);
	}
	
	static def getProjectName(MIRFile mir) {
		return mir.generatedPackage
	}
	
	static def getCorrespondenceDecoratorFileExtPrefix() {
		return "mapped_"
	}
}
	
	