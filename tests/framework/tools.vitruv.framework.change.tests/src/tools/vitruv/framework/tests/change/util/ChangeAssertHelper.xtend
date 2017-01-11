package tools.vitruv.framework.tests.change.util

import tools.vitruv.framework.change.echange.AdditiveEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.SubtractiveEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.compound.MoveEObject
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.util.datatypes.Quadruple
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Assert

import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*
import tools.vitruv.framework.change.echange.feature.list.PermuteListEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import static org.junit.Assert.*;
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot

class ChangeAssertHelper {

	private new() {
	}

	public static def <T> assertSingleChangeWithType(List<?> changes, Class<T> type) {
		changes.claimOne
		return assertObjectInstanceOf(changes.get(0), type)
	}

	public static def <T> T assertObjectInstanceOf(Object object, Class<T> type) {
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
		var condition = newValue == null && newValueInChange == null;
		if (newValue instanceof EObject && newValueInChange instanceof EObject) {
			val newEObject = newValue as EObject
			var newEObjectInChange = newValueInChange as EObject
			condition = EcoreUtil.equals(newEObject, newEObjectInChange)
		} else if (!condition) {
			condition = newValue != null && newValue.equals(newValueInChange)
		}
		Assert.assertTrue("new value in change ' " + newValueInChange + "' must be the same than the given new value '" + newValue + "'!", condition)
	}

