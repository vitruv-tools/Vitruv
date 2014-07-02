package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.net.URL;
import java.util.List;

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

        ChangeAssert.assertChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE + 2 * CHANGES_PER_SET);
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
        ChangeAssert.assertChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE + 1 * CHANGES_PER_SET);
        ChangeAssert.assertContainsAddChange(changes, operation.eContainingFeature(), operation);
        ChangeAssert.assertContainsAddChange(changes, shadowedGenericType.eContainingFeature(), shadowedGenericType);
    }
}
