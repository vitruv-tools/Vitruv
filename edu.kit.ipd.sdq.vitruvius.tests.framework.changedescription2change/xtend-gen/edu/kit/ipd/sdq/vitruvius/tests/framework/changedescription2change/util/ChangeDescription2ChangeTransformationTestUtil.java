package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util;

import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

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
    changeDescription2Change.startRecording();
    if ((position == (-1))) {
      Root _rootElement = changeDescription2Change.getRootElement();
      EList<Integer> _multiValuedEAttribute = _rootElement.getMultiValuedEAttribute();
      _multiValuedEAttribute.add(Integer.valueOf(expectedValue));
    } else {
      Root _rootElement_1 = changeDescription2Change.getRootElement();
      EList<Integer> _multiValuedEAttribute_1 = _rootElement_1.getMultiValuedEAttribute();
      _multiValuedEAttribute_1.add(position, Integer.valueOf(expectedValue));
    }
    final List<?> changes = changeDescription2Change.getChanges();
    ChangeAssertHelper.<InsertEAttributeValue>assertSingleChangeWithType(changes, InsertEAttributeValue.class);
    Object _get = changes.get(0);
    final InsertEAttributeValue<?, ?> insertEAttributValue = ((InsertEAttributeValue<?, ?>) _get);
    EObject _affectedEObject = insertEAttributValue.getAffectedEObject();
    ChangeAssertHelper.assertAffectedEObject(insertEAttributValue, _affectedEObject);
    ChangeAssertHelper.assertNewValue(insertEAttributValue, Integer.valueOf(expectedValue));
    ChangeAssertHelper.assertIndex(insertEAttributValue, expectedValue);
    Root _rootElement_2 = changeDescription2Change.getRootElement();
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(_rootElement_2, 
      ChangeDescription2ChangeTransformationTest.MULI_VALUED_E_ATTRIBUTE_NAME);
    ChangeAssertHelper.assertAffectedEFeature(insertEAttributValue, _feautreByName);
  }
}
