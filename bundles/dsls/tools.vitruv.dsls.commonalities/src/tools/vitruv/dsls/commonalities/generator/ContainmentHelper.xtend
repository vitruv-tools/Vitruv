package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentRelation

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ContainmentHelper extends GenerationHelper {

	@Data
	static class Containment {
		val ParticipationClass contained
		val ParticipationClass container
		val EReference reference
	}

	package new() {
	}

	def getContainments(Participation participation) {
		return (participation.allContainmentRelations.flatMap[getContainments]
			+ participation.allContainmentConditions.flatMap[getContainment])
			.filterNull
	}

	def getContainments(ParticipationRelation relation) {
		if (!relation.isContainment) {
			return null
		} else {
			val container = relation.rightClasses.head
			if (container === null) return null
			val containerEClass = container.changeClass
			return relation.leftClasses.map [ contained |
				val containedEClass = contained.changeClass
				new Containment(contained, container, containerEClass.getContainmentFeature(containedEClass))
			]
		}
	}

	def getContainment(ParticipationCondition condition) {
		if (!condition.isContainment) {
			return null
		} else {
			val container = condition.rightOperands.head?.participationClass
			if (container === null) return null
			val containerEClass = container.changeClass
			val contained = condition.leftOperand.participationClass
			val containedEClass = contained.changeClass
			// TODO allow containment operator to specify the containment reference feature (instead of guessing it)
			return Collections.singleton(
				new Containment(contained, container, containerEClass.getContainmentFeature(containedEClass))
			)
		}
	}

	// guesses the containment reference
	def static getContainmentFeature(EClass container, EClass contained) {
		return ContainmentRelation.getContainmentFeature(container, container)
	}
}