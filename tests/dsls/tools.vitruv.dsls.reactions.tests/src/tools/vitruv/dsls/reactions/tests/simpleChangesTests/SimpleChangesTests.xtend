package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.Root
import org.eclipse.emf.common.util.ECollections
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor.ChangeType.*

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.dsls.reactions.tests.ExecutionMonitor.observedExecutions
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static org.hamcrest.CoreMatchers.is
import static tools.vitruv.testutils.matchers.ModelMatchers.exists
import static tools.vitruv.testutils.matchers.ModelMatchers.doesNotExist
import org.junit.jupiter.api.Disabled
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes
import tools.vitruv.dsls.reactions.tests.ReactionsExecutionTest
import tools.vitruv.dsls.reactions.tests.TestReactionsCompiler
import static extension tools.vitruv.testutils.matchers.CorrespondenceMatchers.hasNoCorrespondences

class SimpleChangesTests extends ReactionsExecutionTest {
	static val SOURCE_MODEL = 'SimpleChangeSource'.allElementTypes
	static val TARGET_MODEL = "SimpleChangeTarget".allElementTypes
	static val FURTHER_SOURCE_MODEL = 'FurtherSource'.allElementTypes
	static val FURTHER_TARGET_MODEL = 'FurtherTarget'.allElementTypes

	String[] nonContainmentNonRootIds = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"]

	override protected createCompiler(TestReactionsCompiler.Factory factory) {
		factory.createCompiler [
			reactions = #["/tools/vitruv/dsls/reactions/tests/AllElementTypesRedundancy.reactions"]
			changePropagationSegments = #["simpleChangesTests"]
		]
	}