	public static def void assertAffectedEObject(EChange eChange, EObject expectedAffectedEObject) {
		if (eChange instanceof FeatureEChange<?,?>) {
			Assert.assertEquals("The actual affected EObject is a different one than the expected affected EObject",
				expectedAffectedEObject, (eChange as FeatureEChange<?, ?>).affectedEObject)
		} else if (eChange instanceof EObjectExistenceEChange<?>) {
			Assert.assertEquals("The actual affected EObject is a different one than the expected affected EObject",
				expectedAffectedEObject, (eChange as EObjectExistenceEChange<?>).affectedEObject)
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static def assertAffectedEFeature(EChange eChange, EStructuralFeature expectedEFeature) {
		Assert.assertEquals(
			"The actual affected EStructuralFeature is a different one than the expected EStructuralFeature",
			expectedEFeature, (eChange as FeatureEChange<?, ?>).affectedFeature)
	}

	public static def getFeatureByName(EObject eObject, String name) {
		eObject.eClass.getEStructuralFeature(name)
	}

	public static def assertIndices(PermuteListEChange<?,?> permuteChange, List<Integer> expectedIndices) {
		Assert.assertEquals("The new indices for elements at old indices is wrong",
			expectedIndices, permuteChange.newIndicesForElementsAtOldIndices)
	}

	def public static void assertPermuteAttributeListTest(List<?> changes, EObject rootElement,
		List<Integer> expectedIndicesForElementsAtOldIndices, String featureName) {
			
		}

	def public static void assertPermuteListTest(List<?> changes, EObject rootElement,
		List<Integer> expectedIndicesForElementsAtOldIndices, String featureName,
		Class<? extends EChange> changeType) {
			Assert.assertTrue(PermuteListEChange.isAssignableFrom(changeType))
			changes.assertSingleChangeWithType(changeType)
			val permuteEAttributeValues = PermuteListEChange.cast(changes.get(0))
			permuteEAttributeValues.assertAffectedEObject(rootElement)
			permuteEAttributeValues.assertAffectedEFeature(rootElement.getFeatureByName(featureName))
			permuteEAttributeValues.assertIndices(expectedIndicesForElementsAtOldIndices)
		}

		def static void assertContainment(UpdateReferenceEChange<?> updateEReference, boolean expectedValue) {
			Assert.assertEquals("The containment information of the change " + updateEReference + " is wrong",
				expectedValue, updateEReference.isContainment)
		}

		def static void assertIsDelete(EObjectSubtractedEChange<?> subtractiveReference, boolean expectedValue) {
			Assert.assertEquals("Change " + subtractiveReference + " shall not be a delete change",
				expectedValue, expectedValue)
		}

		def static void assertIsCreate(EObjectAddedEChange<?> additiveReference, boolean expectedValue) {
			Assert.assertEquals("Change " + additiveReference + " shall not be a create change",
				expectedValue, expectedValue)
		}
				
		def static void assertUri(RootEChange rootChange, String expectedValue) {
			Assert.assertEquals("Change " + rootChange + " shall have the uri " + URI.createFileURI(expectedValue).toString,
				URI.createFileURI(expectedValue).toString, rootChange.uri)
		}
	
		def static void assertCreateAndReplaceAndDeleteNonRoot(EChange change, EObject expectedOldValue,
			EObject expectedNewValue, String affectedEFeatureName, EObject affectedEObject, boolean isContainment) {
			val compositeChange = change.assertObjectInstanceOf(CreateAndReplaceAndDeleteNonRoot)
			compositeChange.createChange.assertAffectedEObject(expectedNewValue);
			compositeChange.deleteChange.assertAffectedEObject(expectedOldValue);
			compositeChange.replaceChange.assertOldAndNewValue(expectedOldValue, expectedNewValue)
			compositeChange.replaceChange.assertAffectedEFeature(affectedEObject.getFeatureByName(affectedEFeatureName))
			compositeChange.replaceChange.assertAffectedEObject(affectedEObject)
			compositeChange.replaceChange.assertContainment(isContainment)
		}

		def static void assertReplaceSingleValuedEReference(EChange change, Object expectedOldValue,
			Object expectedNewValue, String affectedEFeatureName, EObject affectedEObject, boolean isContainment) {
			val replaceChange = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
			replaceChange.assertOldAndNewValue(expectedOldValue, expectedNewValue)
			replaceChange.assertAffectedEFeature(affectedEObject.getFeatureByName(affectedEFeatureName))
			replaceChange.assertAffectedEObject(affectedEObject)
			replaceChange.assertContainment(isContainment)
		}

		def static void assertSetSingleValuedEReference(EChange change,
			Object expectedNewValue, String affectedEFeatureName, EObject affectedEObject, boolean isContainment,
			boolean isCreate) {
			val rsve = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
			rsve.assertOldAndNewValue(null, expectedNewValue)
			assertFalse(rsve.isFromNonDefaultValue);
			assertTrue(rsve.isToNonDefaultValue);
			rsve.assertAffectedEFeature(affectedEObject.getFeatureByName(affectedEFeatureName))
			rsve.assertAffectedEObject(affectedEObject)
			rsve.assertContainment(isContainment)
			rsve.assertIsCreate(isCreate)
			rsve.assertIsDelete(false);
		}
		
		def static void assertUnsetSingleValuedEReference(EChange change,
			Object expectedOldValue, String affectedEFeatureName, EObject affectedEObject, boolean isContainment,
			boolean isDelete) {
			val rsve = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
			rsve.assertOldAndNewValue(expectedOldValue, null)
			assertTrue(rsve.isFromNonDefaultValue);
			assertFalse(rsve.isToNonDefaultValue);
			rsve.assertAffectedEFeature(affectedEObject.getFeatureByName(affectedEFeatureName))
			rsve.assertAffectedEObject(affectedEObject)
			rsve.assertContainment(isContainment)
			rsve.assertIsCreate(false);
			rsve.assertIsDelete(isDelete)
		}
		
		def static void assertReplaceSingleValueEAttribute(EChange eChange, Object oldValue, Object newValue) {
			val rsve = eChange.assertObjectInstanceOf(ReplaceSingleValuedEAttribute)
			rsve.assertOldAndNewValue(oldValue,newValue)
		}
		
		def static void assertSetSingleValueEAttribute(EChange eChange, Object newValue) {
			val rsve = eChange.assertObjectInstanceOf(ReplaceSingleValuedEAttribute)
			rsve.assertOldAndNewValue((rsve.affectedFeature as EStructuralFeature).defaultValue, newValue)
			assertFalse(rsve.isFromNonDefaultValue);
			assertTrue(rsve.isToNonDefaultValue);
		}
		
		def static void assertUnsetSingleValueEAttribute(EChange eChange, Object oldValue) {
			val rsve = eChange.assertObjectInstanceOf(ReplaceSingleValuedEAttribute)
			rsve.assertOldAndNewValue(oldValue,(rsve.affectedFeature as EStructuralFeature).defaultValue)
			assertTrue(rsve.isFromNonDefaultValue);
			assertFalse(rsve.isToNonDefaultValue);
		}

		def static void assertIndex(UpdateSingleListEntryEChange<?,?> change, int expectedIndex) {
			Assert.assertEquals("The value is not at the correct index", change.index, expectedIndex)
		}

		def public static <A extends EObject, T extends EObject> RemoveEReference<A,T> assertRemoveEReference(EChange change, A affectedEObject, String affectedFeatureName,
			T oldValue, int expectedOldIndex, boolean isContainment) {
			val subtractiveChange = assertObjectInstanceOf(change, RemoveEReference)
			subtractiveChange.assertAffectedEFeature(affectedEObject.getFeatureByName(affectedFeatureName))
			subtractiveChange.assertAffectedEObject(affectedEObject)
			subtractiveChange.assertOldValue(oldValue)
			if (subtractiveChange instanceof RemoveFromListEChange<?,?,?>) {
				subtractiveChange.assertIndex(expectedOldIndex)
			}
			subtractiveChange.assertContainment(isContainment)
			return subtractiveChange
		}
		
		def public static <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A,T> assertRemoveAndDeleteNonRoot(EChange change, A affectedEObject, String affectedFeatureName,
			T oldValue, int expectedOldIndex) {
			val compositeChange = assertObjectInstanceOf(change, RemoveAndDeleteNonRoot)
			compositeChange.deleteChange.assertAffectedEObject(oldValue);
			compositeChange.removeChange.assertAffectedEObject(affectedEObject)
			compositeChange.removeChange.assertAffectedEFeature(affectedEObject.getFeatureByName(affectedFeatureName))
			compositeChange.removeChange.assertOldValue(oldValue)
			if (compositeChange.removeChange instanceof RemoveFromListEChange<?,?,?>) {
				compositeChange.removeChange.assertIndex(expectedOldIndex)
			}
			compositeChange.removeChange.assertContainment(true)
			return compositeChange
		}

		def public static assertRemoveEAttribute(List<?> changes, EObject affectedEObject, String featureName,
			Object oldValue, int expectedOldIndex) {
			changes.assertSingleChangeWithType(RemoveEAttributeValue)
			val removeEAttributeValue = changes.get(0) as RemoveEAttributeValue<?, ?>
			removeEAttributeValue.assertAffectedEObject(affectedEObject)
			removeEAttributeValue.assertAffectedEFeature(affectedEObject.getFeatureByName(featureName))
			removeEAttributeValue.assertOldValue(oldValue)
			removeEAttributeValue.assertIndex(expectedOldIndex)
		}

		def public static ExplicitUnsetEFeature<?,?,?,?> assertExplicitUnset(EChange change) {
			val unsetChange = change.assertObjectInstanceOf(ExplicitUnsetEFeature)
			Assert.assertEquals("atomic changes should be the same than the subtractive changes",
				unsetChange.atomicChanges, unsetChange.subtractiveChanges)
			return unsetChange
		}

		def public static assertInsertRootEObject(EChange change, Object newValue, boolean isCreate, String uri) {
			val insertRoot = change.assertObjectInstanceOf(InsertRootEObject)
			insertRoot.assertNewValue(newValue)
			insertRoot.assertUri(uri)
		}

		def public static assertRemoveRootEObject(EChange change, Object oldValue, boolean isDelete, String uri) {
			val removeRoot = change.assertObjectInstanceOf(RemoveRootEObject)
			removeRoot.assertOldValue(oldValue)
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
			return new Quadruple<EObjectSubtractedEChange<?>, UpdateReferenceEChange<?>, EObjectAddedEChange<?>, UpdateReferenceEChange<?>>(
				subtractiveReferenceChange, removeUpdateEReferenceChange, addEReferenceChange,
				addUpdateEReferenceChange)

			}

			def public static assertAtomicChanges(CompoundEChange eCompoundChange, int atomicChanges) {
				Assert.assertEquals("Expected exactly " + atomicChanges + " changes in move EObject",
					eCompoundChange.atomicChanges.size, atomicChanges)
			}

			def public static <A extends EObject, T extends EObject> InsertEReference<A,T> assertInsertEReference(EChange change, A affectedEObject, String featureName,
				T expectedNewValue, int expectedIndex, boolean isContainment) {
				val insertEReference = change.assertObjectInstanceOf(InsertEReference)
				insertEReference.assertAffectedEObject(affectedEObject)
				insertEReference.assertAffectedEFeature(affectedEObject.getFeatureByName(featureName))
				insertEReference.assertNewValue(expectedNewValue)
				insertEReference.assertIndex(expectedIndex)
				insertEReference.assertContainment(isContainment)
				return insertEReference
			}

			def public static <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A,T> assertCreateAndInsertNonRoot(EChange change, A affectedEObject, String featureName,
				T expectedNewValue, int expectedIndex) {
				val createAndInsert = change.assertObjectInstanceOf(CreateAndInsertNonRoot)
				createAndInsert.createChange.assertAffectedEObject(expectedNewValue)
				createAndInsert.insertChange.assertAffectedEObject(affectedEObject)
				createAndInsert.insertChange.assertAffectedEFeature(affectedEObject.getFeatureByName(featureName))
				createAndInsert.insertChange.assertNewValue(expectedNewValue)
				createAndInsert.insertChange.assertIndex(expectedIndex)
				createAndInsert.insertChange.assertContainment(true)
				return createAndInsert
			}


			def public static assertInsertEAttribute(List<?> changes, EObject affectedEObject, String featureName,
				Object expectedValue, int expectedIndex) {
				changes.assertSingleChangeWithType(InsertEAttributeValue)
				val insertEAttributValue = changes.get(0) as InsertEAttributeValue<? extends EObject, ? extends EObject>
				insertEAttributValue.assertAffectedEObject(insertEAttributValue.affectedEObject)
				insertEAttributValue.assertNewValue(expectedValue)
				insertEAttributValue.assertIndex(expectedIndex)
				insertEAttributValue.assertAffectedEFeature(affectedEObject.getFeatureByName(featureName))
			}

		}
		