package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.ChangeAssert;

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

        ChangeAssert.assertChangeListSize(changes, CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass);
    }

    @Test
    public void singleDeleteInList() {
        EObject removedClassifier = sourceRoot.getEClassifiers().remove(0);
        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                removedClassifier);
    }

    @Test
    public void singleSetReferenceChange() {
        EClass exampleClass1 = (EClass) (sourceRoot.getEClassifiers().get(0));
        exampleClass1.setName(exampleClass1.getName() + "!");
        String newName = exampleClass1.getName();

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertChangeListSize(changes, CHANGES_PER_SET);
        Change setChange = changes.get(0);

        assert setChange instanceof EMFModelChange;
        EChange innerChange = ((EMFModelChange) setChange).getEChange();

        assert innerChange instanceof UpdateEAttributeImpl<?>;
        UpdateEAttributeImpl<?> attrChange = (UpdateEAttributeImpl<?>) innerChange;

        assert attrChange.getNewValue().equals(newName);
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

        ChangeAssert.assertChangeListSize(changes, 3 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass1);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass2);
        ChangeAssert.assertContainsAddChange(changes, newClass2.eClass().getEStructuralFeature("eOperations"),
                operation);
    }

    @Test
    public void multipleDeleteInLists() {
        EClass removedClassifier = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EOperation removedOperation = removedClassifier.getEOperations().remove(0);
        sourceRoot.getEClassifiers().remove(removedClassifier);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                removedClassifier);
        ChangeAssert.assertContainsRemoveChange(changes, removedClassifier.eClass()
                .getEStructuralFeature("eOperations"), removedOperation);
    }

    @Test
    public void multipleSetChangeInSameObject() {
        EClass exampleClass1 = (EClass) (sourceRoot.getEClassifiers().get(1));
        exampleClass1.setName(exampleClass1.getName() + "!");
        exampleClass1.setAbstract(!exampleClass1.isAbstract());
        String newName = exampleClass1.getName();
        boolean newAbstract = exampleClass1.isAbstract();

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertChangeListSize(changes, 2 * CHANGES_PER_SET);
        ChangeAssert.assertContainsAttributeChange(changes, exampleClass1.eClass().getEStructuralFeature("name"),
                newName);
        ChangeAssert.assertContainsAttributeChange(changes, exampleClass1.eClass().getEStructuralFeature("abstract"),
                newAbstract);
    }

    @Test
    public void moveWithinContainmentAtBorders() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");

        EOperation o1 = exampleClass1.getEOperations().remove(1);
        exampleClass1.getEOperations().add(2, o1);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, CHANGES_PER_LIST_MOVE_OP);
        ChangeAssert.assertContainsListChange(changes, exampleClass1.eClass().getEStructuralFeature("eOperations"), o1,
                exampleClass1, 1, false);
        ChangeAssert.assertContainsListChange(changes, exampleClass1.eClass().getEStructuralFeature("eOperations"), o1,
                exampleClass1, 2, true);
    }

    @Test
    public void createInEmptyList() {
        EClass classWithoutOperations = (EClass) sourceRoot.getEClassifier("DerivedClass");
        EOperation newOperation = EcoreFactory.eINSTANCE.createEOperation();
        assert classWithoutOperations.getEOperations().isEmpty();
        classWithoutOperations.getEOperations().add(newOperation);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsAddChange(changes,
                classWithoutOperations.eClass().getEStructuralFeature("eOperations"), newOperation);
        ChangeAssert.assertContainsListChange(changes,
                classWithoutOperations.eClass().getEStructuralFeature("eOperations"), newOperation,
                classWithoutOperations, 0, true);
    }

    @Test
    public void removeAllInReferenceList() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");

        List<EOperation> originalList = new ArrayList<>(exampleClass1.getEOperations());

        while (!exampleClass1.getEOperations().isEmpty()) {
            exampleClass1.getEOperations().remove(0);
        }

        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertChangeListSize(changes, originalList.size() * CHANGES_PER_CREATEREMOVE);
        for (EOperation op : originalList) {
            ChangeAssert.assertContainsListChange(changes, exampleClass1.eClass().getEStructuralFeature("eOperations"),
                    op, exampleClass1, 0, false);
        }
    }

    @Test
    public void moveBetweenContainmentsIsHandledAsMove() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EClass exampleClass2 = (EClass) sourceRoot.getEClassifier("ExampleClass2");

        EOperation o1 = exampleClass1.getEOperations().remove(0);
        exampleClass2.getEOperations().add(0, o1);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, 2 * CHANGES_PER_LIST_ADD_DELOP + 1 * CHANGES_PER_SET);
        ChangeAssert.assertContainsListChange(changes, exampleClass1.eClass().getEStructuralFeature("eOperations"), o1,
                exampleClass1, 0, false);
        ChangeAssert.assertContainsListChange(changes, exampleClass2.eClass().getEStructuralFeature("eOperations"), o1,
                exampleClass2, 0, true);
    }

    @Test
    public void singleUnsetAttributeFeature() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        exampleClass1.eUnset(exampleClass1.eClass().getEStructuralFeature("name"));

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, CHANGES_PER_UNSET);
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
        ChangeAssert.assertChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert
                .assertContainsAddChange(changes, operation.eClass().getEStructuralFeature("eParameters"), newParam);
        ChangeAssert.assertContainsAddChange(changes, newParam.eClass().getEStructuralFeature("eGenericType"), type);
        ChangeAssert.assertContainsListChange(changes, operation.eClass().getEStructuralFeature("eParameters"),
                newParam, operation, 0, true);
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
        ChangeAssert.assertChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, param.eClass().getEStructuralFeature("eGenericType"), type);
        ChangeAssert.assertContainsAddChange(changes, param.eClass().getEStructuralFeature("eGenericType"), newType);

    }

    @Ignore
    @Test
    public void singleUnsetContainmentReferenceFeature() {
        EClass exampleClass2 = (EClass) sourceRoot.getEClassifier("ExampleClass2");
        List<EOperation> originalOperations = new ArrayList<>(exampleClass2.getEAllOperations());
        assert originalOperations.size() > 0;

        exampleClass2.eUnset(exampleClass2.eClass().getEStructuralFeature("eOperations"));

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, CHANGES_PER_UNSET + 2 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsUnsetChange(changes, exampleClass2.eClass().getEStructuralFeature("eOperations"),
                exampleClass2);
        for (EOperation op : originalOperations) {
            ChangeAssert.assertContainsRemoveChange(changes, exampleClass2.eClass()
                    .getEStructuralFeature("eOperations"), op);
        }
    }
}
