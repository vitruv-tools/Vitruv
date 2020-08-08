package tools.vitruv.dsls.commonalities.generator.helper

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentOperator

class ContainmentHelper extends GenerationHelper {

	package new() {
	}

	val Map<ReferenceContainment, EReference> containmentReferences = new HashMap

	def getEReference(ReferenceContainment containment) {
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
	static def getContainmentReference(EClass container, EClass contained) {
		return ContainmentOperator.getContainmentReference(container, container)
	}
}
