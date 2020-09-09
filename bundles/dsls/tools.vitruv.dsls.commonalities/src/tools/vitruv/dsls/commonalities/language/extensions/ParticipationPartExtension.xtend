package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.SimpleTupleParticipationPart

@Utility
package class ParticipationPartExtension {

	static def dispatch getParticipationClasses(SimpleTupleParticipationPart part) {
		#[part.participationClass]
	}

	static def dispatch getParticipationClasses(ParticipationRelation part) {
		part.leftClasses + part.rightClasses
	}
}
