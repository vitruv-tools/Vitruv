package tools.vitruv.dsls.commonalities.language.extensions

import tools.vitruv.dsls.commonalities.language.CommonalityAttribute

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import tools.vitruv.dsls.commonalities.language.Commonality

class CommonalityAttributeExtension {
	static def getDeclaringCommonality(CommonalityAttribute attribute) {
		attribute.getDirectEContainer(Commonality)
	}
}