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
        assert backTranslation == anySrcClass;
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
