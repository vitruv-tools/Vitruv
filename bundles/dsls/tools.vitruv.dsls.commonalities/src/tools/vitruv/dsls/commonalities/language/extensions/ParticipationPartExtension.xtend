package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import java.util.List

@Utility
package class ParticipationPartExtension {
	static def dispatch Iterable<ParticipationClass> getAllParticipationClasses(ParticipationClass pClass) {
		List.of(pClass)
	}

	static def dispatch Iterable<ParticipationClass> getAllParticipationClasses(ParticipationRelation relation) {
		relation.leftParts.flatMap[allParticipationClasses] + relation.rightParts.flatMap[allParticipationClasses]
	}

	static def dispatch Iterable<ParticipationRelation> getAllParticipationRelations(ParticipationClass pClass) {
		emptyList()
	}

	static def dispatch Iterable<ParticipationRelation> getAllParticipationRelations(ParticipationRelation relation) {
		List.of(relation) + //
		relation.leftParts.flatMap[allParticipationRelations] + relation.rightParts.flatMap[allParticipationRelations]
	}
	
	static def dispatch ParticipationClass getContainer(ParticipationClass pClass) {
		pClass
	}

	static def dispatch ParticipationClass getContainer(ParticipationRelation relation) {
		relation.rightParts.head?.container
	}
}
