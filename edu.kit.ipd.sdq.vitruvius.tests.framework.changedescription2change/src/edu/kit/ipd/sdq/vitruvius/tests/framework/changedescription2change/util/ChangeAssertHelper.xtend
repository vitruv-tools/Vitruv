package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ECompoundChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleEListEntry
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.ERootChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Quadruple
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Assert

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

class ChangeAssertHelper {

	private new() {
	}

	public static def <T> assertSingleChangeWithType(List<?> changes, Class<T> type) {
		changes.claimOne
		return assertObjectInstanceOf(changes.get(0), type)
	}

	public static def <T> assertObjectInstanceOf(Object object, Class<T> type) {
		Assert.assertTrue("The object " + object.class.simpleName + " should be type of " + type.simpleName,
			type.isInstance(object))
		return type.cast(object)
	}

	public static def <T extends AdditiveEChange<?>, SubtractiveEChange> assertOldAndNewValue(T eChange,
		Object oldValue, Object newValue) {
		eChange.assertOldValue(oldValue)
		eChange.assertNewValue(newValue)
	}

	public static def assertOldValue(EChange eChange, Object oldValue) {
		Assert.assertEquals("old value must be the same than the given old value", oldValue,
			(eChange as SubtractiveEChange<?>).oldValue)
	}

	public static def assertNewValue(AdditiveEChange<?> eChange, Object newValue) {
		val newValueInChange = eChange.newValue
		var condition = false
		if (newValue instanceof EObject && newValueInChange instanceof EObject) {
			val newEObject = newValue as EObject
			var newEObjectInChange = newValueInChange as EObject
			condition = EcoreUtil.equals(newEObject, newEObjectInChange)
		} else {
			condition = newValue?.equals(newValueInChange)
		}
		Assert.assertTrue("new value in change ' " + newValueInChange + "' must be the same than the given new value '" + newValue + "'!", condition)
	}

	public static def void assertAffectedEObject(EChange eChange, EObject expectedAffectedEObject) {
		Assert.assertEquals("The actual affected EObject is a different one than the expected affected EObject",
			(eChange as EFeatureChange<?, ?>).affectedEObject, expectedAffectedEObject)
	}

	public static def assertAffectedEFeature(EChange eChange, EStructuralFeature expectedEFeature) {
		Assert.assertEquals(
			"The actual affected EStructuralFeature is a different one than the expected EStructuralFeature",
			(eChange as EFeatureChange<?, ?>).affectedFeature, expectedEFeature)
	}

	public static def getFeautreByName(EObject eObject, String name) {
		eObject.eClass.getEStructuralFeature(name)
	}

	public static def assertIndices(PermuteEList permuteChange, List<Integer> expectedIndices) {
		Assert.assertEquals("The new indices for elements at old indices is wrong",
			permuteChange.newIndicesForElementsAtOldIndices, expectedIndices)
	}

	def public static void assertPermuteListTest(List<?> changes, EObject rootElement,
		List<Integer> expectedIndicesForElementsAtOldIndices, String featureName,
		Class<? extends PermuteEList> changeType) {
			changes.assertSingleChangeWithType(changeType)
			val permuteEAttributeValues = changeType.cast(changes.get(0))
			permuteEAttributeValues.assertAffectedEObject(rootElement)
			permuteEAttributeValues.assertAffectedEFeature(rootElement.getFeautreByName(featureName))
			permuteEAttributeValues.assertIndices(expectedIndicesForElementsAtOldIndices)
		}

		def static void assertContainment(UpdateEReference<?> updateEReference, boolean expectedValue) {
			Assert.assertEquals("The containment information of the change " + updateEReference + " is wrong",
				updateEReference.isContainment, expectedValue)
		}

		def static void assertIsDelete(SubtractiveEReferenceChange<?> subtractiveReference, boolean expectedValue) {
			Assert.assertEquals("Change " + subtractiveReference + " shall not be a delete change",
				subtractiveReference.isIsDelete, expectedValue)
		}

		def static void assertIsCreate(AdditiveEReferenceChange<?> additiveReference, boolean expectedValue) {
			Assert.assertEquals("Change " + additiveReference + " shall not be a create change",
				additiveReference.isIsCreate, expectedValue)
		}
				
		def static void assertUri(ERootChange rootChange, String expectedValue) {
			Assert.assertEquals("Change " + rootChange + " shall have the uri " + expectedValue,
				rootChange.uri, expectedValue)
		}

		def static void assertReplaceSingleValuedEReference(List<?> changes, Object expectedOldValue,
			Object expectedNewValue, String affectedEFeatureName, EObject affectedEObject, boolean isContainment,
			boolean isCreate, boolean isDelete) {
			val change = changes.assertSingleChangeWithType(ReplaceSingleValuedEReference)
			change.assertOldAndNewValue(expectedOldValue, expectedNewValue)
			change.assertAffectedEFeature(affectedEObject.getFeautreByName(affectedEFeatureName))
			change.assertAffectedEObject(affectedEObject)
			change.assertContainment(isContainment)
		}
		
		def static void assertReplaceSingleValueEAttribute(EChange eChange, Object oldValue, Object newValue) {
			val rsve = eChange.assertObjectInstanceOf(ReplaceSingleValuedEAttribute)
			rsve.assertOldAndNewValue(oldValue,newValue)
		}

