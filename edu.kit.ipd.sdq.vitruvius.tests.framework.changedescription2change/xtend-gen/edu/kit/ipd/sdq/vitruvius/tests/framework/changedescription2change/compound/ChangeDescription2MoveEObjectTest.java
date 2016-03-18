package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.compound;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Quadruple;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2MoveEObjectTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public void testMoveEObjectFromContainmentSingleToContainmentList() {
    final NonRoot nonRoot = this.createAndAddNonRootToContainment(true);
    EList<NonRoot> _multiValuedContainmentEReference = this.rootElement.getMultiValuedContainmentEReference();
    NonRoot _singleValuedContainmentEReference = this.rootElement.getSingleValuedContainmentEReference();
    _multiValuedContainmentEReference.add(_singleValuedContainmentEReference);
    this.rootElement.setSingleValuedContainmentEReference(null);
    final List<?> changes = this.getChanges();
    final Quadruple<SubtractiveEReferenceChange<?>, UpdateEReference<?>, AdditiveEReferenceChange<?>, UpdateEReference<?>> moveChanges = ChangeAssertHelper.assertMoveEObject(changes, 2);
    SubtractiveEReferenceChange<?> _first = moveChanges.getFirst();
    ChangeAssertHelper.assertSubtractiveChange(Collections.<SubtractiveEReferenceChange<?>>unmodifiableList(CollectionLiterals.<SubtractiveEReferenceChange<?>>newArrayList(_first)), this.rootElement, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0, true, false);
    UpdateEReference<?> _second = moveChanges.getSecond();
    ChangeAssertHelper.assertAffectedEObject(_second, this.rootElement);
    UpdateEReference<?> _second_1 = moveChanges.getSecond();
    EStructuralFeature _feautreByName = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME);
    ChangeAssertHelper.assertAffectedEFeature(_second_1, _feautreByName);
    AdditiveEReferenceChange<?> _third = moveChanges.getThird();
    ChangeAssertHelper.assertInsertEReference(Collections.<AdditiveEReferenceChange<?>>unmodifiableList(CollectionLiterals.<AdditiveEReferenceChange<?>>newArrayList(_third)), this.rootElement, ChangeDescription2ChangeTransformationTest.MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0, true, false);
    UpdateEReference<?> _fourth = moveChanges.getFourth();
    ChangeAssertHelper.assertAffectedEObject(_fourth, this.rootElement);
    UpdateEReference<?> _fourth_1 = moveChanges.getFourth();
    EStructuralFeature _feautreByName_1 = ChangeAssertHelper.getFeautreByName(this.rootElement, ChangeDescription2ChangeTransformationTest.MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME);
    ChangeAssertHelper.assertAffectedEFeature(_fourth_1, _feautreByName_1);
  }
}
