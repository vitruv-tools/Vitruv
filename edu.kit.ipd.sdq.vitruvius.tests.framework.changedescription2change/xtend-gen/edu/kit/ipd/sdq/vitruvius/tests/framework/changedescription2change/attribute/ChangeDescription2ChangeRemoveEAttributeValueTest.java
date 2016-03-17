package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.attribute;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil;
import java.util.List;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2ChangeRemoveEAttributeValueTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public void testUnsetRemoveEAttributeValue() {
    ChangeDescription2ChangeTransformationTestUtil.testReplaceSingleValuedAttribute(this, 0, 42);
    this.startRecording();
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.SINGE_VALUE_E_ATTRIBUTE_NAME);
    this.rootElement.eUnset(_feautreByName);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.<RemoveEAttributeValue>assertSingleChangeWithType(changes, RemoveEAttributeValue.class);
    Object _get = changes.get(0);
    final RemoveEAttributeValue<?, ?> removeEAttributeValue = ((RemoveEAttributeValue<?, ?>) _get);
    ChangeAssertHelper.assertAffectedEObject(removeEAttributeValue, this.rootElement);
    ChangeAssertHelper.assertOldValue(removeEAttributeValue, Integer.valueOf(42));
  }
  
  @Test
  public void testRemoveEAttributeValue() {
    ChangeDescription2ChangeTransformationTestUtil.testReplaceSingleValuedAttribute(this, 0, 42);
    this.startRecording();
    ChangeDescription2ChangeTransformationTestUtil.testReplaceSingleValuedAttribute(this, 42, 0);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.<RemoveEAttributeValue>assertSingleChangeWithType(changes, RemoveEAttributeValue.class);
    Object _get = changes.get(0);
    final RemoveEAttributeValue<?, ?> removeEAttributeValue = ((RemoveEAttributeValue<?, ?>) _get);
    ChangeAssertHelper.assertAffectedEObject(removeEAttributeValue, this.rootElement);
    ChangeAssertHelper.assertOldValue(removeEAttributeValue, Integer.valueOf(42));
  }
}
