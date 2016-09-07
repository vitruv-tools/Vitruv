package tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.jml;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import tools.vitruvius.domains.jml.language.jML.JMLFactory;
import tools.vitruvius.domains.jml.language.jML.JMLMemberModifier;
import tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration;
import tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration;
import tools.vitruvius.applications.jmljava.changesynchronizer.ChangeBuilder;
import tools.vitruvius.applications.jmljava.helper.Utilities;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import tools.vitruvius.framework.util.datatypes.Pair;
import tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase;
import tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase.CloneContainer;
import tools.vitruvius.applications.jmljava.tests.unittests.utils.ModelLoader.IResourceFiles;

public class JMLMemberDeclarationWithModifierTransformationsTest extends TransformationTestsBase {

    private static enum ResourceFiles implements IResourceFiles {
        JAVA("JMLMemberDeclarationWithModifierTransformationsTest.java.resource"),
        JML("JMLMemberDeclarationWithModifierTransformationsTest.jml.resource"),
        
        EXPECTED_RemoveHelperWithoutConflict("JMLMemberDeclarationWithModifierTransformationsTest_EXPECTED_RemoveHelperWithoutConflict.jml.resource"),
        EXPECTED_AddHelperWithoutConflict("JMLMemberDeclarationWithModifierTransformationsTest_EXPECTED_AddHelperWithoutConflict.jml.resource"),
        EXPECTED_AddPureWithoutConflict("JMLMemberDeclarationWithModifierTransformationsTest_EXPECTED_AddPureWithoutConflict.jml.resource"),
        EXPECTED_DeletePureWithoutConflict("JMLMemberDeclarationWithModifierTransformationsTest_EXPECTED_DeletePureWithoutConflict.jml.resource"),
        EXPECTED_AddPureTransitively("JMLMemberDeclarationWithModifierTransformationsTest_EXPECTED_AddPureTransitively.jml.resource");
        private String modelFileName;

        private ResourceFiles(String modelFileName) {
            this.modelFileName = modelFileName;
        }

        @Override
        public String getModelFileName() {
            return modelFileName;
        }
    }

    //private CompilationUnit cuJava;
    private tools.vitruvius.domains.jml.language.jML.CompilationUnit cuJML;

    @Override
    protected Pair<ModelInstance, ModelInstance> getModelInstances() throws Exception {
        ModelInstance miJava = loadModelInstance(ResourceFiles.JAVA);
        //cuJava = miJava.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        ModelInstance miJML = loadModelInstance(ResourceFiles.JML);
        cuJML = miJML
                .getUniqueRootEObjectIfCorrectlyTyped(tools.vitruvius.domains.jml.language.jML.CompilationUnit.class);
        return new Pair<ModelInstance, ModelInstance>(miJava, miJML);
    }
    
