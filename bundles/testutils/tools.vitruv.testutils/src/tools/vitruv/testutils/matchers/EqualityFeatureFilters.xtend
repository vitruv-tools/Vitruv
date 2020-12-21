package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.Set
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier
import static extension tools.vitruv.testutils.matchers.MatcherUtil.joinSemantic
import org.eclipse.emf.ecore.EObject

@FinalFieldsConstructor
package class IgnoreNamedFeatures implements EqualityFeatureFilter {
	val Set<String> featureNames

	override includeFeature(EObject object, EStructuralFeature feature) {
		!featureNames.contains(feature.name)
	}

	override describeTo(extension StringBuilder builder) {
		append('ignored any feature called ') //
		.joinSemantic(featureNames, 'or')[append("'").append(it).append("'")]
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptNamedFeatures implements EqualityFeatureFilter {
	val Set<String> featureNames

	override includeFeature(EObject object, EStructuralFeature feature) {
		featureNames.contains(feature.name)
	}

	override describeTo(extension StringBuilder builder) {
		append('considered only features called ') //
		.joinSemantic(featureNames, 'or')[append("'").append(it).append("'")]
	}
}

@FinalFieldsConstructor
package class IgnoreTypedFeatures implements EqualityFeatureFilter {
	val Set<EClassifier> featureTypes

	override includeFeature(EObject object, EStructuralFeature feature) {
		!featureTypes.contains(feature.EType)
	}

	override describeTo(extension StringBuilder builder) {
		append('ignored any feature of type ') //
		.joinSemantic(featureTypes, 'or')[append(name)]
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptTypedFeatures implements EqualityFeatureFilter {
	val Set<EClassifier> featureTypes

	override includeFeature(EObject object, EStructuralFeature feature) {
		featureTypes.contains(feature.EType)
	}

	override describeTo(extension StringBuilder builder) {
		append('considered only features of type ') //
		.joinSemantic(featureTypes, 'or')[append(name)]
	}
}

@FinalFieldsConstructor
package class TypeIncludingFeatureFilter implements EqualityFeatureFilter {
	val Set<Class<? extends EObject>> filteredTypes
	val EqualityFeatureFilter filter

	override includeFeature(EObject object, EStructuralFeature feature) {
		if (filteredTypes.exists[isInstance(object)]) filter.includeFeature(object, feature) else true
	}

	override describeTo(extension StringBuilder builder) {
		filter.describeTo(builder)
		append(' on ').joinSemantic(filteredTypes, 'and')[append(simpleName).append('s')]
	}
}

@FinalFieldsConstructor
package class TypeExcludingFeatureFilter implements EqualityFeatureFilter {
	val Set<Class<? extends EObject>> filteredTypes
	val EqualityFeatureFilter filter

	override includeFeature(EObject object, EStructuralFeature feature) {
		if (!filteredTypes.exists[isInstance(object)]) filter.includeFeature(object, feature) else true
	}

	override describeTo(extension StringBuilder builder) {
		filter.describeTo(builder)
		append(' unless it was on a ').joinSemantic(filteredTypes, 'or')[append(simpleName)]
	}
}