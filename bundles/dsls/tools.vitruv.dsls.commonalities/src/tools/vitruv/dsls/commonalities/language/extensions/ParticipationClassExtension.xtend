package tools.vitruv.dsls.commonalities.language.extensions

import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.commonalities.language.ParticipationRelationDeclaration

@Utility
package class ParticipationClassExtension {
	def static ParticipationRelationDeclaration getOptionalParticipationRelation(ParticipationClass participationClass) {
		findOptionalParticipationRelation(participationClass.eContainer)
	}

	def private static dispatch findOptionalParticipationRelation(EObject catchAll) {}

	def private static dispatch findOptionalParticipationRelation(
		ParticipationRelationDeclaration relationDeclaration) {
		relationDeclaration
	}
}
