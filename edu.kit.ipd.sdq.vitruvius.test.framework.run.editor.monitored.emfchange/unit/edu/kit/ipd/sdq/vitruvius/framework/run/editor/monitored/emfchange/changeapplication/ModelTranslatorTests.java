package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication.ModelTranslator.PathElement;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Models;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.BasicTestCase;

public class ModelTranslatorTests extends BasicTestCase {

    private ModelTranslator createModelTranslator(URL source, URL target) {
        Resource sourceR = Models.loadModel(source);
        Resource targetR = Models.loadModel(target);
        targetR.setURI(sourceR.getURI());
        return new ModelTranslator(sourceR, targetR);
    }

    @Test(expected = NoSuchElementException.class)
    public void translateNonExistingObjectFails() {
        ModelTranslator translator = createModelTranslator(Files.EXAMPLEMODEL_ECORE, Files.EMPTY_ECORE);
        Resource source = translator.getSourceResource();
        EPackage rootPkg = (EPackage) source.getContents().get(0);
        EClass anyClass = (EClass) rootPkg.getEClassifiers().get(0);
        System.err.println(translator.lookupInTarget(anyClass)); // target is empty, should fail.
    }

    @Test(expected = NoSuchElementException.class)
    public void translateFailsIfDifferentTypeOnPathToSourceObject() {
        ModelTranslator translator = createModelTranslator(Files.EXAMPLEMODEL_ECORE, Files.DATATYPE_ECORE);
        Resource source = translator.getSourceResource();
        EPackage rootPkg = (EPackage) source.getContents().get(0);
        EClass anyClass = (EClass) rootPkg.getEClassifiers().get(1);
        EOperation anyOperation = anyClass.getEOperations().get(0);
        System.err.println(translator.lookupInTarget(anyOperation)); // target has datatype on path,
                                                                     // should fail.
    }

    @Test
    public void pathIsComputedForRootElement() {
        ModelTranslator translator = createModelTranslator(Files.EXAMPLEMODEL_ECORE, Files.EXAMPLEMODEL_ECORE);
        Resource source = translator.getSourceResource();
        EPackage srcRootPkg = (EPackage) source.getContents().get(0);
        List<PathElement> path = translator.getPathFromRootElement(srcRootPkg);

        assert !path.isEmpty();
        PathElement firstPathElement = path.get(0);
        assert firstPathElement.getObj() == srcRootPkg;
        assert firstPathElement.getPosition() == -1;
    }

    @Test
    public void pathIsComputedForMethodInEcore() {
        ModelTranslator translator = createModelTranslator(Files.EXAMPLEMODEL_ECORE, Files.EXAMPLEMODEL_ECORE);
        Resource source = translator.getSourceResource();
        EPackage srcRootPkg = (EPackage) source.getContents().get(0);
        EList<EClassifier> pkgContents = srcRootPkg.getEClassifiers();

        EClass anySrcClass = (EClass) pkgContents.get(1);
        EOperation anySrcOperation = anySrcClass.getEAllOperations().get(0);

        List<PathElement> path = translator.getPathFromRootElement(anySrcOperation);

        assert !path.isEmpty();
        assert path.size() == 3 : "Path size=3 expected, got " + path.size();
        PathElement firstPathElement = path.get(0);
        assert firstPathElement.getObj() == srcRootPkg;
        assert firstPathElement.getPosition() == -1;

        PathElement secondPathElement = path.get(1);
        assert secondPathElement.getObj() == anySrcClass;
        assert secondPathElement.getPosition() == 1;

        PathElement thirdPathElement = path.get(2);
        assert thirdPathElement.getObj() == anySrcOperation;
        assert thirdPathElement.getPosition() == 0;
    }

    @Test
    public void translateClassInEcore() {
        ModelTranslator translator = createModelTranslator(Files.EXAMPLEMODEL_ECORE, Files.EXAMPLEMODEL_ECORE);
        Resource source = translator.getSourceResource();
        EPackage srcRootPkg = (EPackage) source.getContents().get(0);
        EClass anySrcClass = (EClass) srcRootPkg.getEClassifiers().get(0);
        EClass translatedClass = (EClass) translator.lookupInTarget(anySrcClass);

        assert (anySrcClass.getName().equals(translatedClass.getName())) : "Translated class does not match source class.";
    }
}
