package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.elements.Classifier
import tools.vitruv.dsls.commonalities.language.elements.WellKnownClassifiers
import tools.vitruv.dsls.commonalities.language.impl.CommonalityAttributeImpl

import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityAttributeI extends CommonalityAttributeImpl {

	override basicGetClassLikeContainer() {
		containingCommonalityFile.commonality
	}

	override isMultiValued() {
		// TODO Validation: All mappings need to be either multi- or single-valued.
		getMappings().containsAny[it.isMultiValuedRead]
	}

	override getType() {
		if (getMappings().size === 0) return WellKnownClassifiers.JAVA_OBJECT;

		// The most specific type required by the mappings:
		var Classifier requiredType = WellKnownClassifiers.LEAST_SPECIFIC_TYPE
		// The least specific type provided by the mappings:
		var Classifier providedType = WellKnownClassifiers.MOST_SPECIFIC_TYPE
		for (CommonalityAttributeMapping mapping : getMappings()) {
			val mappingRequiredType = mapping.requiredType ?: WellKnownClassifiers.LEAST_SPECIFIC_TYPE
			val mappingProvidedType = mapping.providedType ?: WellKnownClassifiers.MOST_SPECIFIC_TYPE
			assertTrue(mappingRequiredType.isSuperTypeOf(mappingProvidedType))

			if (requiredType.isSuperTypeOf(mappingRequiredType)) {
				// The mapping's required type is more specific:
				requiredType = mappingRequiredType
			} else if (!mappingRequiredType.isSuperTypeOf(requiredType)) {
				// The mapping's required type is not within the same inheritance hierarchy as the current required
				// type. -> The mappings are incompatible.
				// TODO check this during validation
				throw new RuntimeException('''Incompatible mappings for commonality attribute «this»''')
			}

			if (mappingProvidedType.isSuperTypeOf(providedType)) {
				// The mapping's provided type is less specific:
				providedType = mappingProvidedType
			} else if (!providedType.isSuperTypeOf(mappingProvidedType)) {
				// The mapping's provided type is not within the same inheritance hierarchy as the current provided
				// type. -> The mappings are incompatible.
				// TODO check this during validation
				throw new RuntimeException('''Incompatible mappings for commonality attribute «this»''')
			}
		}
		if (!requiredType.isSuperTypeOf(providedType)) {
			// The mappings are incompatible.
			// TODO check this during validation
			throw new RuntimeException('''Incompatible mappings for commonality attribute «this»''')
		}

		if (providedType === WellKnownClassifiers.MOST_SPECIFIC_TYPE) {
			// We do not have any reading mappings. We expect there to be at least one writing mapping.
			assertTrue(requiredType !== WellKnownClassifiers.LEAST_SPECIFIC_TYPE)
			return requiredType
		} else {
			return providedType
		}
	}

	override toString() {
		'''«classLikeContainer».«name»'''
	}
}
