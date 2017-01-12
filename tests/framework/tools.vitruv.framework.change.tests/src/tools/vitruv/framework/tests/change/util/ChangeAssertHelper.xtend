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
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import static extension edu.kit.ipd.sdq.commons.util.java.util.ListUtil.*
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject

class ChangeAssertHelper {

	private new() {
	}
	
	public static def assertChangeCount(List<?> changes, int expectedCount) {
		Assert.assertEquals("There were " + changes.size + " changes, although " + expectedCount + " were expected",
			expectedCount, changes.size
		);
	}
	
	public static def EChange claimChange(List<EChange> changes, int index) {
		return changes.claimElementAt(index)
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
		List<Integer> expectedIndicesForElementsAtOldIndices, EStructuralFeature affectedFeature) {
			
		}

	def public static void assertPermuteListTest(EChange change, EObject rootElement,
		List<Integer> expectedIndicesForElementsAtOldIndices, EStructuralFeature affectedFeature,
		Class<? extends EChange> changeType) {
			Assert.assertTrue(PermuteListEChange.isAssignableFrom(changeType))
			val permuteEAttributeValues = assertObjectInstanceOf(change, PermuteListEChange)
			permuteEAttributeValues.assertAffectedEObject(rootElement)
			permuteEAttributeValues.assertAffectedEFeature(affectedFeature)
			permuteEAttributeValues.assertIndices(expectedIndicesForElementsAtOldIndices)
		}

		def static void assertContainment(UpdateReferenceEChange<?> updateEReference, boolean expectedValue) {
			Assert.assertEquals("The containment information of the change " + updateEReference + " is wrong",
				expectedValue, updateEReference.isContainment)
		}

		def static void assertUri(RootEChange rootChange, String expectedValue) {
			Assert.assertEquals("Change " + rootChange + " shall have the uri " + URI.createFileURI(expectedValue).toString,
				URI.createFileURI(expectedValue).toString, rootChange.uri)
		}
	
		def static void assertCreateAndReplaceAndDeleteNonRoot(EChange change, EObject expectedOldValue,
			EObject expectedNewValue, EStructuralFeature affectedFeature, EObject affectedEObject, boolean isContainment) {
			val compositeChange = change.assertObjectInstanceOf(CreateAndReplaceAndDeleteNonRoot)
			compositeChange.createChange.assertAffectedEObject(expectedNewValue);
			compositeChange.deleteChange.assertAffectedEObject(expectedOldValue);
			compositeChange.replaceChange.assertOldAndNewValue(expectedOldValue, expectedNewValue)
			compositeChange.replaceChange.assertAffectedEFeature(affectedFeature)
			compositeChange.replaceChange.assertAffectedEObject(affectedEObject)
			compositeChange.replaceChange.assertContainment(isContainment)
		}

		def static void assertReplaceSingleValuedEReference(EChange change, Object expectedOldValue,
			Object expectedNewValue, EStructuralFeature affectedFeature, EObject affectedEObject, boolean isContainment) {
			val replaceChange = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
			replaceChange.assertOldAndNewValue(expectedOldValue, expectedNewValue)
			replaceChange.assertAffectedEFeature(affectedFeature)
			replaceChange.assertAffectedEObject(affectedEObject)
			replaceChange.assertContainment(isContainment)
		}

		def static void assertSetSingleValuedEReference(EChange change,
			Object expectedNewValue, EStructuralFeature affectedFeature, EObject affectedEObject, boolean isContainment,
			boolean isCreate) {
			val rsve = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
			rsve.assertOldAndNewValue(null, expectedNewValue)
			assertFalse(rsve.isFromNonDefaultValue);
			assertTrue(rsve.isToNonDefaultValue);
			rsve.assertAffectedEFeature(affectedFeature)
			rsve.assertAffectedEObject(affectedEObject)
			rsve.assertContainment(isContainment)
		}
		
		def static void assertUnsetSingleValuedEReference(EChange change,
			Object expectedOldValue, EStructuralFeature affectedFeature, EObject affectedEObject, boolean isContainment,
			boolean isDelete) {
			val rsve = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
			rsve.assertOldAndNewValue(expectedOldValue, null)
			assertTrue(rsve.isFromNonDefaultValue);
			assertFalse(rsve.isToNonDefaultValue);
			rsve.assertAffectedEFeature(affectedFeature)
			rsve.assertAffectedEObject(affectedEObject)
			rsve.assertContainment(isContainment)
		}
		
		def static void assertIndex(UpdateSingleListEntryEChange<?,?> change, int expectedIndex) {
			Assert.assertEquals("The value is not at the correct index", expectedIndex, change.index)
		}

		def public static <A extends EObject, T extends EObject> RemoveEReference<A,T> assertRemoveEReference(EChange change, A affectedEObject, EStructuralFeature affectedFeature,
			T oldValue, int expectedOldIndex, boolean isContainment) {
			val subtractiveChange = assertObjectInstanceOf(change, RemoveEReference)
			subtractiveChange.assertAffectedEFeature(affectedFeature)
			subtractiveChange.assertAffectedEObject(affectedEObject)
			subtractiveChange.assertOldValue(oldValue)
			if (subtractiveChange instanceof RemoveFromListEChange<?,?,?>) {
				subtractiveChange.assertIndex(expectedOldIndex)
			}
			subtractiveChange.assertContainment(isContainment)
			return subtractiveChange
		}
		
