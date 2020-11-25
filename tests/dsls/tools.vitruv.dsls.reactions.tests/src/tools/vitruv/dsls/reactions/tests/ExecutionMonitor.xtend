package tools.vitruv.dsls.reactions.tests

import java.util.EnumSet
import java.util.Set
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import static java.util.Collections.emptySet
import static java.util.EnumSet.allOf
import static java.util.EnumSet.noneOf

interface ExecutionMonitor<ExecutionType extends Enum<ExecutionType>> {
	def Set<ExecutionType> getObservedExecutions()

	static def <ExecutionType extends Enum<ExecutionType>> Matcher<ExecutionMonitor<ExecutionType>> observedExecutions(
		ExecutionType... types) {
		new ObservedExecutionsMatcher(
			if (types.length == 0) emptySet() else EnumSet.of(types.get(0), types)
		)
	}

	static def <ExecutionType extends Enum<ExecutionType>> Matcher<ExecutionMonitor<ExecutionType>> observedExecutions(
		Set<ExecutionType> types) {
		new ObservedExecutionsMatcher(types)
	}
}

@FinalFieldsConstructor
package class ObservedExecutionsMatcher<ExecutionType extends Enum<ExecutionType>> extends TypeSafeMatcher<ExecutionMonitor<ExecutionType>> {
	val Set<ExecutionType> expectedExecutions

	override protected matchesSafely(ExecutionMonitor<ExecutionType> item) {
		item.observedExecutions == expectedExecutions
	}

	override describeTo(Description description) {
		description.appendText("the execution monitor to have observed ").appendValueList('[', ', ', ']',
			expectedExecutions)
	}

	override describeMismatchSafely(ExecutionMonitor<ExecutionType> item, Description mismatchDescription) {
		var Set<ExecutionType> occuredButShouldNot = null
		var Set<ExecutionType> occuredNotButShould = null
		if (item.observedExecutions.isEmpty) {
			occuredButShouldNot = emptySet()
			occuredNotButShould = expectedExecutions
		} else {
			val itemClass = item.observedExecutions.get(0).class as Class<ExecutionType>
			occuredButShouldNot = noneOf(itemClass)
			occuredNotButShould = noneOf(itemClass)
			for (execution : allOf(itemClass)) {
				if (expectedExecutions.contains(execution) && !item.observedExecutions.contains(execution)) {
					occuredNotButShould.add(execution)
				} else if (item.observedExecutions.contains(execution) && !expectedExecutions.contains(execution)) {
					occuredButShouldNot.add(execution)
				}
			}
		}
		if (!occuredNotButShould.isEmpty) {
			mismatchDescription.appendText("these expected executions were missing: ").appendValueList('[', ', ', ']',
				occuredNotButShould)
		}
		if (!occuredButShouldNot.isEmpty) {
			if (!occuredNotButShould.isEmpty) {
				mismatchDescription.appendText(" and ")
			}
			mismatchDescription.appendText("these observed executions were not expected: ").appendValueList('[', ', ',
				']', occuredButShouldNot)
		}
	}
}
