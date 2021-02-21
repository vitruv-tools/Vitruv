package tools.vitruv.testutils.tests.matchers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import org.eclipse.emf.ecore.EObject
import tools.vitruv.testutils.printing.ModelPrinterChange
import tools.vitruv.testutils.TestLogging
import tools.vitruv.testutils.printing.UseModelPrinter
import tools.vitruv.testutils.printing.UnsetFeaturesHidingModelPrinter
import allElementTypes.NonRoot
import allElementTypes.Root
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures
import static allElementTypes.AllElementTypesPackage.Literals.*
import allElementTypes.impl.NonRootImpl
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.pcm
import tools.vitruv.testutils.matchers.EqualityStrategy
import static tools.vitruv.testutils.matchers.EqualityStrategy.Result.*
import pcm_mockup.PInterface
import static org.hamcrest.CoreMatchers.is

@UseModelPrinter(UnsetFeaturesHidingModelPrinter)
@ExtendWith(RegisterMetamodelsInStandalone, ModelPrinterChange, TestLogging)
class ModelDeepEqualityMatcherTest {
	@ParameterizedTest(name = "{0}")
	@MethodSource("equalObjects")
	@DisplayName("accepts equal objects as equal")
	def void recognizesEquals(String name, EObject left, EObject right) {
		assertDoesNotThrow [
			assertThat(left, equalsDeeply(right))
		]	
	}
	
