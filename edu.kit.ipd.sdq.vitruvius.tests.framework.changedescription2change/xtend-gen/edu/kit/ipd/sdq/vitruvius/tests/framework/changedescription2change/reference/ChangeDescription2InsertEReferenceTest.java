package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
    this.assertInsertEReferenceTest(nonRoot, this.rootElement, ChangeDescription2ChangeTransformationTest.MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot, expectedIndex, true);
  }
  
  private void testInsertInEReference(final int expectedIndex) {
    final NonRoot nonRoot = this.createNonRootInInsertEFerence(true);
    EList<NonRoot> _multiValuedNonContainmentEReference = this.rootElement.getMultiValuedNonContainmentEReference();
    _multiValuedNonContainmentEReference.add(expectedIndex, nonRoot);
    this.assertInsertEReferenceTest(nonRoot, this.rootElement, ChangeDescription2ChangeTransformationTest.MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot, expectedIndex, false);
  }
  
  private NonRoot createNonRootInInsertEFerence(final boolean startRecording) {
    NonRoot _xblockexpression = null;
    {
      final EStructuralFeature feature = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME);
      _xblockexpression = this.createAndAddNonRootToFeature(feature, startRecording);
    }
    return _xblockexpression;
  }
  
  private void assertInsertEReferenceTest(final NonRoot nonRoot, final EObject affectedEObject, final String featureName, final Object expectedNewValue, final int expectedIndex, final boolean isContainment) {
    final List<?> changes = this.getChanges();
    final InsertEReference insertEReference = ChangeAssertHelper.<InsertEReference>assertSingleChangeWithType(changes, InsertEReference.class);
    ChangeAssertHelper.assertAffectedEObject(insertEReference, affectedEObject);
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(this.rootElement, featureName);
    ChangeAssertHelper.assertAffectedEFeature(insertEReference, _feautreByName);
    ChangeAssertHelper.assertNewValue(insertEReference, expectedNewValue);
    ChangeAssertHelper.assertIndex(insertEReference, expectedIndex);
    ChangeAssertHelper.assertContainment(insertEReference, isContainment);
    ChangeAssertHelper.assertIsCreate(insertEReference, isContainment);
  }
}
