package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2InsertEReferenceTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public void testInsertEReferenceNonContainment() {
    this.testInsertInEReference(0);
  }
  
  @Test
  public void testMultipleInsertEReferenceNonContainment() {
    this.testInsertInEReference(0);
    this.testInsertInEReference(1);
    this.testInsertInEReference(2);
    this.testInsertInEReference(1);
  }
  
  @Test
  public void testInsertEReferenceContainment() {
    this.testInsertInContainmentEReference(0);
  }
  
  @Test
  public void testMultipleInsertEReferenceContainment() {
    this.testInsertInContainmentEReference(0);
    this.testInsertInContainmentEReference(1);
    this.testInsertInContainmentEReference(2);
    this.testInsertInContainmentEReference(1);
  }
  
  private void testInsertInContainmentEReference(final int expectedIndex) {
    this.startRecording();
    final NonRoot nonRoot = this.createNonRootInInsertEFerence(false);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertInsertEReference(changes, this.rootElement, ChangeDescription2ChangeTransformationTest.MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot, expectedIndex, true, true);
  }
  
  private void testInsertInEReference(final int expectedIndex) {
    final NonRoot nonRoot = this.createNonRootInInsertEFerence(true);
    EList<NonRoot> _multiValuedNonContainmentEReference = this.rootElement.getMultiValuedNonContainmentEReference();
    _multiValuedNonContainmentEReference.add(expectedIndex, nonRoot);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertInsertEReference(changes, this.rootElement, ChangeDescription2ChangeTransformationTest.MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot, expectedIndex, false, false);
  }
  
  private NonRoot createNonRootInInsertEFerence(final boolean startRecording) {
    NonRoot _xblockexpression = null;
    {
      final EStructuralFeature feature = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME);
      _xblockexpression = this.createAndAddNonRootToFeature(feature, startRecording);
    }
    return _xblockexpression;
  }
}
