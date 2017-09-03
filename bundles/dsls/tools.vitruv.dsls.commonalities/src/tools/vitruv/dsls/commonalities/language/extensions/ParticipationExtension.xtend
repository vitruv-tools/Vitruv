package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelationDeclaration
import tools.vitruv.dsls.commonalities.language.SimpleParticipationDeclaration
import tools.vitruv.dsls.commonalities.language.SimpleTupleParticipationDeclarationPart
import tools.vitruv.dsls.commonalities.language.TupleParticipationDeclaration

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

@Utility
class ParticipationExtension {
	def static dispatch Iterable<ParticipationClass> getClasses(SimpleParticipationDeclaration participation) {
		Collections.singleton(participation.participationClass)
	}
	
	def static dispatch Iterable<ParticipationClass> getClasses(TupleParticipationDeclaration participation) {
		participation.parts.flatMap [containedClasses]
	}
	
	def private static dispatch Iterable<ParticipationClass> getContainedClasses(SimpleTupleParticipationDeclarationPart participationPart) {
		Collections.singleton(participationPart.participationClass)
	}
	
	def private static dispatch Iterable<ParticipationClass> getContainedClasses(ParticipationRelationDeclaration participationPart) {
		participationPart.leftClasses + participationPart.rightClasses
	}
	
	def static getDomain(Participation participation) {
		participation.classes.head?.superMetaclass?.domain
	}
}