	def static Stream<Arguments> equalObjects() {
		Stream.of(
			Arguments.of("simple object", aet.Root => [id = 'id'], aet.Root => [id = 'id']),
			Arguments.of(
				"with ordered multi-valued attribute",
				aet.Root => [id = 'root'; multiValuedEAttribute += #[1, 2, 42, 8]],
				aet.Root => [id = 'root'; multiValuedEAttribute += #[1, 2, 42, 8]]
			),
			Arguments.of(
				"with unordered multi-valued attribute (same order)",
				aet.Root => [id = 'root'; multiValuedUnorderedEAttribute += #[1, 2, 42, 8]],
				aet.Root => [id = 'root'; multiValuedUnorderedEAttribute += #[1, 2, 42, 8]]
			),
			Arguments.of(
				"with unordered multi-valued attribute (different order)",
				aet.Root => [id = 'root'; multiValuedUnorderedEAttribute += #[1, 2, 42, 8]],
				aet.Root => [id = 'root'; multiValuedUnorderedEAttribute += #[8, 42, 2, 1]]
			),
			Arguments.of(
				"with single-valued reference to the outside",
				aet.Root => [id = 'root'; singleValuedNonContainmentEReference = 
					aet.NonRoot.inDifferentRoot => [id = 'sub']
				],
				aet.Root => [id = 'root'; singleValuedNonContainmentEReference = 
					aet.NonRoot.inDifferentRoot => [id = 'sub']
				]
			),
			Arguments.of(
				"with single-valued reference to the outside (same id but different content)",
				aet.Root => [id = 'root'; singleValuedNonContainmentEReference = 
					aet.NonRoot.inDifferentRoot => [id = 'sub'; value = 'different']
				],
				aet.Root => [id = 'root'; singleValuedNonContainmentEReference = 
					aet.NonRoot.inDifferentRoot => [id = 'sub'; value = 'test']
				]			
			),
			Arguments.of(
				"with ordered multi-valued reference to the outside",
				aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
					aet.NonRoot.inDifferentRoot => [id = 'sub1'],
					aet.NonRoot.inDifferentRoot => [id = 'sub2'],
					aet.NonRoot.inDifferentRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
					aet.NonRoot.inDifferentRoot => [id = 'sub1'],
					aet.NonRoot.inDifferentRoot => [id = 'sub2'],
					aet.NonRoot.inDifferentRoot => [id = 'sub3']
				]]
			),
			Arguments.of(
				"with unordered multi-valued reference to the outside (same order)",
				aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
					aet.NonRoot.inDifferentRoot => [id = 'sub1'],
					aet.NonRoot.inDifferentRoot => [id = 'sub2'],
					aet.NonRoot.inDifferentRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
					aet.NonRoot.inDifferentRoot => [id = 'sub1'],
					aet.NonRoot.inDifferentRoot => [id = 'sub2'],
					aet.NonRoot.inDifferentRoot => [id = 'sub3']
				]]
			),
			Arguments.of(
				"with unordered multi-valued reference to the outside (different order)",
				aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
					aet.NonRoot.inDifferentRoot => [id = 'sub1'],
					aet.NonRoot.inDifferentRoot => [id = 'sub2'],
					aet.NonRoot.inDifferentRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
					aet.NonRoot.inDifferentRoot => [id = 'sub3'],
					aet.NonRoot.inDifferentRoot => [id = 'sub1'],
					aet.NonRoot.inDifferentRoot => [id = 'sub2']
				]]
			),
			Arguments.of(
				"with single-valued reference to the inside",
				aet.Root => [id = 'root'; singleValuedNonContainmentEReference = 
					nonRootInSameRoot => [id = 'sub']
				],
				aet.Root => [id = 'root'; singleValuedNonContainmentEReference = 
					nonRootInSameRoot => [id = 'sub']
				]
			),
			Arguments.of(
				"with ordered multi-valued reference to the inside",
				aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
					nonRootInSameRoot => [id = 'sub1'],
					nonRootInSameRoot => [id = 'sub2'],
					nonRootInSameRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
					nonRootInSameRoot => [id = 'sub1'],
					nonRootInSameRoot => [id = 'sub2'],
					nonRootInSameRoot => [id = 'sub3']
				]]
			),
			Arguments.of(
				"with unordered multi-valued reference to the inside (same order)",
				aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
					nonRootInSameRoot => [id = 'sub1'],
					nonRootInSameRoot => [id = 'sub2'],
					nonRootInSameRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
					nonRootInSameRoot => [id = 'sub1'],
					nonRootInSameRoot => [id = 'sub2'],
					nonRootInSameRoot => [id = 'sub3']
				]]
			),
			Arguments.of(
				"with unordered multi-valued reference to the inside (different order)",
				aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
					nonRootInSameRoot => [id = 'sub1'],
					nonRootInSameRoot => [id = 'sub2'],
					nonRootInSameRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
					nonRootInSameRoot => [id = 'sub3'],
					nonRootInSameRoot => [id = 'sub1'],
					nonRootInSameRoot => [id = 'sub2']
				]]
			),
			Arguments.of(
				"with single-valued containment reference",
				aet.Root => [id = 'root'; singleValuedContainmentEReference = aet.NonRoot => [id = 'sub']],
				aet.Root => [id = 'root'; singleValuedContainmentEReference = aet.NonRoot => [id = 'sub']]
			),
			Arguments.of(
				"with ordered multi-valued containment reference",
				aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
					aet.NonRoot => [id = 'sub1'],
					aet.NonRoot => [id = 'sub2'],
					aet.NonRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
					aet.NonRoot => [id = 'sub1'],
					aet.NonRoot => [id = 'sub2'],
					aet.NonRoot => [id = 'sub3']
				]]
			),
			Arguments.of(
				"with unordered multi-valued containment reference (same order)",
				aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
					aet.NonRoot => [id = 'sub1'],
					aet.NonRoot => [id = 'sub2'],
					aet.NonRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
					aet.NonRoot => [id = 'sub1'],
					aet.NonRoot => [id = 'sub2'],
					aet.NonRoot => [id = 'sub3']
				]]
			),
			Arguments.of(
				"with unordered multi-valued containment reference (different order)",
				aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
					aet.NonRoot => [id = 'sub1'],
					aet.NonRoot => [id = 'sub2'],
					aet.NonRoot => [id = 'sub3']
				]],
				aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
					aet.NonRoot => [id = 'sub3'],
					aet.NonRoot => [id = 'sub1'],
					aet.NonRoot => [id = 'sub2']
				]]
			)
		)
	}
	
	@Test
	@DisplayName("recognizes a changed single-valued attribute")
	def void changedAttribute() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'different'], equalsDeeply(aet.Root => [id = 'test']))
		]
		assertThat(exception.message, containsString('wrong value: "different"'))
	}


	@Test
	@DisplayName("recognizes a different order in a multi-valued attribute")
	def void attributeListReorded() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedEAttribute += #[1, 2, 42, 8]],
				equalsDeeply(aet.Root => [id = 'root'; multiValuedEAttribute += #[1, 8, 2, 42]])
			)
		]
		assertThat(exception.message, containsString('different position for: 8'))
	}
	
	@Test
	@DisplayName("recognizes a changed single-valued reference to the outside")
	def void changedReferenceToOutside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; singleValuedNonContainmentEReference =
				aet.NonRoot.inDifferentRoot => [id = 'different']
			], equalsDeeply(aet.Root => [id = 'root'; singleValuedNonContainmentEReference =
				aet.NonRoot.inDifferentRoot => [id = 'sub']
			]))
		]
		assertThat(exception.message, containsString('wrong value: NonRoot(id="different")'))
	}
	
	@Test
	@DisplayName("recognizes a changed single-valued reference to the inside")
	def void changedReferenceToInside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; singleValuedNonContainmentEReference =
				nonRootInSameRoot => [id = 'different']
			], equalsDeeply(aet.Root => [id = 'root'; singleValuedNonContainmentEReference =
				nonRootInSameRoot => [id = 'sub']
			]))
		]
		assertThat(exception.message, containsString('wrong value: NonRoot(id="different")'))
	}
	
	@Test
	@DisplayName("recognizes a reordered multi-valued reference to the outside")
	def void reorderedReferenceToOutside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub2'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3'],
				aet.NonRoot.inDifferentRoot => [id = 'sub1']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub2'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('different position for: NonRoot(id="sub1")'))
	}
	
	@Test
	@DisplayName("recognizes a reordered multi-valued reference to the inside")
	def void reorderedReferenceToInside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub2'],
				nonRootInSameRoot => [id = 'sub3'],
				nonRootInSameRoot => [id = 'sub1']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub2'],
				nonRootInSameRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('different position for: NonRoot(id="sub1")'))
	}
	
	@Test
	@DisplayName("recognizes an insertion into a multi-valued unordered reference to the outside")
	def void insertionInUnorderedReferenceToOutside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub2'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('unexpected value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes an insertion into a multi-valued unordered ereference to the inside")
	def void insertionInUnuorderedReferenceToInside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub2'],
				nonRootInSameRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub3']
			]; nonRootInSameRoot => [id = 'sub2']]))
		]
		assertThat(exception.message, containsString('unexpected value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes a removal from a multi-valued unordered reference to the outside")
	def void removalFromUnorderedReferenceToOutside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub2'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('missing the value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes a removal from a multi-valued unordered reference to the inside")
	def void removalFromUnorderedReferenceToInside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub3']
			]; nonRootInSameRoot => [id = 'sub2']
			], equalsDeeply(aet.Root => [id = 'root'; multiValuedUnorderedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub2'],
				nonRootInSameRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('missing the value: NonRoot(id="sub2")'))
	}
	
		@Test
	@DisplayName("recognizes an insertion into a multi-valued reference to the outside")
	def void insertionInReferenceToOutside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub2'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('unexpected value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes an insertion into a multi-valued reference to the inside")
	def void insertionInReferenceToInside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub2'],
				nonRootInSameRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub3']
			]; nonRootInSameRoot => [id = 'sub2']]))
		]
		assertThat(exception.message, containsString('unexpected value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes a removal from a multi-valued reference to the outside")
	def void removalFromReferenceToOutside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				aet.NonRoot.inDifferentRoot => [id = 'sub1'],
				aet.NonRoot.inDifferentRoot => [id = 'sub2'],
				aet.NonRoot.inDifferentRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('missing the value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes a removal from a multi-valued reference to the inside")
	def void removalFromReferenceToInside() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub3']
			]; nonRootInSameRoot => [id = 'sub2']
			], equalsDeeply(aet.Root => [id = 'root'; multiValuedNonContainmentEReference += #[
				nonRootInSameRoot => [id = 'sub1'],
				nonRootInSameRoot => [id = 'sub2'],
				nonRootInSameRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('missing the value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes an id-changed single-valued containment reference")
	def void idChangedSingleContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; singleValuedContainmentEReference =
				aet.NonRoot => [id = 'different']
			], equalsDeeply(aet.Root => [id = 'root'; singleValuedContainmentEReference =
				aet.NonRoot => [id = 'sub']
			]))
		]
		assertThat(exception.message, containsString('unexpected value: NonRoot(id="different")'))
		assertThat(exception.message, containsString('missing the value: NonRoot(id="sub")'))
	}
	
	@Test
	@DisplayName("recognizes a value-changed single-valued containment reference")
	def void valueSingleChangedContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; singleValuedContainmentEReference =
				aet.NonRoot => [id = 'sub'; value = 'different']
			], equalsDeeply(aet.Root => [id = 'root'; singleValuedContainmentEReference =
				aet.NonRoot => [id = 'sub'; value = 'test']
			]))
		]
		assertThat(exception.message, containsString('wrong value: "different"'))
	}
	
	@Test
	@DisplayName("recognizes a reordered multi-valued containment reference")
	def void reorderedContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'sub2'],
				aet.NonRoot => [id = 'sub3'],
				aet.NonRoot => [id = 'sub1']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub2'],
				aet.NonRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('different position for: NonRoot(id="sub1")'))
	}
	
	@Test
	@DisplayName("recognizes an insertion into a multi-valued containment reference")
	def void insertionInContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub2'],
				aet.NonRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('unexpected value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes a removal from a multi-valued containment reference")
	def void removalFromContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub2'],
				aet.NonRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('missing the value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes a value change in a multi-valued containment reference")
	def void valueChangedInMultiContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'; value = 'a'],
				aet.NonRoot => [id = 'sub2'; value = 'different'],
				aet.NonRoot => [id = 'sub3'; value = 'c' ]
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'; value = 'a'],
				aet.NonRoot => [id = 'sub2'; value = 'b'],
				aet.NonRoot => [id = 'sub3'; value = 'c']
			]]))
		]
		assertThat(exception.message, containsString('wrong value: "different"'))
	}
	
	@Test
	@DisplayName("recognizes an insertion into a multi-valued undordered containment reference")
	def void insertionInUnorderedContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub2'],
				aet.NonRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('unexpected value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes a removal from a multi-valued unordered containment reference")
	def void removalFromUnorderedContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub3']
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'],
				aet.NonRoot => [id = 'sub2'],
				aet.NonRoot => [id = 'sub3']
			]]))
		]
		assertThat(exception.message, containsString('missing the value: NonRoot(id="sub2")'))
	}
	
	@Test
	@DisplayName("recognizes a value change in a multi-valued containment reference")
	def void valueChangedInUnorderedMultiContainmentReference() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'; value = 'a'],
				aet.NonRoot => [id = 'sub2'; value = 'different'],
				aet.NonRoot => [id = 'sub3'; value = 'c' ]
			]], equalsDeeply(aet.Root => [id = 'root'; multiValuedUnorderedContainmentEReference += #[
				aet.NonRoot => [id = 'sub1'; value = 'a'],
				aet.NonRoot => [id = 'sub2'; value = 'b'],
				aet.NonRoot => [id = 'sub3'; value = 'c']
			]]))
		]
		assertThat(exception.message, containsString('wrong value: "different"'))
	}
	
	@Test
	@DisplayName("reports a value change only via the containment reference (single-valued)")
	def void reportsValueChangeOnlyViaSingleContainmentReference() {
		val actualNonRoot = aet.NonRoot => [id = 'sub'; value = 'different']
		val expectedNonRoot = aet.NonRoot => [id = 'sub'; value = 'test'] 
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root';
				singleValuedContainmentEReference = actualNonRoot
				singleValuedNonContainmentEReference = actualNonRoot
				multiValuedNonContainmentEReference += actualNonRoot
				multiValuedUnorderedNonContainmentEReference += actualNonRoot
			], equalsDeeply(aet.Root => [id = 'root';
				singleValuedContainmentEReference = expectedNonRoot
				singleValuedNonContainmentEReference = expectedNonRoot
				multiValuedNonContainmentEReference += expectedNonRoot
				multiValuedUnorderedNonContainmentEReference += expectedNonRoot
			]))
		]
		assertThat(exception.message, containsExactDifferences(
			'.singleValuedContainmentEReference (NonRoot(id="sub")).value had the wrong value: "different"'
		))
	}	
	
	@Test
	@DisplayName("reports a value change only via the containment reference (multi-valued)")
	def void reportsValueChangeOnlyViaMultiContainmentReference() {
		val actualNonRoot = aet.NonRoot => [id = 'sub'; value = 'different']
		val expectedNonRoot = aet.NonRoot => [id = 'sub'; value = 'test'] 
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root';
				multiValuedContainmentEReference += actualNonRoot
				singleValuedNonContainmentEReference = actualNonRoot
				multiValuedNonContainmentEReference += actualNonRoot
				multiValuedUnorderedNonContainmentEReference += actualNonRoot
			], equalsDeeply(aet.Root => [id = 'root';
				multiValuedContainmentEReference += expectedNonRoot
				singleValuedNonContainmentEReference = expectedNonRoot
				multiValuedNonContainmentEReference += expectedNonRoot
				multiValuedUnorderedNonContainmentEReference += expectedNonRoot
			]))
		]
		assertThat(exception.message, containsExactDifferences(
			'.multiValuedContainmentEReference[0] (NonRoot(id="sub")).value had the wrong value: "different"'
		))
	}
	
	@Test
	@DisplayName("reports a value change only via the containment reference (multi-valued unordered)")
	def void reportsValueChangeOnlyViaMultiUnorderedContainmentReference() {
		val actualNonRoot = aet.NonRoot => [id = 'sub'; value = 'different']
		val expectedNonRoot = aet.NonRoot => [id = 'sub'; value = 'test'] 
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root';
				multiValuedUnorderedContainmentEReference += actualNonRoot
				singleValuedNonContainmentEReference = actualNonRoot
				multiValuedNonContainmentEReference += actualNonRoot
				multiValuedUnorderedNonContainmentEReference += actualNonRoot
			], equalsDeeply(aet.Root => [id = 'root';
				multiValuedUnorderedContainmentEReference += expectedNonRoot
				singleValuedNonContainmentEReference = expectedNonRoot
				multiValuedNonContainmentEReference += expectedNonRoot
				multiValuedUnorderedNonContainmentEReference += expectedNonRoot
			]))
		]
		assertThat(exception.message, containsExactDifferences(
			'.multiValuedUnorderedContainmentEReference{0} (NonRoot(id="sub")).value had the wrong value: "different"'
		))
	}
	
	@Test
	@DisplayName("reports a value change via a non-containment reference if the containment reference is ignored")
	def void reportsValueChangeViaNonContainmentIfNecessary() {
		val actualNonRoot = aet.NonRoot => [id = 'sub'; value = 'different']
		val expectedNonRoot = aet.NonRoot => [id = 'sub'; value = 'test'] 
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [id = 'root';
				singleValuedContainmentEReference = actualNonRoot
				singleValuedNonContainmentEReference = actualNonRoot
			], equalsDeeply(aet.Root => [id = 'root';
				singleValuedContainmentEReference = expectedNonRoot
				singleValuedNonContainmentEReference = expectedNonRoot
			], ignoringFeatures(ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE)))
		]
		assertThat(exception.message, containsExactDifferences(
			'.singleValuedNonContainmentEReference (NonRoot(id="sub")).value had the wrong value: "different"'
		))
	}
	
	@Test
	@DisplayName("allows to change the value comparison to using #equals()")
	def void comparisonUsingEquals() {
		assertDoesNotThrow [
			assertThat(aet.Root => [
				id = 'root'
				singleValuedNonContainmentEReference = new ValueBasedNonRoot().inDifferentRoot => [
					id = 'different'
					value = 'test'
				]
			], equalsDeeply(aet.Root => [
				id = 'root'
				singleValuedNonContainmentEReference = new ValueBasedNonRoot().inDifferentRoot => [
					id = 'test'
					value = 'test'
				]
			], usingEqualsForReferencesTo(NON_ROOT)))
		]
	}
	
	@Test
	@DisplayName("recognizes changed values when changing the value comparison to using #equals()")
	def void comparisonUsingEqualsFails() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [
				id = 'root'
				singleValuedNonContainmentEReference = new ValueBasedNonRoot().inDifferentRoot => [
					id = 'test'
					value = 'different'
				]
			], equalsDeeply(aet.Root => [
				id = 'root'
				singleValuedNonContainmentEReference = new ValueBasedNonRoot().inDifferentRoot => [
					id = 'test'
					value = 'test'
				]
			], usingEqualsForReferencesTo(NON_ROOT)))
		]
		assertThat(exception.message, containsString('wrong value: NonRoot(id="test")'))
	}
	
	@Test
	@DisplayName("recognizes changed values when changing the value comparison to using #equals()")
	def void comparisonUsingEqualsFails2() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.Root => [
				id = 'root'
				singleValuedNonContainmentEReference = aet.NonRoot.inDifferentRoot => [
					id = 'test'
					value = 'test'
				]
			], equalsDeeply(aet.Root => [
				id = 'root'
				singleValuedNonContainmentEReference = aet.NonRoot.inDifferentRoot => [
					id = 'test'
					value = 'test'
				]
			], usingEqualsForReferencesTo(NON_ROOT)))
		]
		assertThat(exception.message, containsString('wrong value: NonRoot(id="test")'))
	}
	
	@Test	
	@DisplayName("allows defining custom strategies to compare referenced elements")
	def void customStrategyForComparison() {
		assertDoesNotThrow [
			assertThat(pcm.Interface => [
				id = 'testInterface'
				methods += pcm.Method => [
					id = 'testMethod'
					name = 'test'
					returnType = pcm.Interface.inDifferentRepository => [
						id = 'different'
						name = 'referenced'
					]
				]
			], equalsDeeply(pcm.Interface => [
				id = 'testInterface'
				methods += pcm.Method => [
					id = 'testMethod'
					name = 'test'
					returnType = pcm.Interface.inDifferentRepository => [
						id = 'referencedInterface'
						name = 'referenced'
					]
				]
			], comaringPcmInterfacesByName))
		]
	}
	
	@Test
	@DisplayName("ignores nested references when changing the value comparison")
	def void ignoresNestedDifferencesWhenUsingComparisonStrategy() {
		assertDoesNotThrow [
			assertThat(pcm.Interface => [
				id = 'testInterface'
				methods += pcm.Method => [
					id = 'testMethod'
					name = 'test'
					returnType = pcm.Interface.inDifferentRepository => [
						id = 'referencedInterface'
						name = 'referenced'
						methods += pcm.Method => [
							id = 'different'
							name = 'different'
						]
					]
				]
			], equalsDeeply(pcm.Interface => [
				id = 'testInterface'
				methods += pcm.Method => [
					id = 'testMethod'
					name = 'test'
					returnType = pcm.Interface.inDifferentRepository => [
						id = 'referencedInterface'
						name = 'referenced'
						methods += pcm.Method => [
							id = 'fooMethod'
							name = 'fooMethod'
						]
						methods += pcm.Method => [
							id = 'additionalMethod'
							name = 'additionalMethod'
						]
					]
				]
			], comaringPcmInterfacesByName))
		]
	}
	
	@Test
	@DisplayName("recognizes change values when using a custom value comparison")
	def void customStrategyForComparisonFailure() {
		val exception = assertThrows(AssertionError) [
			assertThat(pcm.Interface => [
				id = 'testInterface'
				methods += pcm.Method => [
					id = 'testMethod'
					name = 'test'
					returnType = pcm.Interface.inDifferentRepository => [
						id = 'referencedInterface'
						name = 'different'
					]
				]
			], equalsDeeply(pcm.Interface => [
				id = 'testInterface'
				methods += pcm.Method => [
					id = 'testMethod'
					name = 'test'
					returnType = pcm.Interface.inDifferentRepository => [
						id = 'referencedInterface'
						name = 'referenced'
					]
				]
			], comaringPcmInterfacesByName))
		]
		assertThat(exception.message, containsString('wrong value: PInterface(id="referencedInterface"'))
	}
	
	@Test
	@DisplayName("allows to ignore ID features")
	def void ignoreIdFeature() {
		assertDoesNotThrow [
			assertThat(aet.Root => [
				id = 'differentRoot'
				singleValuedContainmentEReference = aet.NonRoot => [
					id = 'differentSub'
					value = 'test'
				]
			], equalsDeeply(aet.Root => [
				id = 'root'
				singleValuedContainmentEReference = aet.NonRoot => [
					id = 'test'
					value = 'test'
				]
			], ignoringFeatures(IDENTIFIED__ID)))
		]
	}
	
	@Test
	@DisplayName("allows to ignore references (missing object)")
	def void ignoreReferenceMissing() {
		assertDoesNotThrow [
			assertThat(aet.ValueBased => [ 
				children += aet.ValueBased
			], equalsDeeply(aet.ValueBased => [
				children += aet.ValueBased => [
					referenced += aet.NonRoot.inDifferentRoot => [id = 'test']
				]
			], ignoringFeatures(VALUE_BASED__REFERENCED)))
		]
	}
	
	@Test
	@DisplayName("allows to ignore references (additional object)")
	def void ignoreReferenceAdded() {
		assertDoesNotThrow [
			assertThat(aet.ValueBased => [
				children += aet.ValueBased => [
					referenced += aet.NonRoot.inDifferentRoot => [id = 'test']
				]
			], equalsDeeply(aet.ValueBased => [
				children += aet.ValueBased
			], ignoringFeatures(VALUE_BASED__REFERENCED)))
		]
	}
	
	@Test
	@DisplayName("allows to ignore references (changed object)")
	def void ignoreReferenceChanged() {
		assertDoesNotThrow [
			assertThat(aet.ValueBased => [
				children += aet.ValueBased => [
					referenced += aet.NonRoot.inDifferentRoot => [id = 'different']
				]
			], equalsDeeply(aet.ValueBased => [
				children += aet.ValueBased => [
					referenced += aet.NonRoot.inDifferentRoot => [id = 'test']
				]
			], ignoringFeatures(VALUE_BASED__REFERENCED)))
		]
	}
	
	@Test
	@DisplayName("uses the same fallback ID for matched objects in reporting")
	def void samefallbackIdInReporting() {
		val exception = assertThrows(AssertionError) [
			assertThat(aet.ValueBased => [
				value = 'test'
				children += aet.ValueBased => [value = 'different']
			], equalsDeeply(aet.ValueBased => [
				value = 'test'
				children += aet.ValueBased => [value = 'test']
			]))
		]
		assertThat(exception.message, is('''
			
			Expected: a ValueBased deeply equal to <ValueBased#1(
			        value="test", 
			        children=[ValueBased#2(value="test")]
			)>
			     but: found the following differences:
			        •  (ValueBased#1).children contained the unexpected value: ValueBased#3(value="different"), 
			        •  (ValueBased#1).children was missing the value: ValueBased#2(value="test")
			    for object <ValueBased#1(
			        value="test", 
			        children=[ValueBased#3(value="different")]
			)>'''
		))
	}
	
	def private static inDifferentRoot(NonRoot nonRoot) {
		nonRoot => [aet.Root => [
			id = 'foreignRoot'
			singleValuedContainmentEReference = nonRoot
		]]
	}
	
	def private static inDifferentRepository(PInterface content) {
		content => [pcm.Repository => [
			id = 'foreignRepository'
			interfaces += content
		]]
	}
	
	def private static nonRootInSameRoot(Root root) {
		aet.NonRoot => [
			root.multiValuedUnorderedContainmentEReference += it
		]
	}
	
	def private static containsExactDifferences(String... differences) {
		containsString("found the following differences:" + System.lineSeparator +
			differences.join(System.lineSeparator) ['        • ' + it ] + System.lineSeparator +
			"    for object"
		)
	}
	
	def private static comaringPcmInterfacesByName() {
		new PcmInterfaceNameEquality
	}
	
	private static class ValueBasedNonRoot extends NonRootImpl {
		override equals(Object object) {
			if (object === this) true
			else if (object === null) false
			else if (object instanceof ValueBasedNonRoot) this.value == object.value
			else false
		}
		
		override hashCode() {
			if (value === null) 0 else value.hashCode()
		}
	}
	
	private static class PcmInterfaceNameEquality implements EqualityStrategy {
		override compare(EObject left, EObject right) {
			if (left instanceof PInterface) {
				if (right instanceof PInterface) {
					return left.name == right.name ? EQUAL : UNEQUAL
				}
			}
			return UNKNOWN
		}
		
		override describeTo(StringBuilder builder) {
			builder.append("compared referenced PCM interfaces using their name")
		}
	}
}