package tools.vitruv.dsls.commonalities.language.elements.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass

@Utility
package class ParticipationClassExtension {

	def static changeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.changeClass
	}
}
