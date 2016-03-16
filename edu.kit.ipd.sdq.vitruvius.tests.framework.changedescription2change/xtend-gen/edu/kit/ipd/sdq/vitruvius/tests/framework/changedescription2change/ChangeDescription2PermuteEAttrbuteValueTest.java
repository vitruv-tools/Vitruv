package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2PermuteEAttrbuteValueTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public int permuteAttributeTest() {
    int _xblockexpression = (int) 0;
    {
      ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 42, (-1));
      ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 21, (-1));
      this.startRecording();
      EList<Integer> _multiValuedEAttribute = this.rootElement.getMultiValuedEAttribute();
      EList<Integer> _multiValuedEAttribute_1 = this.rootElement.getMultiValuedEAttribute();
      Integer _remove = _multiValuedEAttribute_1.remove(0);
      _multiValuedEAttribute.add(_remove);
      final List<?> changes = this.getChanges();
      ChangeAssertHelper.assertSingleChangeWithType(changes, PermuteEAttributeValues.class);
      Object _get = changes.get(0);
      final PermuteEAttributeValues<?> permuteEAttributeValues = ((PermuteEAttributeValues<?>) _get);
      ChangeAssertHelper.assertAffectedEObject(permuteEAttributeValues, this.rootElement);
      EClass _eClass = this.rootElement.eClass();
      EStructuralFeature _eStructuralFeature = _eClass.getEStructuralFeature("multiValuedEAttribute");
      ChangeAssertHelper.assertAffectedEFeature(permuteEAttributeValues, _eStructuralFeature);
      permuteEAttributeValues.getNewIndex();
      _xblockexpression = permuteEAttributeValues.getOldIndex();
    }
    return _xblockexpression;
  }
}