		def public static <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A,T> assertRemoveAndDeleteNonRoot(EChange change, A affectedEObject, EStructuralFeature affectedFeature,
			T oldValue, int expectedOldIndex) {
			val compositeChange = assertObjectInstanceOf(change, RemoveAndDeleteNonRoot)
			compositeChange.deleteChange.assertAffectedEObject(oldValue);
			compositeChange.removeChange.assertAffectedEObject(affectedEObject)
			compositeChange.removeChange.assertAffectedEFeature(affectedFeature)
			compositeChange.removeChange.assertOldValue(oldValue)
			if (compositeChange.removeChange instanceof RemoveFromListEChange<?,?,?>) {
				compositeChange.removeChange.assertIndex(expectedOldIndex)
			}
			compositeChange.removeChange.assertContainment(true)
			return compositeChange
		}

		def public static assertRemoveEAttribute(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
			Object oldValue, int expectedOldIndex) {
			val removeEAttributeValue = assertObjectInstanceOf(change, RemoveEAttributeValue)
			removeEAttributeValue.assertAffectedEObject(affectedEObject)
			removeEAttributeValue.assertAffectedEFeature(affectedFeature)
			removeEAttributeValue.assertOldValue(oldValue)
			removeEAttributeValue.assertIndex(expectedOldIndex)
		}
		
		def public static assertReplaceSingleValuedEAttribute(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
			Object oldValue, Object newValue) {
			val removeEAttributeValue = assertObjectInstanceOf(change, ReplaceSingleValuedEAttribute)
			removeEAttributeValue.assertAffectedEObject(affectedEObject)
			removeEAttributeValue.assertAffectedEFeature(affectedFeature)
			removeEAttributeValue.assertOldValue(oldValue)
			removeEAttributeValue.assertNewValue(newValue)
		}

		def public static <A extends EObject,T,S extends SubtractiveAttributeEChange<A,T>> ExplicitUnsetEFeature<A,T> assertExplicitUnset(EChange change) {
			val unsetChange = change.assertObjectInstanceOf(ExplicitUnsetEFeature)
			Assert.assertEquals("atomic changes should be the same than the subtractive changes",
				unsetChange.atomicChanges, unsetChange.subtractiveChanges)
			return unsetChange
		}

		def public static assertInsertRootEObject(EChange change, Object newValue, String uri) {
			val insertRoot = change.assertObjectInstanceOf(InsertRootEObject)
			insertRoot.assertNewValue(newValue)
			insertRoot.assertUri(uri)
		}

		def public static assertRemoveRootEObject(EChange change, Object oldValue, String uri) {
			val removeRoot = change.assertObjectInstanceOf(RemoveRootEObject)
			removeRoot.assertOldValue(oldValue)
			removeRoot.assertUri(uri)
		}

		def public static assertMoveEObject(EChange change, int atomicChanges) {
			val moveEObject = assertObjectInstanceOf(change, MoveEObject)
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

			def public static <A extends EObject, T extends EObject> InsertEReference<A,T> assertInsertEReference(EChange change, A affectedEObject, EStructuralFeature affectedFeature,
				T expectedNewValue, int expectedIndex, boolean isContainment) {
				val insertEReference = change.assertObjectInstanceOf(InsertEReference)
				insertEReference.assertAffectedEObject(affectedEObject)
				insertEReference.assertAffectedEFeature(affectedFeature)
				insertEReference.assertNewValue(expectedNewValue)
				insertEReference.assertIndex(expectedIndex)
				insertEReference.assertContainment(isContainment)
				return insertEReference
			}

			def public static assertInsertEAttribute(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
				Object expectedValue, int expectedIndex) {
				val insertEAttributValue = assertObjectInstanceOf(change, InsertEAttributeValue)
				insertEAttributValue.assertAffectedEObject(insertEAttributValue.affectedEObject as EObject)
				insertEAttributValue.assertNewValue(expectedValue)
				insertEAttributValue.assertIndex(expectedIndex)
				insertEAttributValue.assertAffectedEFeature(affectedFeature)
			}

			public def static assertCreateEObject(EChange change, EObject affectedEObject) {
				val createEObject = assertObjectInstanceOf(change, CreateEObject);
				createEObject.assertAffectedEObject(affectedEObject);
			}
			
			public def static assertDeleteEObject(EChange change, EObject affectedEObject) {
				val deleteEObject = assertObjectInstanceOf(change, DeleteEObject);
				deleteEObject.assertAffectedEObject(affectedEObject);
			}
			
			
			def public static <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A,T> assertCreateAndInsertNonRoot(EChange change, A affectedEObject, EStructuralFeature affectedFeature,
				T expectedNewValue, int expectedIndex) {
				val createAndInsert = change.assertObjectInstanceOf(CreateAndInsertNonRoot)
				createAndInsert.createChange.assertCreateEObject(expectedNewValue);
				createAndInsert.insertChange.assertInsertEReference(affectedEObject, affectedFeature, expectedNewValue, expectedIndex, true);
				return createAndInsert
			}
		}
		