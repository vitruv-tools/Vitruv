package tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.jml;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Test;

import tools.vitruvius.domains.jml.language.jML.JMLModelElement;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier;
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration;
import tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration;
import tools.vitruvius.applications.jmljava.changesynchronizer.ChangeBuilder;
import tools.vitruvius.applications.jmljava.helper.Utilities;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import tools.vitruvius.framework.util.datatypes.Pair;
import tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase;
import tools.vitruvius.applications.jmljava.tests.unittests.utils.ModelLoader.IResourceFiles;

public class JMLMethodDeclarationTransformationsTest extends TransformationTestsBase {
    
    private static enum ResourceFiles implements IResourceFiles {
        JAVA("JMLMethodDeclarationTransformationsTest.java.resource"),
        JML("JMLMethodDeclarationTransformationsTest.jml.resource"),
        HELPER("Helper_Elements.java.resource"),
        
        JML_EXPECTED_RenameModelMethod("JMLMethodDeclarationTransformationsTest_EXPECTED_RenameModelMethod.jml.resource");

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
    public void testRenameMethodWithRestrictionConflict() throws Exception {
        MemberDeclWithModifier method = ((JMLSpecifiedElement)Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class).getBodyDeclarations().get(3)).getElement();
        CloneContainer<MethodDeclaration> methodDeclaration = createClones(Utilities.getFirstChildOfType(method, MethodDeclaration.class));
        methodDeclaration.changed().setIdentifier("calledMethod2");
        
        EMFModelChange change = ChangeBuilder.createUpdateChange(methodDeclaration.original(), methodDeclaration.changed(), JMLPackage.eINSTANCE.getIdentifierHaving_Identifier());
        
        EObject expectedJML = Utilities.clone(cuJML);
        EObject expectedJava = Utilities.clone(cuJava);

        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(change);
        
        callSynchronizer(change);
        
        //TODO don't know if these checks are enough
        assertEqualsModel(expectedJML, cuJML);
        assertEqualsModel(expectedJava, cuJava);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testRenameModelMethod() throws Exception {
        MemberDeclWithModifier method = Utilities.getFirstChildOfType(cuJML, JMLModelElement.class).getElement();
        CloneContainer<MethodDeclaration> methodDeclaration = createClones(Utilities.getFirstChildOfType(method, MethodDeclaration.class));
        methodDeclaration.changed().setIdentifier("modelMethod2");
        
        EObject expectedJava = Utilities.clone(cuJava);
        
        EMFModelChange change = ChangeBuilder.createUpdateChange(methodDeclaration.original(), methodDeclaration.changed(), JMLPackage.eINSTANCE.getIdentifierHaving_Identifier());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.JML_EXPECTED_RenameModelMethod, cuJML);
        assertEqualsModel(expectedJava, cuJava);
        assertNumberOfRealUpdateCalls(1);
    }
    
    @Test
    public void testRenameModelMethodWithJavaConflict() throws Exception {
        MemberDeclWithModifier method = Utilities.getFirstChildOfType(cuJML, JMLModelElement.class).getElement();
        CloneContainer<MethodDeclaration> methodDeclaration = createClones(Utilities.getFirstChildOfType(method, MethodDeclaration.class));
        methodDeclaration.changed().setIdentifier("dummyMethod");
        
        EObject expectedJava = Utilities.clone(cuJava);
        EObject expectedJML = Utilities.clone(cuJML);
        
        EMFModelChange change = ChangeBuilder.createUpdateChange(methodDeclaration.original(), methodDeclaration.changed(), JMLPackage.eINSTANCE.getIdentifierHaving_Identifier());
        
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(change);
        
        callSynchronizer(change);
        
        assertEqualsModel(expectedJML, cuJML);
        assertEqualsModel(expectedJava, cuJava);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testRenameModelMethodWithJMLConflict() throws Exception {
        MemberDeclWithModifier method = Utilities.getFirstChildOfType(cuJML, JMLModelElement.class).getElement();
        CloneContainer<MethodDeclaration> methodDeclaration = createClones(Utilities.getFirstChildOfType(method, MethodDeclaration.class));
        methodDeclaration.changed().setIdentifier("otherModelMethod");
        
        EObject expectedJava = Utilities.clone(cuJava);
        EObject expectedJML = Utilities.clone(cuJML);
        
        EMFModelChange change = ChangeBuilder.createUpdateChange(methodDeclaration.original(), methodDeclaration.changed(), JMLPackage.eINSTANCE.getIdentifierHaving_Identifier());
        
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(change);
        
        callSynchronizer(change);
        
        assertEqualsModel(expectedJML, cuJML);
        assertEqualsModel(expectedJava, cuJava);
        assertNumberOfRealUpdateCalls(0);
    }
    
}
