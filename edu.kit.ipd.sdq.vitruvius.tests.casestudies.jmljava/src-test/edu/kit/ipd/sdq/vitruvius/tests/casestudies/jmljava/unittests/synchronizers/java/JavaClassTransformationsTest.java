package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.synchronizers.java;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.notNull;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.ModifiersPackage;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.changesynchronizer.ChangeBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.Utilities;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.synchronizers.TransformationTestsBase;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.synchronizers.TransformationTestsBase.CloneContainer;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;

public class JavaClassTransformationsTest extends TransformationTestsBase {

    private static enum ResourceFiles implements IResourceFiles {
        JAVA("JavaClassTransformationsTest.java.resource"),
        JML("JavaClassTransformationsTest.jml.resource"),
        HELPER("Helper_Elements.java.resource"),
        
        EXPECTED_AddModifier("JavaClassTransformationsTest_EXPECTED_AddModifier.jml.resource"),
        EXPECTED_AddField("JavaClassTransformationsTest_EXPECTED_AddField.jml.resource"),
        EXPECTED_AddMethod("JavaClassTransformationsTest_EXPECTED_AddMethod.jml.resource"),
        EXPECTED_DeleteModifier("JavaClassTransformationsTest_EXPECTED_DeleteModifier.jml.resource"),
        EXPECTED_DeleteMethod("JavaClassTransformationsTest_EXPECTED_DeleteMethod.jml.resource"),
        EXPECTED_DeleteField("JavaClassTransformationsTest_EXPECTED_DeleteField.jml.resource"),
        EXPECTED_ReplaceModifier("JavaClassTransformationsTest_EXPECTED_ReplaceModifier.jml.resource");

        private String modelFileName;

        private ResourceFiles(String modelFileName) {
            this.modelFileName = modelFileName;
        }

        @Override
        public String getModelFileName() {
            return modelFileName;
        }
    }

    private CompilationUnit cuJava;
    private edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit cuJML;

    @Override
    protected Pair<ModelInstance, ModelInstance> getModelInstances() throws Exception {
        ModelInstance miJava = loadModelInstance(ResourceFiles.JAVA);
        cuJava = miJava.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        ModelInstance miJML = loadModelInstance(ResourceFiles.JML);
        cuJML = miJML
                .getUniqueRootEObjectIfCorrectlyTyped(edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit.class);
        return new Pair<ModelInstance, ModelInstance>(miJava, miJML);
    }
    
    @Test
    public void testCreateModifier() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        Modifier newModifier = ModifiersFactory.eINSTANCE.createFinal();
        classifier.changed().getAnnotationsAndModifiers().add(0, newModifier);

        EMFModelChange change = ChangeBuilder.createCreateChange(newModifier, classifier.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_AddModifier, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(1, newModifier);
    }
    
    @Test
    public void testCreateField() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Field newField = helperCu.getClassifiers().get(0).getFields().get(0);
        classifier.changed().getMembers().add(0, newField);

        EMFModelChange change = ChangeBuilder.createCreateChange(newField, classifier.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_AddField, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(5, newField);
        assertNumberOfCorrespondences(1, newField.getModifiers().get(0));
        assertNumberOfCorrespondences(1, newField.getTypeReference());
    }
    
    @Test
    public void testCreateMethod() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Method newMethod = helperCu.getClassifiers().get(0).getMethods().get(0);
        classifier.changed().getMembers().add(0, newMethod);

        EMFModelChange change = ChangeBuilder.createCreateChange(newMethod, classifier.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_AddMethod, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(4, newMethod);
        assertNumberOfCorrespondences(1, newMethod.getModifiers().get(0));
        assertNumberOfCorrespondences(1, newMethod.getTypeReference());
        assertNumberOfCorrespondences(1, newMethod.getExceptions().get(0));
        assertNumberOfCorrespondences(1, newMethod.getParameters().get(0));
        assertNumberOfCorrespondences(1, newMethod.getParameters().get(0).getTypeReference());
        assertNumberOfCorrespondences(1, newMethod.getParameters().get(1));
        assertNumberOfCorrespondences(1, newMethod.getParameters().get(1).getTypeReference());
    }
    
