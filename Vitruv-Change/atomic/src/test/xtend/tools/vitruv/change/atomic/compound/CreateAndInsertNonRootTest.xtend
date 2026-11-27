package tools.vitruv.change.atomic.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.eobject.CreateEObject
import tools.vitruv.change.atomic.eobject.EObjectTest
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.atomic.util.EChangeAssertHelper.*
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet

/**
 * Test class for the concrete {@link CreateAndInsertNonRoot} EChange,
 * which creates a new non root EObject and inserts it in containment reference.
 */
class CreateAndInsertNonRootTest extends EObjectTest {
	var Root affectedEObject
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	@BeforeEach
	def void prepareState() {
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		assertIsStateBefore
	}

	/**
	 * Tests whether resolving the {@link CreateAndInsertNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, 0)

		// Resolve		
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests the {@link CreateAndInsertNonRoot} EChange by applying it forward.
	 * Creates and inserts a new non root object into a multi valued 
	 * containment reference.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve and apply change
		val resolvedChange = createUnresolvedChange(affectedEObject, 0).applyForwardAndResolve

		assertEquals(referenceContent.size, 1)
		val createChange = assertType(resolvedChange.get(0), CreateEObject)
		assertTrue(referenceContent.contains(createChange.affectedElement))

		// Create and resolve and apply change 2	
		val unresolvedChange2 = createUnresolvedChange(affectedEObject, 1)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests the {@link CreateAndInsertNonRoot} EChange by applying it backward.
	 * A non root object which was added to a containment reference will be removed and
	 * deleted.
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, 0).applyForwardAndResolve

		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, 1).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		val createChange = assertType(resolvedChange.get(0), CreateEObject)
		val createChange2 = assertType(resolvedChange2.get(0), CreateEObject)
		assertTrue(referenceContent.contains(createChange.affectedElement))
		assertFalse(referenceContent.contains(createChange2.affectedElement))
		assertEquals(referenceContent.size, 1)

		// Apply backward 1	
		resolvedChange.applyBackward

		// State after
		assertIsStateBefore
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertEquals(referenceContent.size, 0)
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertEquals(referenceContent.size, 2)
		assertThat(referenceContent.get(0), instanceOf(NonRoot))
		assertThat(referenceContent.get(1), instanceOf(NonRoot))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange<Uuid>> createUnresolvedChange(Root affectedRootObject, int index) {
		return compoundFactory.createCreateAndInsertNonRootChange(affectedRootObject, affectedFeature, aet.NonRoot,
			index).unresolve
	}
}
