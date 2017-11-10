package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Commonality

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.getContainingCommonalityFile

@Utility package class CommonalityExtension {
	
	def static getConcept(Commonality commonality) {
		commonality.containingCommonalityFile.concept
	}
}