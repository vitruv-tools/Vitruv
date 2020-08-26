package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation

import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EcorePackage

@ParticipationRelationOperator(name = 'in')
class ContainmentOperator extends AbstractParticipationRelationOperator {

	new(EObject[] leftObjects, EObject[] rightObjects) {
		super(leftObjects, rightObjects)
	}

	override enforce() {
		for (right : rightObjects) {
			for (left : leftObjects) {
				val containmentReference = right.eClass.getContainmentReference(left.eClass)
				if (containmentReference.upperBound != 1) {
					(right.eGet(containmentReference) as List<EObject>) += left
				} else {
					right.eSet(containmentReference, left)
				}
			}
		}
	}

	override check() {
		for (right : rightObjects) {
			for (left : leftObjects) {
				val containmentReference = right.eClass.getContainmentReference(left.eClass)
				if (containmentReference.upperBound != 1) {
					if (!(right.eGet(containmentReference) as List<EObject>).contains(left)) {
						return false
					}
				} else {
					if (right.eGet(containmentReference) != left) {
						return false
					}
				}
			}
		}
	}

	static def getContainmentReference(EClass container, EClass contained) {
		val containmentFeature = container.EAllReferences.findFirst [
			isContainment && EType instanceof EClass && (EType as EClass).isAssignableFrom(contained)
		]
		if (containmentFeature === null) {
			throw new IllegalStateException('''Could not find any containment feature in ‹«container.name
				»› that may contain ‹«contained.name»›.''')
		}
		return containmentFeature
	}

	private static def isAssignableFrom(EClass superType, EClass candidate) {
		return (superType == EcorePackage.Literals.EOBJECT) || superType.isSuperTypeOf(candidate)
	}
}
