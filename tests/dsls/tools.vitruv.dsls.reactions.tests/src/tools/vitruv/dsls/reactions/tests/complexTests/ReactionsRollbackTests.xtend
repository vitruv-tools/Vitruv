package tools.vitruv.dsls.reactions.tests.complexTests

import allElementTypes.Root

import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static org.hamcrest.CoreMatchers.nullValue
import static org.hamcrest.CoreMatchers.is
import org.junit.jupiter.api.Test
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes
import tools.vitruv.dsls.reactions.tests.ReactionsExecutionTest
import tools.vitruv.dsls.reactions.tests.TestReactionsCompiler

class ReactionsRollbackTests extends ReactionsExecutionTest {
	static val SOURCE_MODEL = 'RollbackSource'.allElementTypes
	static val TARGET_MODEL = 'RollbackTarget'.allElementTypes

	override protected createCompiler(TestReactionsCompiler.Factory factory) {
		factory.createCompiler [
			reactions = #["/tools/vitruv/dsls/reactions/tests/AllElementTypesRedundancy.reactions"]
			changePropagationSegments = #["simpleChangesTests"]
		]
	}

	@Test
	def void testReverse() {
		resourceAt(SOURCE_MODEL).propagate [
			contents += aet.Root => [id = 'Rollback']
		]
		assertThat(resourceAt(SOURCE_MODEL), containsModelOf(resourceAt(TARGET_MODEL)))

		val result = Root.from(SOURCE_MODEL).propagate [
			singleValuedContainmentEReference = aet.NonRoot => [
				id = 'testId'
			]
		]
		virtualModel.reverseChanges(result)

		assertThat(Root.from(SOURCE_MODEL).singleValuedContainmentEReference, is(nullValue))
		assertThat(Root.from(TARGET_MODEL).singleValuedContainmentEReference, is(nullValue))
	}
}
