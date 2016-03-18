package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util;

import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.List;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class ChangeDescription2ChangeTransformationTestUtil {
  private ChangeDescription2ChangeTransformationTestUtil() {
  }
  
  public static void testReplaceSingleValuedAttribute(final ChangeDescription2ChangeTransformationTest test, final int oldValue, final int newValue) {
    test.startRecording();
    Root _rootElement = test.getRootElement();
    _rootElement.setSingleValuedEAttribute(Integer.valueOf(newValue));
    final List<?> changes = test.getChanges();
    ChangeAssertHelper.<ReplaceSingleValuedEAttribute>assertSingleChangeWithType(changes, ReplaceSingleValuedEAttribute.class);
    Object _get = changes.get(0);
    final ReplaceSingleValuedEAttribute<?, ?> replaceEAttribute = ((ReplaceSingleValuedEAttribute<?, ?>) _get);
    ChangeAssertHelper.assertOldAndNewValue(replaceEAttribute, Integer.valueOf(oldValue), Integer.valueOf(newValue));
  }
  
  public static void testInsertEAttributeValue(final ChangeDescription2ChangeTransformationTest changeDescription2Change, final int expectedIndex, final int expectedValue, final int position) {
    int index = expectedIndex;
    changeDescription2Change.startRecording();
    if (((-1) == position)) {
      Root _rootElement = changeDescription2Change.getRootElement();
      EList<Integer> _multiValuedEAttribute = _rootElement.getMultiValuedEAttribute();
      _multiValuedEAttribute.add(Integer.valueOf(expectedValue));
      Root _rootElement_1 = changeDescription2Change.getRootElement();
      EList<Integer> _multiValuedEAttribute_1 = _rootElement_1.getMultiValuedEAttribute();
      int _size = _multiValuedEAttribute_1.size();
      int _minus = (_size - 1);
      index = _minus;
    } else {
      Root _rootElement_2 = changeDescription2Change.getRootElement();
      EList<Integer> _multiValuedEAttribute_2 = _rootElement_2.getMultiValuedEAttribute();
      _multiValuedEAttribute_2.add(position, Integer.valueOf(expectedValue));
    }
    final List<?> changes = changeDescription2Change.getChanges();
    Root _rootElement_3 = changeDescription2Change.getRootElement();
    ChangeAssertHelper.assertInsertEAttribute(changes, _rootElement_3, 
      ChangeDescription2ChangeTransformationTest.MULTI_VALUE_E_ATTRIBUTE_NAME, Integer.valueOf(expectedValue), index);
  }
}
