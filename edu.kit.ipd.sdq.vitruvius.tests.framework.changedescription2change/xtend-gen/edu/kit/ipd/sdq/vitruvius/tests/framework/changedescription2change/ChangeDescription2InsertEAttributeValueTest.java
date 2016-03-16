package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change;

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2InsertEAttributeValueTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public void testInsertEAttributeValue() {
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 42, (-1));
  }
  
  @Test
  public void testMultipleInsertEAttributeValue() {
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 42, (-1));
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 1, 21, (-1));
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 2, 10, (-1));
  }
  
  @Test
  public void testInsertAtPositionInsertEAttributeValue() {
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 42, (-1));
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 0, 21, 0);
    ChangeDescription2ChangeTransformationTestUtil.testInsertEAttributeValue(this, 1, 10, 1);
  }
}
