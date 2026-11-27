package tools.vitruv.change.atomic.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import java.util.stream.Stream
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

/**
 * Test class for the concrete {@link ReplaceSingleValuedEReference} EChange,
 * which replaces the value of a reference with a new one.
 */
class ReplaceSingleValuedEReferenceTest extends ReferenceEChangeTest {
	var NonRoot oldValue
	var EReference affectedFeature

	@BeforeEach
	def void prepareElements() {
		oldValue = aet.NonRoot.withUuid
	}

	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEReference} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Set state before
		isNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange()

		// Resolve
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	@ParameterizedTest
	@MethodSource("testConfigurations")
	def void applyForwardTest(boolean isContainment, boolean newValueIsNull, boolean oldValueIsNull) {
		isContainment ? isContainmentTest : isNonContainmentTest
		if (newValueIsNull) {
			newValue = null
		}
		if (oldValueIsNull) {
			oldValue = null
		}
		
		// Create change (resolved)
		val unresolvedChange = createUnresolvedChange()

		// State before
		assertIsStateBefore(newValue)

		// Apply forward
		unresolvedChange.applyForwardAndResolve

		// State after
		assertIsStateAfter(oldValue)
	}

	@ParameterizedTest
	@MethodSource("testConfigurations")
	def void applyBackwardTest(boolean isContainment, boolean newValueIsNull, boolean oldValueIsNull) {
		isContainment ? isContainmentTest : isNonContainmentTest
		if (newValueIsNull) {
			newValue = null
		}
		if (oldValueIsNull) {
			oldValue = null
		}

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().applyForwardAndResolve

		// Set state after
		prepareReference(newValue)
		assertIsStateAfter(null)

		// Apply backward
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore(null)
	}

	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value containment reference with its old value.
	 * The old value was null.
	 */
	@Test
	def void replaceSingleValuedEReferenceApplyBackwardOldValueNullTest() {
		// Set state before
		oldValue = null
		isContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().applyForwardAndResolve

		// Set state after
		prepareReference(newValue)
		assertIsStateAfter(null)

		// Apply backward
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore(newValue)
	}

	/**
	 * Starts a test with a containment feature and sets state before.
	 */
	def private void isContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareReference(oldValue)
	}

	/**
	 * Starts a test with a non containment feature and sets state before.
	 */
	def private void isNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE
		prepareReference(oldValue)
		prepareResource
	}

	/**
	 * Sets the value of the affected feature.
	 * @param object The new value of the affected feature.
	 */
	def private void prepareReference(EObject object) {
		affectedEObject.eSet(affectedFeature, object)
	}

	/**
	 * Prepares all new and old values and stores them in a resource.
	 */
	override protected void prepareResource() {
		super.prepareResource
		resource.contents.add(oldValue)
	}

	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore(NonRoot valueInStagingArea) {
		resourceIsStateBefore
		val currentValue = affectedEObject.eGet(affectedFeature) as EObject
		if (oldValue !== null || currentValue !== null)
			assertThat(oldValue, equalsDeeply(currentValue))
	}

	/**
	 * Resource is in state before the change.
	 */
	def private void resourceIsStateBefore() {
		if (!affectedFeature.containment) {
			assertEquals(resourceContent.size, 4)
			assertThat(newValue, equalsDeeply(resourceContent.get(1)))
			assertThat(newValue2, equalsDeeply(resourceContent.get(2)))
			assertThat(oldValue, equalsDeeply(resourceContent.get(3)))
		} else {
			assertEquals(resourceContent.size, 1)
		}
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter(NonRoot valueInStaggingArea) {
		resourceIsStateBefore
		val currentValue = affectedEObject.eGet(affectedFeature) as EObject
		if (newValue === null) {
			assertNull(currentValue)
		}
		else {
			assertThat(newValue, equalsDeeply(currentValue))

		}
	}

	/**
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange() {
		return atomicFactory.createReplaceSingleReferenceChange(affectedEObject, affectedFeature, oldValue, newValue).unresolve
	}
	
	static def Stream<Arguments> testConfigurations() {
		return Stream.of(
			Arguments.of(Named.of("non containment", false), Named.of("with new", false), Named.of("with old", false)),
			Arguments.of(Named.of("containment", true), Named.of("with new", false), Named.of("with old", false)),
			Arguments.of(Named.of("containment", true), Named.of("with new = null", true), Named.of("with old", false)),
			Arguments.of(Named.of("containment", true), Named.of("with new", false), Named.of("with old = null", false))
		)
	}
}
