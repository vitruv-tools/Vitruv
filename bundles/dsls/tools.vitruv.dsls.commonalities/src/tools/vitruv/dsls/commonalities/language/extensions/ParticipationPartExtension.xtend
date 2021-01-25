package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.ParticipationPart

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationExtension.*
import tools.vitruv.dsls.commonalities.language.Participation

@Utility
package class ParticipationPartExtension {
	static def getDeclaringParticipation(ParticipationPart participationPart) {
		participationPart.getEContainer(Participation)
	}
	
	static def getDeclaringCommonality(ParticipationPart participationPart) {
		participationPart.declaringParticipation.declaringCommonality
	}
	
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
	
	static def dispatch ParticipationClass getDeclaredContainerClass(ParticipationClass pClass) {
		pClass
	}

	static def dispatch ParticipationClass getDeclaredContainerClass(ParticipationRelation relation) {
		relation.rightParts.head?.declaredContainerClass
	}
}
