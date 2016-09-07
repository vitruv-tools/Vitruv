package tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.java;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.notNull;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.imports.Import;
import org.junit.Test;

import tools.vitruvius.applications.jmljava.changesynchronizer.ChangeBuilder;
import tools.vitruvius.applications.jmljava.helper.Utilities;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import tools.vitruvius.framework.util.datatypes.Pair;
import tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase;
import tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase.CloneContainer;
import tools.vitruvius.applications.jmljava.tests.unittests.utils.ModelLoader.IResourceFiles;

public class JavaCompilationUnitTransformationsTest extends TransformationTestsBase {
    
    private static enum ResourceFiles implements IResourceFiles {
        JAVA("JavaCompilationUnitTransformationsTest.java.resource"),
        JML("JavaCompilationUnitTransformationsTest.jml.resource"),
        HELPER("Helper_Elements.java.resource"),
        
        EXPECTED_AddStaticClassifierImport("JavaCompilationUnitTransformationsTest_EXPECTED_AddStaticClassifierImport.jml.resource"),
        EXPECTED_AddStaticMemberImport("JavaCompilationUnitTransformationsTest_EXPECTED_AddStaticMemberImport.jml.resource"),
        EXPECTED_AddPackageImport("JavaCompilationUnitTransformationsTest_EXPECTED_AddPackageImport.jml.resource"),
        EXPECTED_AddClassifierImport("JavaCompilationUnitTransformationsTest_EXPECTED_AddClassifierImport.jml.resource"),
        EXPECTED_DeleteStaticClassifierImport("JavaCompilationUnitTransformationsTest_EXPECTED_DeleteStaticClassifierImport.jml.resource"),
        EXPECTED_DeleteStaticMemberImport("JavaCompilationUnitTransformationsTest_EXPECTED_DeleteStaticMemberImport.jml.resource"),
        EXPECTED_DeletePackageImport("JavaCompilationUnitTransformationsTest_EXPECTED_DeletePackageImport.jml.resource"),
        EXPECTED_DeleteClassifierImport("JavaCompilationUnitTransformationsTest_EXPECTED_DeleteClassifierImport.jml.resource"),
        EXPECTED_AddClassifier("JavaCompilationUnitTransformationsTest_EXPECTED_AddClassifier.jml.resource"),
        EXPECTED_DeleteClassifier("JavaCompilationUnitTransformationsTest_EXPECTED_DeleteClassifier.jml.resource");

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
    private tools.vitruvius.domains.jml.language.jML.CompilationUnit cuJML;

    @Override
    protected Pair<ModelInstance, ModelInstance> getModelInstances() throws Exception {
        ModelInstance miJava = loadModelInstance(ResourceFiles.JAVA);
        cuJava = miJava.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        ModelInstance miJML = loadModelInstance(ResourceFiles.JML);
        cuJML = miJML
                .getUniqueRootEObjectIfCorrectlyTyped(tools.vitruvius.domains.jml.language.jML.CompilationUnit.class);
        return new Pair<ModelInstance, ModelInstance>(miJava, miJML);
    }
    
    @Test
    public void testCreateStaticMemberImport() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);
        
        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Import newImport = helperCu.getImports().get(0);
        cu.changed().getImports().add(0, newImport);

        EMFModelChange change = ChangeBuilder.createCreateChange(newImport, cu.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_AddStaticMemberImport, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(1, newImport);
    }
    
    @Test
    public void testCreateStaticClassifierImport() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);
        
        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Import newImport = helperCu.getImports().get(1);
        cu.changed().getImports().add(0, newImport);

        EMFModelChange change = ChangeBuilder.createCreateChange(newImport, cu.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_AddStaticClassifierImport, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(1, newImport);
    }
    
    @Test
    public void testCreatePackageImport() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);

        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Import newImport = helperCu.getImports().get(2);
        cu.changed().getImports().add(0, newImport);

        EMFModelChange change = ChangeBuilder.createCreateChange(newImport, cu.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_AddPackageImport, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(1, newImport);
    }
    
    @Test
    public void testCreateClassifierImport() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);

        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Import newImport = helperCu.getImports().get(3);
        cu.changed().getImports().add(0, newImport);

        EMFModelChange change = ChangeBuilder.createCreateChange(newImport, cu.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_AddClassifierImport, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(1, newImport);
    }
    
    @Test
    public void testDeleteStaticMemberImport() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);
        cu.changed().getImports().remove(1);
        Import deletedImport = cu.original().getImports().get(1);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(deletedImport, cu.changed());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeleteStaticMemberImport, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(0, deletedImport);
    }
    
    @Test
    public void testDeleteStaticClassifierImport() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);
        cu.changed().getImports().remove(0);
        Import deletedImport = cu.original().getImports().get(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(deletedImport, cu.changed());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeleteStaticClassifierImport, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(0, deletedImport);
    }
    
    @Test
    public void testDeletePackageImport() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);
        cu.changed().getImports().remove(2);
        Import deletedImport = cu.original().getImports().get(2);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(deletedImport, cu.changed());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeletePackageImport, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(0, deletedImport);
    }
    
    @Test
    public void testDeleteClassifierImport() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);
        cu.changed().getImports().remove(3);
        Import deletedImport = cu.original().getImports().get(3);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(deletedImport, cu.changed());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeleteClassifierImport, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(0, deletedImport);
    }
    
    @Test
    public void testCreateClassifier() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);

        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        ConcreteClassifier newClassifier = helperCu.getClassifiers().get(1);
        
        cu.changed().getClassifiers().add(1, newClassifier);
        
        EMFModelChange change = ChangeBuilder.createCreateChange(newClassifier, cu.original());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_AddClassifier, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(2, newClassifier);
        // other correspondences could be checked here, but for now this must be enough
    }
    
    @Test
    public void testDeleteClassifier() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);

        ConcreteClassifier deletedClassifier = cu.original().getClassifiers().get(1);
        cu.changed().getClassifiers().remove(1);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(deletedClassifier, cu.changed());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeleteClassifier, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(0, deletedClassifier);
    }
    
    @Test
    public void testCreateClassifierWithConflict() throws Exception {
        CloneContainer<CompilationUnit> cu = createClones(cuJava);

        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        ConcreteClassifier newClassifier = helperCu.getClassifiers().get(0);
        
        cu.changed().getClassifiers().add(1, newClassifier);
        
        EMFModelChange change = ChangeBuilder.createCreateChange(newClassifier, cu.original());
        
        EObject expected = Utilities.clone(cuJML);
        userInteracting.showMessage(eq(UserInteractionType.MODAL), notNull(String.class));
        syncAbortedListener.synchronisationAborted(change);

        callSynchronizer(change);
        
        assertEqualsModel(expected, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
}
