package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.attribute;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2PermuteEAttrbuteValueTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public void permuteAttributeTest() {
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 42, (-1));
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 21, (-1));
    this.startRecording();
    EList<Integer> _multiValuedEAttribute = this.rootElement.getMultiValuedEAttribute();
    EList<Integer> _multiValuedEAttribute_1 = this.rootElement.getMultiValuedEAttribute();
    Integer _remove = _multiValuedEAttribute_1.remove(0);
    _multiValuedEAttribute.add(_remove);
    final List<Integer> expectedIndicesForElementsAtOldIndices = Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0)));
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertPermuteListTest(changes, this.rootElement, expectedIndicesForElementsAtOldIndices, ChangeDescription2ChangeTransformationTest.MULTI_VALUE_E_ATTRIBUTE_NAME, PermuteEAttributeValues.class);
  }
  
  @Test
  public void permuteAttributeTestComplex() {
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 42, (-1));
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 21, (-1));
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 23, (-1));
    this.startRecording();
    EList<Integer> _multiValuedEAttribute = this.rootElement.getMultiValuedEAttribute();
    EList<Integer> _multiValuedEAttribute_1 = this.rootElement.getMultiValuedEAttribute();
    Integer _remove = _multiValuedEAttribute_1.remove(2);
    _multiValuedEAttribute.add(1, _remove);
    final List<Integer> expectedIndicesForElementsAtOldIndices = Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(1)));
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertPermuteListTest(changes, this.rootElement, expectedIndicesForElementsAtOldIndices, ChangeDescription2ChangeTransformationTest.MULTI_VALUE_E_ATTRIBUTE_NAME, PermuteEAttributeValues.class);
  }
}
