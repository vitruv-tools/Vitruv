/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
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
        EAnnotation newAnnot1 = EcoreFactory.eINSTANCE.createEAnnotation();
        EAnnotation newAnnot2 = EcoreFactory.eINSTANCE.createEAnnotation();

        EClassifier exampleClass3 = sourceRoot.getEClassifier("ExampleClass3");
        EAnnotation removedAnnotation = exampleClass3.getEAnnotations().remove(0);

        exampleClass3.getEAnnotations().add(newAnnot1);
        exampleClass3.getEAnnotations().add(newAnnot2);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 3 * CHANGES_PER_CREATEREMOVE);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eAnnotations"),
                removedAnnotation);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eAnnotations"),
                newAnnot1);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eAnnotations"),
                newAnnot2);
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
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 3 * CHANGES_PER_CREATEREMOVE + 2 * CHANGES_PER_SET);
        ChangeAssert.assertContainsAddChange(changes, sourceRoot.eClass().getEStructuralFeature("eClassifiers"),
                newClass);
        ChangeAssert
                .assertContainsAddChange(changes, newClass.eClass().getEStructuralFeature("eOperations"), operation);
        ChangeAssert.assertContainsAddChange(changes, targetClass.eClass().getEStructuralFeature("eOperations"),
                operation2);
        ChangeAssert.assertContainsSingleValuedAttributeChange(changes, newClass.eClass().getEStructuralFeature("abstract"),
                newClass.isAbstract());
        ChangeAssert.assertContainsSingleValuedAttributeChange(changes, operation.eClass().getEStructuralFeature("unique"),
                operation.isUnique());

        ChangeAssert.assertAllAddRemoveBeforeSetAttribute(changes);
    }

    @Test
    public void interleavedSetAndDelete() {
        EClass exampleClass3 = (EClass) sourceRoot.getEClassifier("ExampleClass3");
        EAnnotation removedAnnotation1 = exampleClass3.getEAnnotations().get(0);
        EAnnotation removedAnnotation2 = exampleClass3.getEAnnotations().get(1);

        exampleClass3.setName("Foo");
        exampleClass3.getEAnnotations().remove(removedAnnotation1);
        exampleClass3.setAbstract(true);
        exampleClass3.getEAnnotations().remove(removedAnnotation2);

        List<Change> changes = getChangesAndEndRecording();
        ChangeAssert.assertCorrectlyOrderedAndChangeListSize(changes, 2 * CHANGES_PER_CREATEREMOVE + 2 * CHANGES_PER_SET);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eAnnotations"),
                removedAnnotation1);
        ChangeAssert.assertContainsRemoveChange(changes, sourceRoot.eClass().getEStructuralFeature("eAnnotations"),
                removedAnnotation2);
        ChangeAssert.assertAllAddRemoveBeforeSetAttribute(changes);
    }
}
