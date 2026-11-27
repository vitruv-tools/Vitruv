package tools.vitruv.change.atomic.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.List
import org.eclipse.emf.ecore.EReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.eobject.EObjectTest
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet

/**
 * Test class for the concrete {@link CreateAndReplaceAndDeleteNonRoot} EChange,
 * which creates a new non root EObject and replaces an existing non root object
 * in a single value containment reference. The existing one will be deleted.
 */
class CreateAndReplaceAndDeleteNonRootTest extends EObjectTest {
	var Root affectedEObject
	var NonRoot oldValue
	var EReference affectedFeature

	@BeforeEach
	def void prepareState() {
		oldValue = aet.NonRoot.withUuid
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareStateBefore
	}

	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange forward
	 * by creating a new non root and replacing an existing one.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve 1
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change 
		val resolvedChange = createUnresolvedChange().applyForwardAndResolve

		// State after 
		assertIsStateAfter

		// Apply backward
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	def private void prepareStateBefore() {
		affectedEObject.eSet(affectedFeature, oldValue)
		assertIsStateBefore
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertThat(oldValue, equalsDeeply(affectedEObject.eGet(affectedFeature) as NonRoot))
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertThat(affectedEObject.eGet(affectedFeature), instanceOf(NonRoot))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange<Uuid>> createUnresolvedChange() {
		return compoundFactory.createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue,
			aet.NonRoot).unresolve
	}
}
