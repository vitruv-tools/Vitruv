package tools.vitruv.extensions.dslruntime.commonalities.participationrelations

import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference

import static extension org.eclipse.xtext.EcoreUtil2.*

@RelationName('in')
class ContainmentRelation extends AbstractParticipationRelation {
	
	new(EObject[] leftObjects, EObject[] rightObjects) {
		super(leftObjects, rightObjects)
	}
	
	override afterCreated() {
		for (right : rightObjects) {
			for (left : leftObjects) {
				val containmentFeature = right.eClass.EStructuralFeatures.filter(EReference).findFirst [
					isContainment && EType instanceof EClass && (EType as EClass).isAssignableFrom(left.eClass)
				]
				if (containmentFeature === null) {
					throw new IllegalStateException('''Could not find any containment feature in‹«right»› to insert ‹«left»› in.''')
				}
				if (containmentFeature.upperBound != 1) {
					(right.eGet(containmentFeature) as List<EObject>) += left
				} else {
					right.eSet(containmentFeature, left)
				}
			}
		}
	}
}
