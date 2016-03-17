package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change;

import allElementTypes.AllElementTypesFactory;
import allElementTypes.NonRoot;
import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.framework.changedescription2change.ChangeDescription2ChangeTransformation;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.junit.After;
import org.junit.Before;

/**
 * This class is the test class for the new {@link ChangeDescription2ChangeTransformation}. It
 * reuses some test cases from the test for the old metamodel that can be found in the project
 * edu.kit.ipd.sdq.vitruvius.tests.casestudies.emf.changedescription2change However, it is adapted
 * to test the new change metamodel.
 * @author langhamm
 */
@SuppressWarnings("all")
public abstract class ChangeDescription2ChangeTransformationTest {
  protected ChangeRecorder changeRecorder;
  
  protected Root rootElement;
  
  public final static String MULI_VALUED_E_ATTRIBUTE_NAME = "multiValuedEAttribute";
  
  public final static String SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME = "singleValuedContainmentEReference";
  
  public final static String SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "singleValuedNonContainmentEReference";
  
  public final static String SINGE_VALUE_E_ATTRIBUTE_NAME = "singleValuedEAttribute";
  
  public final static String MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME = "multiValuedContainmentEReference";
  
  public final static String MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "multiValuedNonContainmentEReference";
  
  /**
   * Create a new model and initialize the change monitoring
   */
  @Before
  public void beforeTest() {
    Root _createRoot = AllElementTypesFactory.eINSTANCE.createRoot();
    this.rootElement = _createRoot;
    ChangeRecorder _changeRecorder = new ChangeRecorder();
    this.changeRecorder = _changeRecorder;
  }
  
  @After
  public void afterTest() {
    boolean _isRecording = this.changeRecorder.isRecording();
    if (_isRecording) {
      this.changeRecorder.endRecording();
    }
    this.changeRecorder.dispose();
  }
  
  public List<?> getChanges() {
    final ChangeDescription changesDescriptions = this.changeRecorder.endRecording();
    return ChangeDescription2ChangeTransformation.transform(changesDescriptions);
  }
  
  public void startRecording() {
    List<Root> _asList = Arrays.<Root>asList(this.rootElement);
    this.changeRecorder.beginRecording(_asList);
  }
  
  public Root getRootElement() {
    return this.rootElement;
  }
  
  protected NonRoot createAndAddNonRootToFeature(final EStructuralFeature eStructuralFeature, final boolean shouldStartRecording) {
    final NonRoot nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
    this.rootElement.eSet(eStructuralFeature, nonRoot);
    if (shouldStartRecording) {
      this.startRecording();
    }
    return nonRoot;
  }
  
  protected NonRoot createAndAddNonRootToContainment(final boolean shouldStartRecording) {
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME);
    return this.createAndAddNonRootToFeature(_feautreByName, shouldStartRecording);
  }
}
