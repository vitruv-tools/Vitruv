package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.emf.ecore.EClass
import static org.eclipse.emf.compare.match.eobject.EqualityHelperExtensionProvider.SpecificMatch.*
import org.eclipse.emf.compare.match.eobject.EqualityHelperExtensionProvider.SpecificMatch
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.compare.utils.IEqualityHelper
import tools.vitruv.testutils.matchers.ModelDeepEqualityOption.EqualityStrategy
import java.util.Set
import static extension tools.vitruv.testutils.matchers.MatcherUtil.*

@FinalFieldsConstructor
package class EqualsStrategy extends EqualityStrategy {
	val Set<EClass> targetEClasses
	
	override SpecificMatch compare(EObject left, EObject right, IEqualityHelper equalityHelper) {
		if (!targetEClasses.contains(left.eClass) || !targetEClasses.contains(right.eClass)) UNKNOWN
		else if (left == right) MATCH
		else UNMATCH
	}
	
	override getTargetNsUris() {
		targetEClasses.map[EPackage.nsURI].toSet
	}
	
	override describeTo(extension StringBuilder builder) {
		append("used equals() to compare ").joinSemantic(targetEClasses, 'and')[append(plural(name))]
	}
}