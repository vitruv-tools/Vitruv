package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.ChangeAssert;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.ChangeAssert.ListChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.ChangeAssert.StructuralChangeKind;

public class ChangeDescription2ChangeConverterTestsBasicOperations extends
        AbstractChangeDescription2ChangeConverterTests<EPackage> {

    @Override
    protected URL getModel() {
        return Files.EXAMPLEMODEL_ECORE;
    }

    @Test
    public void singleCreateInList() {
        EClass newClass = EcoreFactory.eINSTANCE.createEClass();
        sourceRoot.getEClassifiers().add(newClass);

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass);
    }

    @Test
    public void singleDeleteInList() {
        EClassifier removedClassifier = sourceRoot.getEClassifier("EmptyClass");
        sourceRoot.getEClassifiers().remove(removedClassifier);
        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                removedClassifier);
    }

    @Test
    public void singleSetReferenceChange() {
        EClass exampleClass1 = (EClass) (sourceRoot.getEClassifiers().get(0));
        exampleClass1.setName(exampleClass1.getName() + "!");
        String newName = exampleClass1.getName();

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_SET);
        ChangeAssert.assertContainsSingleValuedAttributeChange(changes,
                exampleClass1.eClass().getEStructuralFeature("name"), newName);
    }

    @Test
    public void multipleCreateChangeInLists() {
        EClass newClass1 = EcoreFactory.eINSTANCE.createEClass();
        sourceRoot.getEClassifiers().add(newClass1);

        EClass newClass2 = EcoreFactory.eINSTANCE.createEClass();
        sourceRoot.getEClassifiers().add(newClass2);

        EOperation operation = EcoreFactory.eINSTANCE.createEOperation();
        newClass2.getEOperations().add(operation);

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 3 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass1);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass2);
        ChangeAssert.assertContainsAddChange(changes, newClass2.eClass().getEStructuralFeature("eOperations"),
                operation);
    }

    @Test
    public void multipleDeleteInLists() {
        EClass removedClassifier = (EClass) sourceRoot.getEClassifier("ExampleClass3");
        List<EAnnotation> removedAnnotations = new ArrayList<>(removedClassifier.getEAnnotations());
        sourceRoot.getEClassifiers().remove(removedClassifier);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 4 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                removedClassifier);
        for (EAnnotation annotation : removedAnnotations) {
            ChangeAssert.assertContainsRemoveChange(changes,
                    removedClassifier.eClass().getEStructuralFeature("eAnnotations"), annotation);
        }
    }

    @Test
    public void multipleSetChangeInSameObject() {
        EClass exampleClass1 = (EClass) (sourceRoot.getEClassifiers().get(1));
        exampleClass1.setName(exampleClass1.getName() + "!");
        exampleClass1.setAbstract(!exampleClass1.isAbstract());
        String newName = exampleClass1.getName();
        boolean newAbstract = exampleClass1.isAbstract();

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 2 * CHANGES_PER_SET);
        ChangeAssert.assertContainsSingleValuedAttributeChange(changes,
                exampleClass1.eClass().getEStructuralFeature("name"), newName);
        ChangeAssert.assertContainsSingleValuedAttributeChange(changes,
                exampleClass1.eClass().getEStructuralFeature("abstract"), newAbstract);
    }

    @Test
    public void moveWithinContainmentAtBorders() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");

        EOperation o1 = exampleClass1.getEOperations().remove(1);
        exampleClass1.getEOperations().add(2, o1);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_LIST_MOVE_OP);
        ChangeAssert.assertContainsListChange(changes, exampleClass1.eClass().getEStructuralFeature("eOperations"), o1,
                exampleClass1, 1, ListChangeKind.REMOVE, StructuralChangeKind.CONTAINMENT);
        ChangeAssert.assertContainsListChange(changes, exampleClass1.eClass().getEStructuralFeature("eOperations"), o1,
                exampleClass1, 2, ListChangeKind.ADD, StructuralChangeKind.CONTAINMENT);
    }

    @Test
    public void createInEmptyList() {
        EClass classWithoutOperations = (EClass) sourceRoot.getEClassifier("DerivedClass");
        EOperation newOperation = EcoreFactory.eINSTANCE.createEOperation();
        assert classWithoutOperations.getEOperations().isEmpty();
        classWithoutOperations.getEOperations().add(newOperation);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsAddChange(changes,
                classWithoutOperations.eClass().getEStructuralFeature("eOperations"), newOperation);
        ChangeAssert.assertContainsListChange(changes,
                classWithoutOperations.eClass().getEStructuralFeature("eOperations"), newOperation,
                classWithoutOperations, 0, ListChangeKind.ADD, StructuralChangeKind.CONTAINMENT);
    }

    @Test
    public void removeAllInReferenceList() {
        EClass exampleClass3 = (EClass) sourceRoot.getEClassifier("ExampleClass3");

        List<EAnnotation> originalList = new ArrayList<>(exampleClass3.getEAnnotations());

        while (!exampleClass3.getEAnnotations().isEmpty()) {
            exampleClass3.getEAnnotations().remove(0);
        }

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_UNSET + originalList.size()
                * CHANGES_PER_CREATEREMOVE);
        for (EAnnotation op : originalList) {
            ChangeAssert.assertContainsListChange(changes,
                    exampleClass3.eClass().getEStructuralFeature("eAnnotations"), op, exampleClass3, 0,
                    ListChangeKind.REMOVE, StructuralChangeKind.CONTAINMENT);
        }
    }

    @Test
    public void moveBetweenContainmentsIsHandledAsMove() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EClass exampleClass2 = (EClass) sourceRoot.getEClassifier("ExampleClass2");

        EOperation o1 = exampleClass1.getEOperations().remove(0);
        exampleClass2.getEOperations().add(0, o1);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 2 * CHANGES_PER_LIST_ADD_DELOP + 1
                * CHANGES_PER_SET);
        ChangeAssert.assertContainsListChange(changes, exampleClass1.eClass().getEStructuralFeature("eOperations"), o1,
                exampleClass1, 0, ListChangeKind.REMOVE, StructuralChangeKind.CONTAINMENT);
        ChangeAssert.assertContainsListChange(changes, exampleClass2.eClass().getEStructuralFeature("eOperations"), o1,
                exampleClass2, 0, ListChangeKind.ADD, StructuralChangeKind.CONTAINMENT);
    }

    @Test
    public void singleUnsetAttributeFeature() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        exampleClass1.eUnset(exampleClass1.eClass().getEStructuralFeature("name"));

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_UNSET);
        ChangeAssert.assertContainsUnsetChange(changes, exampleClass1.eClass().getEStructuralFeature("name"),
                exampleClass1);
    }

    @Test
    public void singleCreateInSingleValueContainment() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EOperation operation = exampleClass1.getEOperations().get(0);

        // "wiggle" is a parameterless operation.
        assert operation.getName().equals("wiggle");

        EParameter newParam = EcoreFactory.eINSTANCE.createEParameter();
        operation.getEParameters().add(newParam);

        // eGenericType is a single-value containment reference.
        EGenericType type = EcoreFactory.eINSTANCE.createEGenericType();
        newParam.setEGenericType(type);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert
                .assertContainsAddChange(changes, operation.eClass().getEStructuralFeature("eParameters"), newParam);
        ChangeAssert.assertContainsAddChange(changes, newParam.eClass().getEStructuralFeature("eGenericType"), type);
        ChangeAssert.assertContainsListChange(changes, operation.eClass().getEStructuralFeature("eParameters"),
                newParam, operation, 0, ListChangeKind.ADD, StructuralChangeKind.CONTAINMENT);
    }

    @Test
    public void singleRemoveInSingleValueContainment() {
        EClass exampleClass2 = (EClass) sourceRoot.getEClassifier("ExampleClass2");
        EOperation operation = exampleClass2.getEOperations().get(0);

        // "wobble" is an operation containing exactly one parameter.
        assert operation.getName().equals("wobble");

        EParameter param = operation.getEParameters().get(0);
        EGenericType type = param.getEGenericType();
        assert type != null;

        EGenericType newType = EcoreFactory.eINSTANCE.createEGenericType();
        param.setEGenericType(newType);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, param.eClass().getEStructuralFeature("eGenericType"), type);
        ChangeAssert.assertContainsAddChange(changes, param.eClass().getEStructuralFeature("eGenericType"), newType);

    }

    @Test
    public void singleUnsetContainmentReferenceFeature() {
        EClass exampleClass3 = (EClass) sourceRoot.getEClassifier("ExampleClass3");
        List<EAnnotation> originalAnnotations = new ArrayList<>(exampleClass3.getEAnnotations());
        assert originalAnnotations.size() > 0;

        exampleClass3.eUnset(exampleClass3.eClass().getEStructuralFeature("eAnnotations"));

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, CHANGES_PER_UNSET + 3 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsUnsetChange(changes, exampleClass3.eClass().getEStructuralFeature("eOperations"),
                exampleClass3);
        for (EAnnotation op : originalAnnotations) {
            ChangeAssert.assertContainsRemoveChange(changes,
                    exampleClass3.eClass().getEStructuralFeature("eAnnotations"), op);
        }
    }
}
