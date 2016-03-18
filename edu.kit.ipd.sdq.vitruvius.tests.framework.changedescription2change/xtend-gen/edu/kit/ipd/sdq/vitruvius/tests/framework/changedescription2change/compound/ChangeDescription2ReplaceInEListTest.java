package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.compound;

import allElementTypes.AllElementTypesFactory;
import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2ReplaceInEListTest extends ChangeDescription2ChangeTransformationTest {
  @Test
  public void testReplaceInEContainmentList() {
    final NonRoot nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
    final NonRoot nonRoot2 = AllElementTypesFactory.eINSTANCE.createNonRoot();
    EList<NonRoot> _multiValuedContainmentEReference = this.rootElement.getMultiValuedContainmentEReference();
    _multiValuedContainmentEReference.add(nonRoot);
    this.startRecording();
    EList<NonRoot> _multiValuedContainmentEReference_1 = this.rootElement.getMultiValuedContainmentEReference();
    _multiValuedContainmentEReference_1.remove(nonRoot);
    EList<NonRoot> _multiValuedContainmentEReference_2 = this.rootElement.getMultiValuedContainmentEReference();
    _multiValuedContainmentEReference_2.add(nonRoot2);
    final List<?> changes = this.getChanges();
    final Pair<RemoveFromEList, InsertInEList> insertAndRemoveChange = ChangeAssertHelper.assertReplaceInEList(changes, 2);
    RemoveFromEList _first = insertAndRemoveChange.getFirst();
    ChangeAssertHelper.assertSubtractiveChange(Collections.<RemoveFromEList>unmodifiableList(CollectionLiterals.<RemoveFromEList>newArrayList(_first)), this.rootElement, 
      ChangeDescription2ChangeTransformationTest.MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0, true, true);
    InsertInEList _second = insertAndRemoveChange.getSecond();
    ChangeAssertHelper.assertInsertEReference(Collections.<InsertInEList>unmodifiableList(CollectionLiterals.<InsertInEList>newArrayList(_second)), this.rootElement, 
      ChangeDescription2ChangeTransformationTest.MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot2, 0, true, true);
  }
  
  @Test
  public void testReplaceInNonEContainmentList() {
    final NonRoot nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot();
    final NonRoot nonRoot2 = AllElementTypesFactory.eINSTANCE.createNonRoot();
    EList<NonRoot> _multiValuedNonContainmentEReference = this.rootElement.getMultiValuedNonContainmentEReference();
    _multiValuedNonContainmentEReference.add(nonRoot);
    this.startRecording();
    EList<NonRoot> _multiValuedNonContainmentEReference_1 = this.rootElement.getMultiValuedNonContainmentEReference();
    _multiValuedNonContainmentEReference_1.remove(nonRoot);
    EList<NonRoot> _multiValuedNonContainmentEReference_2 = this.rootElement.getMultiValuedNonContainmentEReference();
    _multiValuedNonContainmentEReference_2.add(nonRoot2);
    final List<?> changes = this.getChanges();
    final Pair<RemoveFromEList, InsertInEList> insertAndRemoveChange = ChangeAssertHelper.assertReplaceInEList(changes, 2);
    RemoveFromEList _first = insertAndRemoveChange.getFirst();
    ChangeAssertHelper.assertSubtractiveChange(Collections.<RemoveFromEList>unmodifiableList(CollectionLiterals.<RemoveFromEList>newArrayList(_first)), this.rootElement, 
      ChangeDescription2ChangeTransformationTest.MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0, false, false);
    InsertInEList _second = insertAndRemoveChange.getSecond();
    ChangeAssertHelper.assertInsertEReference(Collections.<InsertInEList>unmodifiableList(CollectionLiterals.<InsertInEList>newArrayList(_second)), this.rootElement, 
      ChangeDescription2ChangeTransformationTest.MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot2, 0, false, false);
  }
  
  @Test
  public void testReplaceInAttributeList() {
    EList<Integer> _multiValuedEAttribute = this.rootElement.getMultiValuedEAttribute();
    _multiValuedEAttribute.add(Integer.valueOf(42));
    this.startRecording();
    EList<Integer> _multiValuedEAttribute_1 = this.rootElement.getMultiValuedEAttribute();
    _multiValuedEAttribute_1.remove(0);
    EList<Integer> _multiValuedEAttribute_2 = this.rootElement.getMultiValuedEAttribute();
    _multiValuedEAttribute_2.add(Integer.valueOf(21));
    final List<?> changes = this.getChanges();
    final Pair<RemoveFromEList, InsertInEList> insertAndRemoveChange = ChangeAssertHelper.assertReplaceInEList(changes, 2);
    RemoveFromEList _first = insertAndRemoveChange.getFirst();
    ChangeAssertHelper.assertRemoveEAttribute(Collections.<RemoveFromEList>unmodifiableList(CollectionLiterals.<RemoveFromEList>newArrayList(_first)), this.rootElement, 
      ChangeDescription2ChangeTransformationTest.MULTI_VALUE_E_ATTRIBUTE_NAME, Integer.valueOf(42), 0);
    InsertInEList _second = insertAndRemoveChange.getSecond();
    ChangeAssertHelper.assertInsertEAttribute(Collections.<InsertInEList>unmodifiableList(CollectionLiterals.<InsertInEList>newArrayList(_second)), this.rootElement, 
      ChangeDescription2ChangeTransformationTest.MULTI_VALUE_E_ATTRIBUTE_NAME, Integer.valueOf(21), 0);
  }
}
