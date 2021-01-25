package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.CommonalityReferenceImpl

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityReferenceI extends CommonalityReferenceImpl {

	override basicGetClassLikeContainer() {
		getOptionalDirectEContainer(Commonality)
	}

	override isMultiValued() {
		getMappings().containsAny[it.isMultiValued]
	}

	override getType() {
		getReferenceType()
	}

	override toString() {
		'''«classLikeContainer».«name»'''
	}
}
