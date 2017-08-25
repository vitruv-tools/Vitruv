package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationRelationDeclaration
import tools.vitruv.dsls.commonalities.language.SimpleTupleParticipationDeclarationPart

@Utility package class ParticipationDeclarationPartExtension {
	
	def static dispatch getParticipationClasses(SimpleTupleParticipationDeclarationPart part) {
		#[part.participationClass]
	}
	
	def static dispatch getParticipationClasses(ParticipationRelationDeclaration part) {
		part.leftClasses + part.rightClasses
	}
}