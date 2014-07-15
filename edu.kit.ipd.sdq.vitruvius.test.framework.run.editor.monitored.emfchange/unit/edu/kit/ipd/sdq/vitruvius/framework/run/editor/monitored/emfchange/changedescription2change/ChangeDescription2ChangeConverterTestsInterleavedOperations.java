package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.ChangeAssert;

public class ChangeDescription2ChangeConverterTestsInterleavedOperations extends
        AbstractChangeDescription2ChangeConverterTests<EPackage> {

    @Override
    protected URL getModel() {
        return Files.EXAMPLEMODEL_ECORE;
    }

    @Test
    public void deleteThenCreateOther() {
        EClass newClass1 = EcoreFactory.eINSTANCE.createEClass();
        EClass newClass2 = EcoreFactory.eINSTANCE.createEClass();

        EClassifier removedClassifier = sourceRoot.getEClassifiers().remove(0);
        sourceRoot.getEClassifiers().add(newClass1);
        sourceRoot.getEClassifiers().add(newClass2);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, 3 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                removedClassifier);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass1);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass2);
    }

    @Test
    public void interleavedCreateAndSet() {
        EClass newClass = EcoreFactory.eINSTANCE.createEClass();
        sourceRoot.getEClassifiers().add(newClass);
        newClass.setAbstract(!newClass.isAbstract());

        EOperation operation = EcoreFactory.eINSTANCE.createEOperation();
        newClass.getEOperations().add(operation);
        operation.setUnique(!operation.isUnique());

        EClass targetClass = (EClass) sourceRoot.getEClassifiers().get(0);
        EOperation operation2 = EcoreFactory.eINSTANCE.createEOperation();
        targetClass.getEOperations().add(operation2);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, 3 * CHANGES_PER_CREATEREMOVE + 2 * CHANGES_PER_SET);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass);
        ChangeAssert
                .assertContainsAddChange(changes, newClass.eClass().getEStructuralFeature("eOperations"), operation);
        ChangeAssert.assertContainsAddChange(changes, targetClass.eClass().getEStructuralFeature("eOperations"),
                operation2);
        ChangeAssert.assertContainsAttributeChange(changes, newClass.eClass().getEStructuralFeature("abstract"),
                newClass.isAbstract());
        ChangeAssert.assertContainsAttributeChange(changes, operation.eClass().getEStructuralFeature("unique"),
                operation.isUnique());

        ChangeAssert.assertAllAddRemoveBeforeSetAttribute(changes);
    }

    @Test
    public void interleavedSetAndDelete() {
        EClass removedClassifier = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EOperation removedOperation = removedClassifier.getEOperations().remove(0);
        sourceRoot.setName("Foo");
        sourceRoot.getEClassifiers().remove(removedClassifier);
        sourceRoot.setNsPrefix("Bar");

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE + 2 * CHANGES_PER_SET);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                removedClassifier);
        ChangeAssert.assertContainsRemoveChange(changes, removedClassifier.eClass()
                .getEStructuralFeature("eOperations"), removedOperation);
        ChangeAssert.assertAllAddRemoveBeforeSetAttribute(changes);
    }
}
