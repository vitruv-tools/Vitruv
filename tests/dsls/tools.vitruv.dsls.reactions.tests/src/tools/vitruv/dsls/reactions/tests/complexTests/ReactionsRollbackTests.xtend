package tools.vitruv.dsls.reactions.tests.complexTests

import allElementTypes.Root
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests

import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static org.hamcrest.CoreMatchers.nullValue
import static org.hamcrest.CoreMatchers.is
import org.junit.jupiter.api.Test
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes

class ReactionsRollbackTests extends AbstractAllElementTypesReactionsTests {
	static val SOURCE_MODEL = 'RollbackSource'.allElementTypes
	static val TARGET_MODEL = 'RollbackTarget'.allElementTypes

	@Test
	def void testReverse() {
		createAndSynchronizeModel(SOURCE_MODEL, newRoot => [
			id = 'Rollback'
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