	@BeforeEach
	def createRoot() {
		resourceAt(SOURCE_MODEL).propagate [
			contents += aet.Root => [
				id = 'EachTestModelSource'
				nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
					id = 'NonRootObjectContainer'
					nonRootObjectsContainment += nonContainmentNonRootIds.map [ nonRootId |
						aet.NonRoot => [id = nonRootId]
					]
				]
			]
		]

		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))

		executionMonitor.reset()
	}

	private static def getExecutionMonitor() {
		SimpleChangesTestsExecutionMonitor.instance
	}

	private def nonRootWithId(Root rootObject, String searchId) {
		rootObject.nonRootObjectContainerHelper.nonRootObjectsContainment.findFirst [
			it.id == searchId
		]
	}

	@Test
	@Disabled("Unset does not produce any change event at the moment") // TODO HK (Change MM)
	def void testUnsetSingleValuedEAttribute() {
		Root.from(SOURCE_MODEL).propagate [
			singleValuedEAttribute = null
		]

		assertThat(executionMonitor, observedExecutions(UnsetEAttribute))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	@Disabled
	def void testUnsetSingleValuedNonContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			singleValuedNonContainmentEReference = nonRootWithId(nonContainmentNonRootIds.get(1))
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			singleValuedNonContainmentEReference = null
		]

		assertThat(executionMonitor, observedExecutions(UnsetNonContainmentEReference))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testUpdateSingleValuedEAttribute() {
		Root.from(SOURCE_MODEL).propagate [
			singleValuedEAttribute = -1
		]

		assertThat(executionMonitor, observedExecutions(UpdateSingleValuedEAttribute))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testUpdateSingleValuedPrimitiveTypeEAttribute() {
		Root.from(SOURCE_MODEL).propagate [
			singleValuedPrimitiveTypeEAttribute = -1
		]

		assertThat(executionMonitor, observedExecutions(UpdateSingleValuedPrimitveTypeEAttribute))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testCreateSingleValuedContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			singleValuedContainmentEReference = aet.NonRoot => [id = "singleValuedContainmentNonRootTest"]
		]

		assertThat(executionMonitor, observedExecutions(CreateEObject, CreateNonRootEObjectSingle))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testDeleteSingleValuedContainmentEReference() {
		val oldElement = aet.NonRoot => [id = "singleValuedContainmentNonRoot"]
		Root.from(SOURCE_MODEL).propagate [
			singleValuedContainmentEReference = oldElement
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			singleValuedContainmentEReference = null
		]

		assertThat(executionMonitor, observedExecutions(DeleteEObject, DeleteNonRootEObjectSingle))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
		assertThat(oldElement, hasNoCorrespondences)
	}

	@Test
	def void testReplaceSingleValuedContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			singleValuedContainmentEReference = aet.NonRoot => [id = "singleValuedContainmentNonRootBefore"]
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			singleValuedContainmentEReference = aet.NonRoot => [id = "singleValuedContainmentNonRootAfter"]
		]

		assertThat(executionMonitor,
			observedExecutions(DeleteNonRootEObjectSingle, DeleteEObject, CreateNonRootEObjectSingle, CreateEObject))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testSetSingleValuedNonContainmentEReference() {
		val testId = nonContainmentNonRootIds.get(1)
		Root.from(SOURCE_MODEL).propagate [
			singleValuedNonContainmentEReference = nonRootWithId(testId)
		]

		assertThat(executionMonitor, observedExecutions(UpdateSingleValuedNonContainmentEReference))
		assertThat(Root.from(SOURCE_MODEL).singleValuedNonContainmentEReference.id, is(testId))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testReplaceSingleValuedNonContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			singleValuedNonContainmentEReference = nonRootWithId(nonContainmentNonRootIds.get(0))
		]
		executionMonitor.reset()
		val testId = nonContainmentNonRootIds.get(1)
		Root.from(SOURCE_MODEL).propagate [
			singleValuedNonContainmentEReference = nonRootWithId(testId)
		]

		assertThat(executionMonitor, observedExecutions(UpdateSingleValuedNonContainmentEReference))
		assertThat(Root.from(SOURCE_MODEL).singleValuedNonContainmentEReference.id, is(testId))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testAddMultiValuedEAttribute() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedEAttribute += 1
		]

		assertThat(executionMonitor, observedExecutions(InsertEAttributeValue))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testDeleteMultiValuedEAttribute() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedEAttribute += #[1, 2]
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			multiValuedEAttribute -= 1
		]

		assertThat(executionMonitor, observedExecutions(RemoveEAttributeValue))
		assertThat(Root.from(SOURCE_MODEL).multiValuedEAttribute.get(0), is(2))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testReplaceMultiValuedEAttribute() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedEAttribute += #[1, 2]
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			multiValuedEAttribute.set(1, 3)
		]

		// TODO HK (Change MM) This should not be, should be one event
		assertThat(executionMonitor, observedExecutions(RemoveEAttributeValue, InsertEAttributeValue))
		assertThat(Root.from(SOURCE_MODEL).multiValuedEAttribute.get(1), is(3))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testAddMultiValuedContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedContainmentEReference += aet.NonRoot => [id = "multiValuedContainmentNonRootTest"]
		]

		assertThat(executionMonitor, observedExecutions(CreateNonRootEObjectInList, CreateEObject))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testDeleteMultiValuedContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedContainmentEReference += aet.NonRoot => [id = "multiValuedContainmentNonRootTest"]
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			multiValuedContainmentEReference.removeIf[it.id == "multiValuedContainmentNonRootTest"]
		]

		assertThat(executionMonitor, observedExecutions(DeleteNonRootEObjectInList, DeleteEObject))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testReplaceMultiValuedContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedContainmentEReference += aet.NonRoot => [id = "multiValuedContainmentNonRootBefore"]
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			multiValuedContainmentEReference.set(0, aet.NonRoot => [id = "multiValuedContainmentNonRootAfter"])
		]

		assertThat(executionMonitor,
			observedExecutions(DeleteNonRootEObjectInList, DeleteEObject, CreateNonRootEObjectInList, CreateEObject))
		assertThat(Root.from(SOURCE_MODEL).multiValuedContainmentEReference.last.id,
			is("multiValuedContainmentNonRootAfter"))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testInsertMultiValuedNonContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedNonContainmentEReference += nonRootWithId(nonContainmentNonRootIds.get(0))
		]

		assertThat(executionMonitor, observedExecutions(InsertNonContainmentEReference))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testRemoveMultiValuedNonContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedNonContainmentEReference += nonRootWithId(nonContainmentNonRootIds.get(1))
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			multiValuedNonContainmentEReference -= nonRootWithId(nonContainmentNonRootIds.get(1))
		]

		assertThat(executionMonitor, observedExecutions(RemoveNonContainmentEReference))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testReplaceMultiValuedNonContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedNonContainmentEReference += nonRootWithId(nonContainmentNonRootIds.get(0))
			multiValuedNonContainmentEReference += nonRootWithId(nonContainmentNonRootIds.get(1))
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			multiValuedNonContainmentEReference.set(1, nonRootWithId(nonContainmentNonRootIds.get(2)))
		]

		// TODO HK (Change MM) this should not be... should be one event: ReplaceNonContainmentEReference!
		assertThat(executionMonitor, observedExecutions(RemoveNonContainmentEReference, InsertNonContainmentEReference))
		Root.from(SOURCE_MODEL) => [
			assertThat(multiValuedNonContainmentEReference.size, is(2))
			assertThat(multiValuedNonContainmentEReference.get(0).id, is(nonContainmentNonRootIds.get(0)))
			assertThat(multiValuedNonContainmentEReference.get(1).id, is(nonContainmentNonRootIds.get(2)))
		]
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	@Disabled("Permute operations are not supported by now? No EChange produced") // TODO HK (Change MM) 
	def void testPermuteMultiValuedNonContainmentEReference() {
		Root.from(SOURCE_MODEL).propagate [
			multiValuedNonContainmentEReference += nonContainmentNonRootIds.map[id|nonRootWithId(id)]
		]
		executionMonitor.reset()
		Root.from(SOURCE_MODEL).propagate [
			ECollections.sort(multiValuedNonContainmentEReference, [a, b|- a.id.compareTo(b.id)])
		]

		assertThat(executionMonitor, observedExecutions(PermuteNonContainmentEReference))
		assertThat(resourceAt(TARGET_MODEL), containsModelOf(resourceAt(SOURCE_MODEL)))
	}

	@Test
	def void testDeleteEachTestModel() {
		assertThat(resourceAt(SOURCE_MODEL), exists())
		assertThat(resourceAt(TARGET_MODEL), exists())

		resourceAt(SOURCE_MODEL).propagate[delete(emptyMap)]

		assertThat(resourceAt(SOURCE_MODEL), doesNotExist())
		assertThat(resourceAt(TARGET_MODEL), doesNotExist())
	}

	@Test
	def void testCreateFurtherModel() {
		resourceAt(FURTHER_SOURCE_MODEL).propagate [
			contents += aet.Root => [
				id = "Further_Source_Test_Model"
			]
		]

		assertThat(resourceAt(FURTHER_TARGET_MODEL), containsModelOf(resourceAt(FURTHER_SOURCE_MODEL)))
	}

	@Test
	def void testDeleteFurtherModel() {
		resourceAt(FURTHER_SOURCE_MODEL).propagate [
			contents += aet.Root => [
				id = "Further_Source_Test_Model"
			]
		]

		assertThat(resourceAt(FURTHER_TARGET_MODEL), exists())
		assertThat(resourceAt(FURTHER_SOURCE_MODEL), containsModelOf(resourceAt(FURTHER_TARGET_MODEL)))

		resourceAt(FURTHER_SOURCE_MODEL).propagate[delete(emptyMap)]

		assertThat(resourceAt(FURTHER_SOURCE_MODEL), doesNotExist())
		assertThat(resourceAt(FURTHER_TARGET_MODEL), doesNotExist())
	}
}
