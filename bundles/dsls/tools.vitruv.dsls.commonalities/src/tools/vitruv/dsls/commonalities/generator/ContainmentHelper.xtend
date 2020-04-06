package tools.vitruv.dsls.commonalities.generator

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.language.extensions.Containment
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentRelation

package class ContainmentHelper extends GenerationHelper {

	package new() {
	}

	val Map<Containment, EReference> containmentReferences = new HashMap

	def getEReference(Containment containment) {
		return containmentReferences.computeIfAbsent(containment) [
			val containerEClass = container.changeClass
			val containedEClass = contained.changeClass
			if (reference === null) {
				// guess the containment reference:
				return containerEClass.getContainmentReference(containedEClass)
			} else {
				return reference.correspondingEFeature as EReference
			}
		]
	}

	// guesses the containment reference
	def static getContainmentReference(EClass container, EClass contained) {
		return ContainmentRelation.getContainmentReference(container, container)
	}
}