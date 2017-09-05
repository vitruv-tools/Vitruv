package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.SimpleTupleParticipationPart

@Utility
class ParticipationPartExtension {

	def static dispatch getParticipationClasses(SimpleTupleParticipationPart part) {
		#[part.participationClass]
	}

	def static dispatch getParticipationClasses(ParticipationRelation part) {
		part.leftClasses + part.rightClasses
	}
}
