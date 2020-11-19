package tools.vitruv.dsls.reactions.tests.complexTests

import allElementTypes.Root
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests

import static tools.vitruv.testutils.domains.AllElementTypesCreators.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static org.hamcrest.CoreMatchers.nullValue
import static org.hamcrest.CoreMatchers.is
import org.junit.jupiter.api.Test

class ReactionsRollbackTests extends AbstractAllElementTypesReactionsTests {
	static val SOURCE_MODEL = getProjectModelPath("EachTestModelSource");
	static val TARGET_MODEL = getProjectModelPath("EachTestModelTarget");

	@Test
	def void testReverse() {
		createAndSynchronizeModel(SOURCE_MODEL, newRoot => [
			id = 'EachTestModelSource'
		]);
		assertThat(resourceAt(SOURCE_MODEL), containsModelOf(resourceAt(TARGET_MODEL)))

		val result = saveAndSynchronizeChanges(Root.from(SOURCE_MODEL).record [
			singleValuedContainmentEReference = newNonRoot => [
				id = 'testId'
			]
		])
		virtualModel.reverseChanges(result);
		
		assertThat(Root.from(SOURCE_MODEL).singleValuedContainmentEReference, is(nullValue))
		assertThat(Root.from(TARGET_MODEL).singleValuedContainmentEReference, is(nullValue))
	}
}
