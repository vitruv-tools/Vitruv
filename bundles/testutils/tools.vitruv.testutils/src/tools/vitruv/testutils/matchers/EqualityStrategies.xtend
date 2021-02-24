package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import tools.vitruv.testutils.matchers.EqualityStrategy
import static tools.vitruv.testutils.matchers.EqualityStrategy.Result.*
import java.util.Set
import static extension tools.vitruv.testutils.printing.TestMessages.*
import java.util.List

@FinalFieldsConstructor
package class EqualsBasedEqualityStrategy implements EqualityStrategy {
	val Set<EClass> targetEClasses

	override Result compare(EObject left, EObject right) {
		if (!isTargetInstance(left) || !isTargetInstance(right)) UNKNOWN
		else if (left == right) EQUAL
		else UNEQUAL
	}
	
	def isTargetInstance(EObject object) {
		targetEClasses.contains(object.eClass) || targetEClasses.exists [isInstance(object)]
	}
	
	override describeTo(extension StringBuilder builder) {
		append("used equals() to compare referenced ").joinSemantic(targetEClasses, 'and') [append(plural(name))]
	}
}

@FinalFieldsConstructor
package class MultiEqualityStrategy implements EqualityStrategy {
	val List<? extends EqualityStrategy> strategies
	
	override compare(EObject left, EObject right) {
		for (strategy : strategies) {
			val strategyResult = strategy.compare(left, right)
			if (strategyResult != EqualityStrategy.Result.UNKNOWN) return strategyResult
		}
		return EqualityStrategy.Result.UNKNOWN
	}
	
	override describeTo(StringBuilder builder) {
		strategies.joinSemantic('and', ';') [target, it | describeTo(target)]
	}
}