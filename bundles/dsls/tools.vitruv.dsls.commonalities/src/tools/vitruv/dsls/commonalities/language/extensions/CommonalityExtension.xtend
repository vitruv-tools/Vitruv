package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Commonality

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import tools.vitruv.dsls.commonalities.language.CommonalityFile

@Utility
package class CommonalityExtension {
	static def getConcept(Commonality commonality) {
		commonality.getDirectEContainer(CommonalityFile).concept
	}
}
