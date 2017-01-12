package tools.vitruv.framework.tests.change.reference

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.junit.Test
import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import tools.vitruv.framework.change.echange.feature.reference.PermuteEReferences
import org.junit.Ignore
import static allElementTypes.AllElementTypesPackage.Literals.*;

class ChangeDescription2PermuteEReferenceTest extends ChangeDescription2ChangeTransformationTest{
	@Ignore
	@Test
	def public void testPermuteContainmentEReference(){
		//prepare
		createAndAddNonRootToContainment(false)
		createAndAddNonRootToContainment(true)
		
		//test permute
		this.rootElement.multiValuedNonContainmentEReference.add(0, this.rootElement.multiValuedContainmentEReference.remove(1))
		
		val changes = getChanges
		val expectedList = #[1, 0]
		changes.claimChange(0).assertPermuteListTest(this.rootElement, expectedList, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, PermuteEReferences)
	}
	
	@Ignore
	@Test
	def public void testPermuteManyContainmentEReference(){
		//prepare
		createAndAddNonRootToContainment(false)
		createAndAddNonRootToContainment(false)
		createAndAddNonRootToContainment(true)
		
		//test permute
		this.rootElement.multiValuedNonContainmentEReference.add(0, this.rootElement.multiValuedContainmentEReference.remove(2))
		
		val changes = getChanges
		val expectedList = #[2, 1, 0]
		changes.claimChange(0).assertPermuteListTest(this.rootElement, expectedList, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, PermuteEReferences)
	}
	
	@Ignore
	@Test
	def public void testPermuteNonContainmentEReference(){
		//prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		val nonRoot2 = createAndAddNonRootToContainment(false)
		this.rootElement.multiValuedNonContainmentEReference.addAll(nonRoot, nonRoot2)
		startRecording
		
		//test permute
		this.rootElement.multiValuedNonContainmentEReference.add(0, this.rootElement.multiValuedNonContainmentEReference.remove(1))
		
		val changes = getChanges
		val expectedList = #[1, 0]
		changes.claimChange(0).assertPermuteListTest(this.rootElement, expectedList, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, PermuteEReferences)
	}
	
	@Ignore
	@Test
	def public void testPermuteManyNonContainmentEReference(){
		//prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		val nonRoot2 = createAndAddNonRootToContainment(false)
		val nonRoot3 = createAndAddNonRootToContainment(false)
		this.rootElement.multiValuedNonContainmentEReference.addAll(nonRoot, nonRoot2, nonRoot3)
		startRecording
		
		//test permute
		this.rootElement.multiValuedNonContainmentEReference.add(0, this.rootElement.multiValuedNonContainmentEReference.remove(2))
		
		val changes = getChanges
		val expectedList = #[2, 1, 0]
		changes.claimChange(0).assertPermuteListTest(this.rootElement, expectedList, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, PermuteEReferences)
	}
}