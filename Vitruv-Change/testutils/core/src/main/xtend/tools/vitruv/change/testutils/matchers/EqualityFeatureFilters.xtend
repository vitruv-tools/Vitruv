package tools.vitruv.change.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.Set
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier
import static extension tools.vitruv.change.testutils.printing.TestMessages.*
import tools.vitruv.change.testutils.matchers.EqualityFeatureFilter
import org.eclipse.emf.ecore.EClass
import java.util.List

@FinalFieldsConstructor
package class IgnoreFeatures implements EqualityFeatureFilter {
	val Set<EStructuralFeature> features

	override includeFeature(EStructuralFeature feature) {
		!features.contains(feature)
	}

	override describeTo(extension StringBuilder builder) {
		append('ignored the ').append(plural(features, 'feature')).append(' ')
			.joinSemantic(features, 'and') [append(EContainingClass.name).append('.').append(name)]
	}
}

@FinalFieldsConstructor
package class IncludeOnlyFeatures implements EqualityFeatureFilter {
	val Set<EStructuralFeature> features

	override includeFeature(EStructuralFeature feature) {
		features.contains(feature)
	}

	override describeTo(extension StringBuilder builder) {
		append('considered only the ').append(plural(features, 'feature')).append(' ')
			.joinSemantic(features, 'and') [append(EContainingClass.name).append('.').append(name)]
	}
}

@FinalFieldsConstructor
package class IgnoreNamedFeatures implements EqualityFeatureFilter {
	val Set<String> featureNames

	override includeFeature(EStructuralFeature feature) {
		!featureNames.contains(feature.name)
	}

	override describeTo(extension StringBuilder builder) {
		append('ignored any feature called ') 
			.joinSemantic(featureNames, 'or') [append("'").append(it).append("'")]
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptNamedFeatures implements EqualityFeatureFilter {
	val Set<String> featureNames

	override includeFeature(EStructuralFeature feature) {
		featureNames.contains(feature.name)
	}

	override describeTo(extension StringBuilder builder) {
		append('considered only features called ') //
			.joinSemantic(featureNames, 'or') [append("'").append(it).append("'")]
	}
}

@FinalFieldsConstructor
package class IgnoreTypedFeatures implements EqualityFeatureFilter {
	val Set<EClassifier> featureTypes

	override includeFeature(EStructuralFeature feature) {
		val featureType = feature.EType
		switch (featureType) {
			EClass: !featureTypes.contains(featureType) && !featureType.EAllSuperTypes.exists [featureTypes.contains(it)]
			default: !featureTypes.contains(featureType)
		}
	}

	override describeTo(extension StringBuilder builder) {
		append('ignored any feature of type ')
			.joinSemantic(featureTypes, 'or') [append(name)]
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptTypedFeatures implements EqualityFeatureFilter {
	val Set<EClassifier> featureTypes

	override includeFeature(EStructuralFeature feature) {
		val featureType = feature.EType
		switch (featureType) {
			EClass: featureTypes.contains(featureType) || featureType.EAllSuperTypes.exists [featureTypes.contains(it)]
			default: featureTypes.contains(featureType)
		}
	}

	override describeTo(extension StringBuilder builder) {
		append('considered only features of type ')
			.joinSemantic(featureTypes, 'or') [append(name)]
	}
}

@FinalFieldsConstructor
package class MultiEqualityFeatureFilter implements EqualityFeatureFilter {
	val List<EqualityFeatureFilter> filters
	
	override includeFeature(EStructuralFeature feature) {
		!filters.exists [!includeFeature(feature)]
	}
	
	override describeTo(StringBuilder builder) {
		filters.joinSemantic('and', ';') [target, it | describeTo(target)]
	}
}
