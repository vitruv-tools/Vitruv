package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.SimpleParticipation
import tools.vitruv.dsls.commonalities.language.SimpleTupleParticipationPart
import tools.vitruv.dsls.commonalities.language.TupleParticipation

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

@Utility
class ParticipationExtension {
	def static dispatch Iterable<ParticipationClass> getClasses(SimpleParticipation participation) {
		Collections.singleton(participation.participationClass)
	}
	
	def static dispatch Iterable<ParticipationClass> getClasses(TupleParticipation participation) {
		participation.parts.flatMap [containedClasses]
	}
	
	def private static dispatch Iterable<ParticipationClass> getContainedClasses(SimpleTupleParticipationPart participationPart) {
		Collections.singleton(participationPart.participationClass)
	}
	
	def private static dispatch Iterable<ParticipationClass> getContainedClasses(ParticipationRelation participationPart) {
		participationPart.leftClasses + participationPart.rightClasses
	}
	
	def static dispatch getDomainName(SimpleParticipation participation) {
		participation?.participationClass?.superMetaclass?.domain?.name
	}
	
	def static dispatch getDomainName(TupleParticipation participation) {
		participation.domainName
	}
	
	def static getDomain(Participation participation) {
		participation.classes.head?.superMetaclass?.domain		
	}
}
