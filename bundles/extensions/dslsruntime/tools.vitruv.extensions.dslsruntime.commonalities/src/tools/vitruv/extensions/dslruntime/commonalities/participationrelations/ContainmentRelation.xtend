package tools.vitruv.extensions.dslruntime.commonalities.participationrelations

import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EcorePackage

@RelationName('in')
class ContainmentRelation extends AbstractParticipationRelation {

	new(EObject[] leftObjects, EObject[] rightObjects) {
		super(leftObjects, rightObjects)
	}

	override afterInserted() {
		for (right : rightObjects) {
			for (left : leftObjects) {
				val containmentFeature = getContainmentFeature(left, right)
				if (containmentFeature.upperBound != 1) {
					(right.eGet(containmentFeature) as List<EObject>) += left
				} else {
					right.eSet(containmentFeature, left)
				}
			}
		}
	}

	override check() {
		for (right : rightObjects) {
			for (left : leftObjects) {
				val containmentFeature = getContainmentFeature(left, right)
				if (containmentFeature.upperBound != 1) {
					if (!(right.eGet(containmentFeature) as List<EObject>).contains(left)) {
						return false
					}
				} else {
					if (right.eGet(containmentFeature) != left) {
						return false
					}
				}
			}
		}
	}

	def private getContainmentFeature(EObject left, EObject right) {
		val containmentFeature = right.eClass.EAllReferences.findFirst [
			isContainment && EType instanceof EClass && (EType as EClass).isAssignableFrom(left.eClass)
		]
		if (containmentFeature === null) {
			throw new IllegalStateException('''Could not find any containment feature in ‹«right.class.canonicalName»› to insert ‹«left.class.canonicalName»› into.''')
		}
		return containmentFeature
	}

	def private static isAssignableFrom(EClass superType, EClass candidate) {
		superType == EcorePackage.Literals.EOBJECT || superType.isSuperTypeOf(candidate)
	}
}
