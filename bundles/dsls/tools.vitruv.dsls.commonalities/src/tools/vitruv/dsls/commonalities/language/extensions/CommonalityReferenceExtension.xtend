package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityReference

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

@Utility class CommonalityReferenceExtension {

	def static isMultiValued(CommonalityReference reference) {
		reference.mappings.containsAny [it.reference.isMultiValued]
	}	
}