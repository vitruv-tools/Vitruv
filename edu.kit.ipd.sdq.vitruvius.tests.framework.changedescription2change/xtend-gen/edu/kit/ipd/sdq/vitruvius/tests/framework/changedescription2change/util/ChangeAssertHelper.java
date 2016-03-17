package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Assert;

@SuppressWarnings("all")
public class ChangeAssertHelper {
  private ChangeAssertHelper() {
  }
  
  public static <T extends Object> T assertSingleChangeWithType(final List<?> changes, final Class<T> type) {
    CollectionBridge.claimOne(changes);
    Object _get = changes.get(0);
    return ChangeAssertHelper.<T>assertObjectInstanceOf(_get, type);
  }
  
  public static <T extends Object> T assertObjectInstanceOf(final Object object, final Class<T> type) {
    Class<?> _class = object.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("The object " + _simpleName);
    String _plus_1 = (_plus + " should be type of ");
    String _simpleName_1 = type.getSimpleName();
    String _plus_2 = (_plus_1 + _simpleName_1);
    boolean _isInstance = type.isInstance(object);
    Assert.assertTrue(_plus_2, _isInstance);
    return type.cast(object);
  }
  
  public static void assertOldAndNewValue(final EChange eChange, final Object oldValue, final Object newValue) {
    ChangeAssertHelper.assertOldValue(eChange, oldValue);
    ChangeAssertHelper.assertNewValue(eChange, newValue);
  }
  
  public static void assertOldValue(final EChange eChange, final Object oldValue) {
    Object _oldValue = ((SubtractiveEChange<?>) eChange).getOldValue();
    Assert.assertEquals("old value must be the same than the given old value", oldValue, _oldValue);
  }
  
  public static void assertNewValue(final EChange eChange, final Object newValue) {
    Object _newValue = ((AdditiveEChange<?>) eChange).getNewValue();
    Assert.assertEquals("new value must be the same than the given new value", newValue, _newValue);
  }
  
  public static void assertAffectedEObject(final EChange eChange, final EObject expectedAffectedEObject) {
    EObject _affectedEObject = ((EFeatureChange<?, ?>) eChange).getAffectedEObject();
    Assert.assertEquals("The actual affected EObject is a different one than the expected affected EObject", _affectedEObject, expectedAffectedEObject);
  }
  
  public static void assertAffectedEFeature(final EChange eChange, final EStructuralFeature expectedEFeature) {
    EStructuralFeature _affectedFeature = ((EFeatureChange<?, ?>) eChange).getAffectedFeature();
    Assert.assertEquals(
      "The actual affected EStructuralFeature is a different one than the expected EStructuralFeature", _affectedFeature, expectedEFeature);
  }
  
  public static EStructuralFeature getFeautreByName(final EObject eObject, final String name) {
    EClass _eClass = eObject.eClass();
    return _eClass.getEStructuralFeature(name);
  }
  
  public static void assertIndices(final PermuteEList permuteChange, final List<Integer> expectedIndices) {
    EList<Integer> _newIndicesForElementsAtOldIndices = permuteChange.getNewIndicesForElementsAtOldIndices();
    Assert.assertEquals("The new indices for elements at old indices is wrong", _newIndicesForElementsAtOldIndices, expectedIndices);
  }
  
  public static void assertPermuteAttributeTest(final List<?> changes, final EObject rootElement, final List<Integer> expectedIndicesForElementsAtOldIndices) {
    ChangeAssertHelper.<PermuteEAttributeValues>assertSingleChangeWithType(changes, PermuteEAttributeValues.class);
    Object _get = changes.get(0);
    final PermuteEAttributeValues<?> permuteEAttributeValues = ((PermuteEAttributeValues<?>) _get);
    ChangeAssertHelper.assertAffectedEObject(permuteEAttributeValues, rootElement);
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(rootElement, "multiValuedEAttribute");
    ChangeAssertHelper.assertAffectedEFeature(permuteEAttributeValues, _feautreByName);
    ChangeAssertHelper.assertIndices(permuteEAttributeValues, expectedIndicesForElementsAtOldIndices);
  }
  
  public static void assertContainment(final UpdateEReference<?> updateEReference, final boolean expectedValue) {
    boolean _isContainment = updateEReference.isContainment();
    Assert.assertEquals((("The containment information of the change " + updateEReference) + " is wrong"), Boolean.valueOf(_isContainment), Boolean.valueOf(expectedValue));
  }
  
  public static void assertIsDelete(final SubtractiveEReferenceChange<?> subtractiveReference, final boolean expectedValue) {
    boolean _isIsDelete = subtractiveReference.isIsDelete();
    Assert.assertEquals((("Change " + subtractiveReference) + " shall not be a delete change"), Boolean.valueOf(_isIsDelete), Boolean.valueOf(expectedValue));
  }
  
  public static void assertIsCreate(final AdditiveEReferenceChange<?> additiveReference, final boolean expectedValue) {
    boolean _isIsCreate = additiveReference.isIsCreate();
    Assert.assertEquals((("Change " + additiveReference) + " shall not be a create change"), Boolean.valueOf(_isIsCreate), Boolean.valueOf(expectedValue));
  }
  
  public static void assertReplaceSingleValuedEReference(final List<?> changes, final Object expectedOldValue, final Object expectedNewValue, final String affectedEFeatureName, final EObject affectedEObject, final boolean isContainment) {
    final ReplaceSingleValuedEReference change = ChangeAssertHelper.<ReplaceSingleValuedEReference>assertSingleChangeWithType(changes, ReplaceSingleValuedEReference.class);
    ChangeAssertHelper.assertOldAndNewValue(change, expectedOldValue, expectedNewValue);
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(affectedEObject, affectedEFeatureName);
    ChangeAssertHelper.assertAffectedEFeature(change, _feautreByName);
    ChangeAssertHelper.assertAffectedEObject(change, affectedEObject);
    ChangeAssertHelper.assertContainment(change, isContainment);
    ChangeAssertHelper.assertIsCreate(change, isContainment);
    ChangeAssertHelper.assertIsDelete(change, isContainment);
  }
  
  public static void assertIndex(final InsertInEList change, final int expectedIndex) {
    int _index = change.getIndex();
    Assert.assertEquals("The value is not at the correct index", _index, expectedIndex);
  }
}
