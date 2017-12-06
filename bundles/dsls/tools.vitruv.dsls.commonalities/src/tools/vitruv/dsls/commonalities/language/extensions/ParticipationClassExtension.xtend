package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation

@Utility package class ParticipationClassExtension {
	def static ParticipationRelation getOptionalParticipationRelation(ParticipationClass participationClass) {
		findOptionalParticipationRelation(participationClass.eContainer)
	}

	def private static dispatch findOptionalParticipationRelation(EObject catchAll) {}

	def private static dispatch findOptionalParticipationRelation(
		ParticipationRelation relationDeclaration) {
		relationDeclaration
	}
	
	def static getDomain(ParticipationClass participationClass) {
		participationClass.superMetaclass?.domain
	}
	
	def static getParticipation(ParticipationClass participationClass) {
		if (participationClass.eIsProxy) return null
		return participationClass.findParticipation
	}
	
	def private static dispatch Participation findParticipation(Participation participation) {
		participation
	}
	
	def private static dispatch Participation findParticipation(EObject object) {
		object.eContainer.findParticipation
	}
	
	def private static dispatch Participation findParticipation(Void nill) {
		throw new IllegalStateException("Found a participation class outside of a participation!")
	}
}
