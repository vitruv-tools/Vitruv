package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference;

import allElementTypes.AllElementTypesFactory;
import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.List;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2ReplaceSingleValuedEReferenceTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public void testReplaceSingleValuedEReferenceContainment() {
    this.startRecording();
    final NonRoot nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
    this.rootElement.setSingleValuedContainmentEReference(nonRoot);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertReplaceSingleValuedEReference(changes, null, nonRoot, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME, 
      this.rootElement, true);
  }
  
  @Test
  public void testReplaceExistingSingleValuedEReferenceContainment() {
    final NonRoot nonRoot = this.createAndAddNonRootToContainment(true);
    final NonRoot replaceNonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
    this.rootElement.setSingleValuedContainmentEReference(replaceNonRoot);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertReplaceSingleValuedEReference(changes, nonRoot, replaceNonRoot, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME, 
      this.rootElement, true);
  }
  
  @Test
  public void testReplaceExistingSingleValuedEReferenceContainmentWithDefault() {
    final NonRoot nonRoot = this.createAndAddNonRootToContainment(true);
    this.rootElement.setSingleValuedContainmentEReference(null);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertReplaceSingleValuedEReference(changes, nonRoot, null, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME, 
      this.rootElement, true);
  }
  
  @Test
  public void testUnsetExistingSingleValuedEReferenceContainment() {
    final NonRoot nonRoot = this.createAndAddNonRootToContainment(true);
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME);
    this.rootElement.eUnset(_feautreByName);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertReplaceSingleValuedEReference(changes, nonRoot, null, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME, 
      this.rootElement, true);
  }
  
  @Test
  public void testReplaceSingleValuedEReferenceNonContainment() {
    final NonRoot nonRoot = this.createAndAddNonRootToContainment(true);
    this.rootElement.setSingleValuedNonContainmentEReference(nonRoot);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertReplaceSingleValuedEReference(changes, null, nonRoot, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, 
      this.rootElement, false);
  }
  
  @Test
  public void testReplaceExistingSingleValuedEReferenceNonContainment() {
    final NonRoot nonRoot = this.createAndAddNonRootToContainment(false);
    this.rootElement.setSingleValuedNonContainmentEReference(nonRoot);
    final NonRoot replaceNonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
    this.startRecording();
    this.rootElement.setSingleValuedNonContainmentEReference(replaceNonRoot);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertReplaceSingleValuedEReference(changes, nonRoot, replaceNonRoot, 
      ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, this.rootElement, false);
  }
  
  @Test
  public void testReplaceExistingSingleValuedEReferenceNonContainmentWithDefault() {
    final NonRoot nonRoot = this.createAndAddNonRootToContainment(true);
    this.rootElement.setSingleValuedNonContainmentEReference(null);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertReplaceSingleValuedEReference(changes, nonRoot, null, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, 
      this.rootElement, false);
  }
  
  @Test
  public void testUnsetReplaceExistingSingleValuedEReferenceNonContainment() {
    final NonRoot nonRoot = this.createAndAddNonRootToContainment(true);
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME);
    this.rootElement.eUnset(_feautreByName);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertReplaceSingleValuedEReference(changes, nonRoot, null, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, 
      this.rootElement, false);
  }
  
  private NonRoot createAndAddNonRootToContainment(final boolean shouldStartRecording) {
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME);
    return this.createAndAddNonRootToFeature(_feautreByName, shouldStartRecording);
  }
}
