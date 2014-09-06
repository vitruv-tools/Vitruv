package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.net.URL;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EParameterImpl;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;

public class ChangeApplicatorTestsUsingEcore extends AbstractChangeApplicatorTests<EPackage> {

    @Override
    protected URL getModel() {
        return Files.EXAMPLEMODEL_ECORE;
    }

    @Test
    public void simpleAddNonrootObject() {
        EClass newClass = EcoreFactory.eINSTANCE.createEClass();
        sourceRoot.getEClassifiers().add(newClass);

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void addNonrootObjectAndSetAttribute() {
        EClass newClass = EcoreFactory.eINSTANCE.createEClass();
        sourceRoot.getEClassifiers().add(newClass);
        newClass.setName("blubb");

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void setAttributeAddNonrootObjectAndSetAttribute() {
        EClass classToBeChanged = (EClass) sourceRoot.getEClassifiers().get(0);
        classToBeChanged.setAbstract(!classToBeChanged.isAbstract());

        EClass newClass = EcoreFactory.eINSTANCE.createEClass();
        sourceRoot.getEClassifiers().add(newClass);

        newClass.setName("blubb");

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void simpleDeleteNonrootObject() {
        EClass newClass = EcoreFactory.eINSTANCE.createEClass();
        sourceRoot.getEClassifiers().add(newClass);
        sourceRoot.getEClassifiers().remove(newClass);
        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void removeBeforeAdd() {
        EClass newClass1 = EcoreFactory.eINSTANCE.createEClass();
        EClass newClass2 = EcoreFactory.eINSTANCE.createEClass();

        sourceRoot.getEClassifiers().remove(0);
        sourceRoot.getEClassifiers().add(newClass1);
        sourceRoot.getEClassifiers().add(1, newClass2);

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void removeAfterAdd() {
        EClass newClass1 = EcoreFactory.eINSTANCE.createEClass();
        EClass newClass2 = EcoreFactory.eINSTANCE.createEClass();
        EClass newClass3 = EcoreFactory.eINSTANCE.createEClass();

        sourceRoot.getEClassifiers().add(1, newClass1);
        sourceRoot.getEClassifiers().add(1, newClass2);
        EClassifier classifier = sourceRoot.getEClassifier("ExampleClass2");
        sourceRoot.getEClassifiers().remove(classifier);
        sourceRoot.getEClassifiers().add(newClass3);

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void moveBetweenContainments() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EClass exampleClass2 = (EClass) sourceRoot.getEClassifier("ExampleClass2");

        EOperation o1 = exampleClass1.getEOperations().remove(0);
        exampleClass2.getEOperations().add(0, o1);
        System.err.println(o1.getName());

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void setAfterMoveBetweenContainments() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EClass exampleClass2 = (EClass) sourceRoot.getEClassifier("ExampleClass2");

        // Moving the two existing operations "over cross"
        EOperation newOp = EcoreFactory.eINSTANCE.createEOperation();
        exampleClass1.getEOperations().add(newOp);
        newOp.setName("foo");
        EOperation o2 = exampleClass1.getEOperations().remove(0);
        exampleClass2.getEOperations().add(0, o2);

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void changeContainersOverCross() {
        EClass exampleClass1 = (EClass) sourceRoot.getEClassifier("ExampleClass1");
        EClass exampleClass2 = (EClass) sourceRoot.getEClassifier("ExampleClass2");

        EOperation o1 = exampleClass2.getEOperations().remove(0);
        EOperation o2 = exampleClass1.getEOperations().remove(0);
        exampleClass2.getEOperations().add(0, o2);
        exampleClass1.getEOperations().add(o1);

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void simpleShadowedAdd() {
        EClass newClass = EcoreFactory.eINSTANCE.createEClass();
        newClass.setName("ShadowedAddClass");
        EOperation shadowedOperation = EcoreFactory.eINSTANCE.createEOperation();
        shadowedOperation.setName("ShadowedOperation");
        newClass.getEOperations().add(shadowedOperation);
        sourceRoot.getEClassifiers().add(newClass);

        synchronizeChangesAndAssertEquality();
    }

    @Test
    public void setAttributeCausingShadowedInsertion() {
        EClass classWithOp = (EClass) sourceRoot.getEClassifier("ExampleClass2");
        EOperation opWithModelArg = classWithOp.getEOperations().get(0);
        assert opWithModelArg != null;
        assert opWithModelArg.getName().equals("wobble");

        EParameterImpl param = (EParameterImpl) opWithModelArg.getEParameters().get(0);
        assert param.getEType() == sourceRoot.getEClassifier("ExampleClass1");
        param.setEType(sourceRoot.getEClassifier("ExampleClass2"));

        synchronizeChangesAndAssertEquality();
    }

    @Override
    protected void registerMetamodels() {
        // Ecore metamodels do not need to be registered.
    }
}
