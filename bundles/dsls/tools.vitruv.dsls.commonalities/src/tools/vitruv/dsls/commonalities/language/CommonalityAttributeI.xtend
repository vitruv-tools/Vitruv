package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.elements.WellKnownClassifiers
import tools.vitruv.dsls.commonalities.language.impl.CommonalityAttributeImpl

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class CommonalityAttributeI extends CommonalityAttributeImpl {

	override basicGetClassLikeContainer() {
		containingCommonalityFile.commonality
	}

	override isMultiValued() {
		getMappings().containsAny[attribute.isMultiValued]
	}

	override getType() {
		if (getMappings().size === 0) return WellKnownClassifiers.JAVA_OBJECT;

		val mappingIterator = getMappings().iterator
		val firstMapping = mappingIterator.next
		var requiredType = firstMapping.requiredType
		var providedType = firstMapping.providedType
		for (var CommonalityAttributeMapping mapping; mappingIterator.hasNext; mapping = mappingIterator.next) {
			// if this is not the case, the specification itself is invalid, so we skip it.
			if (mapping?.requiredType !== null && mapping.requiredType.isSuperTypeOf(mapping.providedType)) {
				if (!mapping.requiredType.isSuperTypeOf(requiredType)) {
					if (mapping.requiredType.isSuperTypeOf(providedType)) {
						requiredType = mapping.requiredType
					} else {
						// specification not compatible to others
					}
				}
				if (!providedType.isSuperTypeOf(mapping.providedType)) {
					if (requiredType.isSuperTypeOf(mapping.providedType)) {
						providedType = mapping.providedType
					}
				}
			}
		}

		// we know that requiredType.isSuperTypeOf(providedType)
		return if (providedType != WellKnownClassifiers.MOST_SPECIFIC_TYPE) providedType else requiredType
	}
	
	override toString() {
		'''«classLikeContainer».«name»'''
	}
	
}