    @Test
    public void testDeleteModifier() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        classifier.changed().getAnnotationsAndModifiers().remove(0);
        Modifier removedModifier = classifier.original().getModifiers().get(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(removedModifier, classifier.changed());

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeleteModifier, cuJML);
        assertNumberOfCorrespondences(0, removedModifier);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testDeleteMethod() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        classifier.changed().getMembers().remove(0);
        Method removedMethod = classifier.original().getMethods().get(0);

        EMFModelChange change = ChangeBuilder.createDeleteChange(removedMethod, classifier.changed());

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeleteMethod, cuJML);
        assertNumberOfCorrespondences(0, removedMethod);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testDeleteMethodWithConflict() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        classifier.changed().getMembers().remove(1);
        Method removedMethod = classifier.original().getMethods().get(1);

        EMFModelChange change = ChangeBuilder.createDeleteChange(removedMethod, classifier.changed());

        EObject expected = Utilities.clone(cuJML);

        userInteracting.showMessage(eq(UserInteractionType.MODAL), notNull(String.class));
        syncAbortedListener.synchronisationAborted(change);
        
        callSynchronizer(change);

        assertEqualsModel(expected, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(4, removedMethod);
    }
    
    @Test
    public void testDeleteField() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        classifier.changed().getMembers().get(5);
        Field removedField = classifier.original().getFields().get(1);

        EMFModelChange change = ChangeBuilder.createDeleteChange(removedField, classifier.changed());

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeleteField, cuJML);
        assertNumberOfCorrespondences(0, removedField);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testDeleteFieldWithConflict() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        classifier.changed().getMembers().get(4);
        Field removedField = classifier.original().getFields().get(0);

        EMFModelChange change = ChangeBuilder.createDeleteChange(removedField, classifier.changed());

        EObject expected = Utilities.clone(cuJML);

        userInteracting.showMessage(eq(UserInteractionType.MODAL), notNull(String.class));
        syncAbortedListener.synchronisationAborted(change);
        
        callSynchronizer(change);
        
        assertEqualsModel(expected, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(5, removedField);
    }
    
    @Test
    public void testReplaceModifier() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        Modifier newModifier = ModifiersFactory.eINSTANCE.createProtected();
        Modifier oldModifier = classifier.original().getModifiers().get(0);
        classifier.changed().getAnnotationsAndModifiers().remove(0);
        classifier.changed().getAnnotationsAndModifiers().add(0, newModifier);

        EMFModelChange change = ChangeBuilder.createReplaceChangeInList(classifier.original(), classifier.changed(), ModifiersPackage.eINSTANCE.getAnnotableAndModifiable_AnnotationsAndModifiers(), newModifier, oldModifier);
        
        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_ReplaceModifier, cuJML);
        assertNumberOfCorrespondences(1, newModifier);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testAddMethodWithConflict() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Method newMethod = helperCu.getClassifiers().get(2).getMethods().get(0);
        classifier.changed().getMembers().add(0, newMethod);

        EMFModelChange change = ChangeBuilder.createCreateChange(newMethod, classifier.original());

        EObject expected = Utilities.clone(cuJML);
        
        userInteracting.showMessage(eq(UserInteractionType.MODAL), notNull(String.class));
        syncAbortedListener.synchronisationAborted(change);
        
        callSynchronizer(change);

        assertEqualsModel(expected, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testAddFieldWithConflict() throws Exception {
        CloneContainer<ConcreteClassifier> classifier = createClones(cuJava.getClassifiers().get(0));
        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Field newField = helperCu.getClassifiers().get(2).getFields().get(0);
        classifier.changed().getMembers().add(0, newField);

        EMFModelChange change = ChangeBuilder.createCreateChange(newField, classifier.original());

        EObject expected = Utilities.clone(cuJML);
        
        userInteracting.showMessage(eq(UserInteractionType.MODAL), notNull(String.class));
        syncAbortedListener.synchronisationAborted(change);
        
        callSynchronizer(change);

        assertEqualsModel(expected, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
}