    @Test
    public void testRemoveHelperWithoutConflict() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // notNecessaryHelperMethodMarked
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(2), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);

        jmlMemberDeclWithModifier.changed().getJmlModifiers().remove(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(jmlMemberDeclWithModifier.original().getJmlModifiers().get(0), jmlMemberDeclWithModifier.changed());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_RemoveHelperWithoutConflict, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testRemoveHelperWithConflict() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // helperMethodMarked
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(1), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);

        jmlMemberDeclWithModifier.changed().getJmlModifiers().remove(0);
        EMFModelChange change = ChangeBuilder.createDeleteChange(jmlMemberDeclWithModifier.original().getJmlModifiers().get(0), jmlMemberDeclWithModifier.changed());
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(change);
        
        EObject expectedJML = Utilities.clone(cuJML);
        
        callSynchronizer(change);
        
        assertEqualsModel(expectedJML, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testAddHelperWithoutConflict() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // helperMethodNotMarked
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(3), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);

        JMLMemberModifier newModifier = JMLFactory.eINSTANCE.createJMLMemberModifier();
        newModifier.setModifier(JMLSpecMemberModifier.HELPER);
        jmlMemberDeclWithModifier.changed().getJmlModifiers().add(newModifier);
        EMFModelChange change = ChangeBuilder.createCreateChange(newModifier, jmlMemberDeclWithModifier.original());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_AddHelperWithoutConflict, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testAddPureWithConflictAssignment() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // nonPureMethodNotMarked
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(7), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);
        JMLMemberModifier newModifier = JMLFactory.eINSTANCE.createJMLMemberModifier();
        newModifier.setModifier(JMLSpecMemberModifier.PURE);
        jmlMemberDeclWithModifier.changed().getJmlModifiers().add(newModifier);
        
        EMFModelChange change = ChangeBuilder.createCreateChange(newModifier, jmlMemberDeclWithModifier.original());
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(change);
        
        EObject expectedJML = Utilities.clone(cuJML);
        callSynchronizer(change);
        
        assertEqualsModel(expectedJML, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }

    @Test
    public void testAddPureWithConflictCall() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // nonPureMethodNotMarked2
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(8), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);
        JMLMemberModifier newModifier = JMLFactory.eINSTANCE.createJMLMemberModifier();
        newModifier.setModifier(JMLSpecMemberModifier.PURE);
        jmlMemberDeclWithModifier.changed().getJmlModifiers().add(newModifier);
        
        EMFModelChange change = ChangeBuilder.createCreateChange(newModifier, jmlMemberDeclWithModifier.original());
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(change);
        
        EObject expectedJML = Utilities.clone(cuJML);
        callSynchronizer(change);
        
        assertEqualsModel(expectedJML, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testAddPureWithoutConflict() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // pureMethodNotMarked
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(6), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);
        JMLMemberModifier newModifier = JMLFactory.eINSTANCE.createJMLMemberModifier();
        newModifier.setModifier(JMLSpecMemberModifier.PURE);
        jmlMemberDeclWithModifier.changed().getJmlModifiers().add(newModifier);
        
        EMFModelChange change = ChangeBuilder.createCreateChange(newModifier, jmlMemberDeclWithModifier.original());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_AddPureWithoutConflict, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testRemovePureWithoutConflict() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // pureMethodMarked
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(5), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);
        jmlMemberDeclWithModifier.changed().getJmlModifiers().remove(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(jmlMemberDeclWithModifier.original().getJmlModifiers().get(0), jmlMemberDeclWithModifier.changed());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_DeletePureWithoutConflict, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testRemovePureWithConflictInSpec() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // pureCalledMethodMarked
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(4), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);
        jmlMemberDeclWithModifier.changed().getJmlModifiers().remove(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(jmlMemberDeclWithModifier.original().getJmlModifiers().get(0), jmlMemberDeclWithModifier.changed());
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(change);       
        EObject expectedJML = Utilities.clone(cuJML);
        
        callSynchronizer(change);
        
        assertEqualsModel(expectedJML, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }

    @Test
    public void testAddPureTransitively() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // pureMethodNotMarkedCalledInMethod
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(10), MemberDeclWithModifier.class);
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);
        JMLMemberModifier newModifier = JMLFactory.eINSTANCE.createJMLMemberModifier();
        newModifier.setModifier(JMLSpecMemberModifier.PURE);
        jmlMemberDeclWithModifier.changed().getJmlModifiers().add(newModifier);
        
        EMFModelChange change = ChangeBuilder.createCreateChange(jmlMemberDeclWithModifier.changed().getJmlModifiers().get(0), jmlMemberDeclWithModifier.original());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_AddPureTransitively, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testRemovePureWithConflictInSpecComplex() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        // pureMethodUsedComplex
        MemberDeclWithModifier jmlMemberDeclOriginal = Utilities.getFirstChildOfType(classDecl.getBodyDeclarations().get(14), MemberDeclWithModifier.class);
        
        System.out.println(((MemberDeclaration)jmlMemberDeclOriginal.getMemberdecl()).getMethod().getIdentifier());
        
        CloneContainer<MemberDeclWithModifier> jmlMemberDeclWithModifier = new CloneContainer<MemberDeclWithModifier>(jmlMemberDeclOriginal);
        jmlMemberDeclWithModifier.changed().getJmlModifiers().remove(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(jmlMemberDeclWithModifier.original().getJmlModifiers().get(0), jmlMemberDeclWithModifier.changed());
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(change);       
        EObject expectedJML = Utilities.clone(cuJML);
        
        callSynchronizer(change);
        
        assertEqualsModel(expectedJML, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
}
