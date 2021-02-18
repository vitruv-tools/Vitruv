package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import tools.vitruv.testutils.matchers.EqualityStrategy
import static tools.vitruv.testutils.matchers.EqualityStrategy.Result.*
import java.util.Set
import static extension tools.vitruv.testutils.printing.TestMessages.*
import java.util.List
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import java.util.HashSet

@FinalFieldsConstructor
package class EqualityStrategies implements EqualityStrategy {
	val Set<EClass> targetEClasses
	
	override Result compare(EObject left, EObject right) {
		if (!targetEClasses.contains(left.eClass) || !targetEClasses.contains(right.eClass)) UNKNOWN
		else if (left == right) EQUAL
		else UNEQUAL
	}
	
	override getTargetNsUris() {
		targetEClasses.mapFixedTo(new HashSet) [EPackage.nsURI]
	}
	
	override describeTo(extension StringBuilder builder) {
		append("used equals() to compare ").joinSemantic(targetEClasses, 'and') [append(plural(name))]
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
	
	override getTargetNsUris() {
		strategies.flatMapFixedTo(new HashSet) [targetNsUris]
	}
	
	override describeTo(StringBuilder builder) {
		strategies.joinSemantic('and', ';') [target, it | describeTo(target)]
	}
}