		def static void assertIndex(UpdateSingleEListEntry change, int expectedIndex) {
			Assert.assertEquals("The value is not at the correct index", change.index, expectedIndex)
		}

		def public static assertSubtractiveChange(List<?> changes, EObject affectedEObject, String affectedFeatureName,
			Object oldValue, int expectedOldIndex, boolean isContainment, boolean isDelete) {
			val change = assertSingleChangeWithType(changes, RemoveEReference)
			change.assertAffectedEFeature(affectedEObject.getFeautreByName(affectedFeatureName))
			change.assertAffectedEObject(affectedEObject)
			change.assertOldValue(oldValue)
			if (change instanceof RemoveFromEList) {
				change.assertIndex(expectedOldIndex)
			}
			change.assertContainment(isContainment)
			change.assertIsDelete(isDelete)
		}

		def public static assertRemoveEAttribute(List<?> changes, EObject affecteEObject, String featureName,
			Object oldValue, int expectedOldIndex) {
			changes.assertSingleChangeWithType(RemoveEAttributeValue)
			val removeEAttributeValue = changes.get(0) as RemoveEAttributeValue<?, ?>
			removeEAttributeValue.assertAffectedEObject(affecteEObject)
			removeEAttributeValue.assertAffectedEFeature(affecteEObject.getFeautreByName(featureName))
			removeEAttributeValue.assertOldValue(oldValue)
			removeEAttributeValue.assertIndex(expectedOldIndex)
		}

		def public static List<?> assertExplicitUnset(List<?> changes) {
			val unsetChange = changes.assertSingleChangeWithType(ExplicitUnsetEFeature)
			Assert.assertEquals("atomic changes should be the same than the subtractive changes",
				unsetChange.atomicChanges, unsetChange.subtractiveChanges)
			return unsetChange.subtractiveChanges
		}

		def public static assertInsertRootEObject(EChange change, Object newValue, boolean isCreate, String uri) {
			val insertRoot = change.assertObjectInstanceOf(InsertRootEObject)
			insertRoot.assertNewValue(newValue)
			insertRoot.assertIsCreate(isCreate)
			insertRoot.assertUri(uri)
		}

		def public static assertRemoveRootEObject(EChange change, Object oldValue, boolean isDelete, String uri) {
			val removeRoot = change.assertObjectInstanceOf(RemoveRootEObject)
			removeRoot.assertOldValue(oldValue)
			removeRoot.assertIsDelete(isDelete)
			removeRoot.assertUri(uri)
		}

		def public static assertMoveEObject(List<?> changes, int atomicChanges) {
			val moveEObject = changes.assertSingleChangeWithType(MoveEObject)
			moveEObject.assertAtomicChanges(atomicChanges)
			val subtractiveReferenceChange = moveEObject.subtractWhatChange
			val removeUpdateEReferenceChange = moveEObject.subtractWhereChange
			val addEReferenceChange = moveEObject.addWhatChange
			val addUpdateEReferenceChange = moveEObject.
				addWhereChange
			return new Quadruple<SubtractiveEReferenceChange<?>, UpdateEReference<?>, AdditiveEReferenceChange<?>, UpdateEReference<?>>(
				subtractiveReferenceChange, removeUpdateEReferenceChange, addEReferenceChange,
				addUpdateEReferenceChange)

			}

			def public static assertReplaceInEList(List<?> changes, int atomicChanges) {
				val replaceInEList = changes.assertSingleChangeWithType(ReplaceInEList)
				replaceInEList.assertAtomicChanges(atomicChanges)
				val removeChange = replaceInEList.removeChange
				val insertInEList = replaceInEList.insertChange
				return new Pair<RemoveFromEList, InsertInEList>(removeChange, insertInEList)
			}

			def public static assertAtomicChanges(ECompoundChange eCompoundChange, int atomicChanges) {
				Assert.assertEquals("Expected exactly " + atomicChanges + " changes in move EObject",
					eCompoundChange.atomicChanges.size, atomicChanges)
			}

			def public static assertInsertEReference(EChange change, EObject affectedEObject, String featureName,
				Object expectedNewValue, int expectedIndex, boolean isContainment, boolean isCreate) {
				val insertEReference = change.assertObjectInstanceOf(InsertEReference)
				insertEReference.assertAffectedEObject(affectedEObject)
				insertEReference.assertAffectedEFeature(affectedEObject.getFeautreByName(featureName))
				insertEReference.assertNewValue(expectedNewValue)
				insertEReference.assertIndex(expectedIndex)
				insertEReference.assertContainment(isContainment)
				insertEReference.assertIsCreate(isCreate)
			}

			def public static assertInsertEAttribute(List<?> changes, EObject affectedEObject, String featureName,
				Object expectedValue, int expectedIndex) {
				changes.assertSingleChangeWithType(InsertEAttributeValue)
				val insertEAttributValue = changes.get(0) as InsertEAttributeValue<?, ?>
				insertEAttributValue.assertAffectedEObject(insertEAttributValue.affectedEObject)
				insertEAttributValue.assertNewValue(expectedValue)
				insertEAttributValue.assertIndex(expectedIndex)
				insertEAttributValue.assertAffectedEFeature(affectedEObject.getFeautreByName(featureName))
			}

		}
		