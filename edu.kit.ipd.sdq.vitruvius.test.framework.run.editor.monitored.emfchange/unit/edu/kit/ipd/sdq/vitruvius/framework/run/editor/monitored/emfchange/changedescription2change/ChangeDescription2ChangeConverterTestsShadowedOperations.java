package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.ChangeAssert;

public class ChangeDescription2ChangeConverterTestsShadowedOperations extends
        AbstractChangeDescription2ChangeConverterTests<EPackage> {

    @Override
    protected URL getModel() {
        return Files.EXAMPLEMODEL_ECORE;
    }

    @Test
    public void shadowedCreateInMultiplicityMany() {
        EClass newClass = EcoreFactory.eINSTANCE.createEClass();
        newClass.setName("ShadowedAddClass");
        EOperation shadowedOperation = EcoreFactory.eINSTANCE.createEOperation();
        shadowedOperation.setName("ShadowedOperation");
        newClass.getEOperations().add(shadowedOperation);
        sourceRoot.getEClassifiers().add(newClass);
        List<Change> changes = getChangesAndEndRecording();

        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE + 2 * CHANGES_PER_SET);
        ChangeAssert.assertContainsAddChange(changes, newClass.eContainingFeature(), newClass);
        ChangeAssert.assertContainsAddChange(changes, shadowedOperation.eContainingFeature(), shadowedOperation);
        ChangeAssert.assertContainsAttributeChange(changes, shadowedOperation.eClass().getEStructuralFeature("name"),
                "ShadowedOperation");
        ChangeAssert.assertContainsAttributeChange(changes, newClass.eClass().getEStructuralFeature("name"),
                "ShadowedAddClass");
        ChangeAssert.assertContainsListChange(changes, newClass.eContainingFeature(), newClass, newClass.eContainer(),
                sourceRoot.getEClassifiers().size() - 1, true);
        ChangeAssert.assertContainsListChange(changes, shadowedOperation.eContainingFeature(), shadowedOperation,
                shadowedOperation.eContainer(), 0, true);
    }

    @Test
    public void shadowedCreateInMultiplicityOne() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EOperation operation = EcoreFactory.eINSTANCE.createEOperation();
        EGenericType shadowedGenericType = EcoreFactory.eINSTANCE.createEGenericType();
        operation.setEGenericType(shadowedGenericType);
        exampleClass1.getEOperations().add(operation);

        List<Change> changes = getChangesAndEndRecording();

        // The additional set operation is caused by an automatic update to eType in the operation.
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE + 1 * CHANGES_PER_SET);
        ChangeAssert.assertContainsAddChange(changes, operation.eContainingFeature(), operation);
        ChangeAssert.assertContainsAddChange(changes, shadowedGenericType.eContainingFeature(), shadowedGenericType);
    }

    @Test
    public void shadowedDeleteInMultiplicityMany() {
        EClass exampleClass3 = (EClass) sourceRoot.getEClassifier("ExampleClass3");
        EList<EAnnotation> annotations = exampleClass3.getEAnnotations();
        sourceRoot.getEClassifiers().remove(exampleClass3);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 4 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                exampleClass3);
        for (EAnnotation annotation : annotations) {
            ChangeAssert.assertContainsRemoveChange(changes,
                    exampleClass3.eClass().getEStructuralFeature("eAnnotations"), annotation);
        }
    }

    @Test
    public void shadowedDeleteInMultiplicityOne() {
        EClass exampleClass4 = (EClass) sourceRoot.getEClassifier("ExampleClass4");
        EOperation operation = exampleClass4.getEOperations().get(0);
        EGenericType removedGenericType = operation.getEGenericType();
        exampleClass4.getEOperations().remove(operation);

        List<Change> changes = getChangesAndEndRecording();

        // The unset operation is caused by the set of ExampleClass4 operations being emptied.
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE + CHANGES_PER_UNSET);
        ChangeAssert.assertContainsRemoveChange(changes, exampleClass4.eClass().getEStructuralFeature("eOperations"),
                operation);
        ChangeAssert.assertContainsRemoveChange(changes, operation.eClass().getEStructuralFeature("eGenericType"),
                removedGenericType);
    }
}
