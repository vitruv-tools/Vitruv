package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

/**
 * Test class for the concrete {@link CreateAndInsertNonRoot} EChange,
 * which creates a new non root EObject and inserts it in containment reference.
 */
public class CreateAndInsertNonRootTest extends ReferenceEChangeTest {
	protected var EReference defaultAffectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets the feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
	}
	
	/**
	 * Resolves a {@link CreateAndInsertNonRoot} EChange. The model is in state
	 * before the change, so the new non root element will be created and inserted
	 * in a containment reference.
	 */
	@Test
	def public void resolveCreateAndInsertNonRootTest() {
		val size = referenceContent.size
		Assert.assertTrue(stagingArea1.contents.empty)	
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndInsertNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)	
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.insertChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != 
			unresolvedChange.insertChange.affectedEObject)	
			
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as CreateAndInsertNonRoot<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertTrue(unresolvedChange.createChange != resolvedChange.createChange)
		Assert.assertTrue(unresolvedChange.insertChange != resolvedChange.insertChange)
		
		// New object is copy
		Assert.assertTrue(resolvedChange.createChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == 
			resolvedChange.insertChange.newValue)
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertEquals(referenceContent.size, size)
		Assert.assertTrue(stagingArea1.contents.empty)			
	}
	
	/**
	 * Resolves a {@link CreateAndInsertNonRoot} EChange. The model is in state
	 * after the change, so the new non root element is in a containment reference.
	 */
	@Test
	def public void resolveCreateAndInsertNonRootTest2() {
		Assert.assertTrue(stagingArea1.contents.empty)			
		referenceContent.add(defaultNewValue)
		
		val size = referenceContent.size
			
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndInsertNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.insertChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != 
			unresolvedChange.insertChange.affectedEObject)	
			
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as CreateAndInsertNonRoot<Root, NonRoot>			

		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertTrue(unresolvedChange.createChange != resolvedChange.createChange)
		Assert.assertTrue(unresolvedChange.insertChange != resolvedChange.insertChange)
		
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == defaultNewValue)
		Assert.assertTrue(resolvedChange.insertChange.newValue == defaultNewValue)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.		
		Assert.assertEquals(referenceContent.size, size)
		Assert.assertTrue(stagingArea1.contents.empty)				
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndInsertNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndInsertNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)	
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests the {@link CreateAndInsertNonRoot} EChange by creating
	 * and inserting a new non root object into a multi valued 
	 * containment reference.
	 */
	@Test
	def public void createAndInsertNonRootApplyForwardTest() {
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val oldSize = referenceContent.size	
		
		// Create change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndInsertNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1) as CreateAndInsertNonRoot<Root, NonRoot>
			
		// Apply forward 1
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 1)
		Assert.assertTrue(referenceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Create change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndInsertNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1) as CreateAndInsertNonRoot<Root, NonRoot>
			
		// Apply forward 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 2)
		Assert.assertTrue(referenceContent.contains(resolvedChange2.createChange.affectedEObject))
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests the {@link CreateAndInsertNonRoot} EChange by applying it backward.
	 * A non root object which was added to a containment reference will be removed and
	 * deleted.
	 */
	@Test
	def public void createAndInsertNonRootApplyBackwardTest() {
		Assert.assertTrue(stagingArea1.contents.empty)			
		referenceContent.add(defaultNewValue)
		referenceContent.add(defaultNewValue2)
		
		val size = referenceContent.size
		
		// Create change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndInsertNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true).
			copyAndResolveAfter(resourceSet1) as CreateAndInsertNonRoot<Root, NonRoot>
	
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertFalse(referenceContent.contains(defaultNewValue))
		Assert.assertTrue(referenceContent.contains(defaultNewValue2))
		Assert.assertEquals(referenceContent.size, size - 1)
		Assert.assertTrue(stagingArea1.contents.empty)

		// Create change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndInsertNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, true).
			copyAndResolveAfter(resourceSet1) as CreateAndInsertNonRoot<Root, NonRoot>	
					
		// Apply backward 2	
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertFalse(referenceContent.contains(defaultNewValue))
		Assert.assertFalse(referenceContent.contains(defaultNewValue2))
		Assert.assertEquals(referenceContent.size, size - 2)
		Assert.assertTrue(stagingArea1.contents.empty)		
	}
}