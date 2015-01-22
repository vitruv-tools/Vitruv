package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.synchronizers.jml;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLModelElement;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLPackage;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSpecifiedElement;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclWithModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MethodDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.NormalClassDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.changesynchronizer.ChangeBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.Utilities;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.synchronizers.TransformationTestsBase;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;

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
