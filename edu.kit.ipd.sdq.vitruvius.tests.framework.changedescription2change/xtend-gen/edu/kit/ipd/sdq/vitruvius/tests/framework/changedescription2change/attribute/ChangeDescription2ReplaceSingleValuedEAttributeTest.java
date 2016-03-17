package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.attribute;

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2ReplaceSingleValuedEAttributeTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public void testSetSingleValuedAttribute() {
    ChangeDescription2ChangeTransformationTestUtil.testReplaceSingleValuedAttribute(this, 0, 42);
  }
  
  @Test
  public void testReplaceSingleValuedAttribute() {
    this.testSetSingleValuedAttribute();
    ChangeDescription2ChangeTransformationTestUtil.testReplaceSingleValuedAttribute(this, 42, 21);
    ChangeDescription2ChangeTransformationTestUtil.testReplaceSingleValuedAttribute(this, 21, 0);
  }
}
