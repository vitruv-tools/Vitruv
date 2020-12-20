package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier
import java.util.List

@FinalFieldsConstructor
package class IgnoreNamedFeatures implements FeatureMatcher {
	val Set<String> featureNames

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		featureNames.contains(feature.name)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptNamedFeatures implements FeatureMatcher {
	val Set<String> featureNames

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		!featureNames.contains(feature.name)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

package class IgnoreUnsetFeatures implements FeatureMatcher {
	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		!expectedObject.eIsSet(feature)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

@FinalFieldsConstructor
package class IgnoreTypedFeatures implements FeatureMatcher {
	val EClassifier featureType

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		feature.EType == featureType
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptTypedFeatures implements FeatureMatcher {
	val List<EClassifier> featureTypes

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		!featureTypes.contains(feature.EType)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}
