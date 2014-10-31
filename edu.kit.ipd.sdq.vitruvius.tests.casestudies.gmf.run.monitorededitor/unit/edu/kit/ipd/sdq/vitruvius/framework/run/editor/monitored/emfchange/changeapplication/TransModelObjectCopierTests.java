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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.net.URL;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication.TransModelObjectCopier.ParentMissingException;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Models;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.BasicTestCase;

public class TransModelObjectCopierTests extends BasicTestCase {

    private TransModelObjectCopier createModelTransModelObjectCopier(URL source, URL target) {
        Resource sourceR = Models.loadModel(source);
        Resource targetR = Models.loadModel(target);
        targetR.setURI(sourceR.getURI());
        ModelTranslator translator = new ModelTranslator(sourceR, targetR);
        return new TransModelObjectCopier(translator);
    }

    @Test(expected = ParentMissingException.class)
    public void copyCannotBeAddedWhenParentIsMissingInTarget() {
        TransModelObjectCopier copier = createModelTransModelObjectCopier(Files.EXAMPLEMODEL_ECORE, Files.EMPTY_ECORE);
        Resource source = copier.getSourceResource();
        EPackage srcRootPkg = (EPackage) source.getContents().get(0);
        EClass anySrcClass = (EClass) srcRootPkg.getEClassifiers().get(0);
        EOperation anySrcOperation = anySrcClass.getEOperations().get(0);

        copier.addCopyInTarget(anySrcOperation);
    }

    @Test
    public void ecoreEClassCanBeCopiedIntoTarget() {
        TransModelObjectCopier copier = createModelTransModelObjectCopier(Files.EXAMPLEMODEL_ECORE, Files.EMPTY_ECORE);
        Resource source = copier.getSourceResource();
        EPackage srcRootPkg = (EPackage) source.getContents().get(0);
        EClass anySrcClass = (EClass) srcRootPkg.getEClassifiers().get(0);

        EClass copyClass = (EClass) copier.addCopyInTarget(anySrcClass);

        EClass backTranslation = (EClass) copier.getModelTranslator().getInverseTranslator().lookupInTarget(copyClass);

        System.err.println("Source class: " + anySrcClass);
        System.err.println("Copy class: " + copyClass);
        System.err.println("Back translation: " + backTranslation);

        assert backTranslation == anySrcClass : "Got " + backTranslation + " instead of " + anySrcClass;
    }

    @Test
    public void ecoreEOperationCanBeCopiedIntoTarget() {
        TransModelObjectCopier copier = createModelTransModelObjectCopier(Files.EXAMPLEMODEL_ECORE, Files.EMPTY_ECORE);
        Resource source = copier.getSourceResource();
        EPackage srcRootPkg = (EPackage) source.getContents().get(0);
        EClass anySrcClass = (EClass) srcRootPkg.getEClassifiers().get(0);
        EOperation anySrcOperation = anySrcClass.getEAllOperations().get(0);

        copier.addCopyInTarget(anySrcClass);

        EOperation copyOp = (EOperation) copier.addCopyInTarget(anySrcOperation);

        EOperation backTranslation = (EOperation) copier.getModelTranslator().getInverseTranslator()
                .lookupInTarget(copyOp);
        assert backTranslation == anySrcOperation;
    }
}
