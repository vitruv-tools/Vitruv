package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.Set
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier
import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection
import static extension tools.vitruv.testutils.matchers.FilterUtil.joinSemantic

@FinalFieldsConstructor
package class IgnoreNamedFeatures implements DescribedFeatureFilter {
	val Set<String> featureNames

	override includeFeature(EStructuralFeature feature) {
		!featureNames.contains(feature.name)
	}

	override describe() {
		new StringBuilder().append('ignored any feature called ') //
		.joinSemantic(featureNames, 'or') [ extension builder, name |
			append("'").append(name).append("'")
		].toString()
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptNamedFeatures implements DescribedFeatureFilter {
	val Set<String> featureNames

	override includeFeature(EStructuralFeature feature) {
		featureNames.contains(feature.name)
	}

	override describe() {
		new StringBuilder().append('considered only features called ') //
		.joinSemantic(featureNames, 'or') [ extension builder, name |
			append("'").append(name).append("'")
		].toString()
	}
}

@FinalFieldsConstructor
package class IgnoreTypedFeatures implements DescribedFeatureFilter {
	val Set<EClassifier> featureTypes

	override includeFeature(EStructuralFeature feature) {
		!featureTypes.contains(feature.EType)
	}

	override describe() {
		new StringBuilder().append('ignored any feature of type ') //
		.joinSemantic(featureTypes, 'or') [ extension builder, type |
			append(type.name)
		].toString()
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptTypedFeatures implements DescribedFeatureFilter {
	val Set<EClassifier> featureTypes

	override includeFeature(EStructuralFeature feature) {
		featureTypes.contains(feature.EType)
	}

	override describe() {
		new StringBuilder().append('considered only features of type ') //
		.joinSemantic(featureTypes, 'or') [ extension builder, type |
			append(type.name)
		].toString()
	}
}

@Utility
package class FilterUtil {
	def static package <T> joinSemantic(StringBuilder builder, Collection<T> collection, String word,
		(StringBuilder, T)=>void mapper) {
		for (var i = 0, var iterator = collection.iterator; iterator.hasNext; i += 1) {
			val element = iterator.next
			if (i > 0) {
				builder.append(', ')
				if (i == collection.size - 2) {
					builder.append('or ')
				}
			}
			mapper.apply(builder, element)
		}
		return builder
	}
}
