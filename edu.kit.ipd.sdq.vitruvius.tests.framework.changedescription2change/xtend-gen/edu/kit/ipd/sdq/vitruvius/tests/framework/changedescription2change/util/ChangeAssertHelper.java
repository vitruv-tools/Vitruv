package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Assert;

@SuppressWarnings("all")
public class ChangeAssertHelper {
  private ChangeAssertHelper() {
  }
  
  public static void assertSingleChangeWithType(final List<?> changes, final Class<?> type) {
    CollectionBridge.claimOne(changes);
    Object _get = changes.get(0);
    ChangeAssertHelper.assertObjectInstanceOf(_get, type);
  }
  
  public static void assertObjectInstanceOf(final Object object, final Class<?> type) {
    Class<?> _class = object.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("The object " + _simpleName);
    String _plus_1 = (_plus + " should be type of ");
    String _simpleName_1 = type.getSimpleName();
    String _plus_2 = (_plus_1 + _simpleName_1);
    boolean _isInstance = type.isInstance(object);
    Assert.assertTrue(_plus_2, _isInstance);
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
    Assert.assertEquals("The actual affected EStructuralFeature is a different one than the expected EStructuralFeature", _affectedFeature, expectedEFeature);
  }
}
