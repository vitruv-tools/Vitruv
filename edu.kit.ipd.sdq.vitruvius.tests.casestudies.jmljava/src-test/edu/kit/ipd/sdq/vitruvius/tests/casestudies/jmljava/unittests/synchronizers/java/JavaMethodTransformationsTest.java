package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.synchronizers.java;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.notNull;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.CommonsPackage;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.ModifiersPackage;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypesPackage;
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

public class JavaMethodTransformationsTest extends TransformationTestsBase {

    private static enum ResourceFiles implements IResourceFiles {
        JAVA("JavaMethodTransformationsTest.java.resource"),
        JML("JavaMethodTransformationsTest.jml.resource"),
        HELPER("Helper_Elements.java.resource"),
        
        EXPECTED_METHOD_ADDPARAM("JavaMethodTransformationsTest_EXPECTED_AddParameter.jml.resource"),
        EXPECTED_METHOD_ADDEXCEPTION("JavaMethodTransformationsTest_EXPECTED_AddException.jml.resource"),
        EXPECTED_METHOD_ADDMODIFIER("JavaMethodTransformationsTest_EXPECTED_AddModifier.jml.resource"),
        EXPECTED_METHOD_RENAMEMETHOD("JavaMethodTransformationsTest_EXPECTED_RenameMethod.jml.resource"),
        EXPECTED_METHOD_RENAMEMETHODWITHSPECS("JavaMethodTransformationsTest_EXPECTED_RenameMethodWithSpecsInvolved.jml.resource"),
        EXPECTED_METHOD_CHANGERETURNTYPE("JavaMethodTransformationsTest_EXPECTED_ChangeReturnType.jml.resource"),
        EXPECTED_METHOD_ReplaceModifier("JavaMethodTransformationsTest_EXPECTED_ReplaceModifier.jml.resource"),
        EXPECTED_METHOD_DeleteParam("JavaMethodTransformationsTest_EXPECTED_DeleteParameter.jml.resource"),
        EXPECTED_METHOD_DeleteException("JavaMethodTransformationsTest_EXPECTED_DeleteException.jml.resource"),
        EXPECTED_METHOD_DeleteModifier("JavaMethodTransformationsTest_EXPECTED_DeleteModifier.jml.resource");

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
    public void testCreateParameter() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        Parameter newParameter = Utilities.clone(method.changed().getParameters().get(0));
        newParameter.setName("newParam");
        method.changed().getParameters().add(0, newParameter);
        
        EMFModelChange change = ChangeBuilder.createCreateChange(newParameter, method.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_ADDPARAM, cuJML);
        assertNumberOfRealUpdateCalls(2);
        assertNumberOfCorrespondences(1, newParameter);
    }

    @Test
    public void testRenameMethod() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        method.changed().setName("methodNameChanged");
        
        EMFModelChange change = ChangeBuilder.createUpdateChange(method.original(), method.changed(), CommonsPackage.eINSTANCE.getNamedElement_Name());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_RENAMEMETHOD, cuJML);
        assertNumberOfRealUpdateCalls(2);
    }

    @Test
    public void testRenameMethodWithSpecificationsInvolved() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(1));
        method.changed().setName("methodNameChanged");
        
        EMFModelChange change = ChangeBuilder.createUpdateChange(method.original(), method.changed(), CommonsPackage.eINSTANCE.getNamedElement_Name());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_RENAMEMETHODWITHSPECS, cuJML);
        assertNumberOfRealUpdateCalls(2);
    }

    @Test
    public void testRenameMethodWithJMLNameConflict() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        method.changed().setName("modelMethod");
                
        EMFModelChange change = ChangeBuilder.createUpdateChange(method.original(), method.changed(), CommonsPackage.eINSTANCE.getNamedElement_Name());

        EObject expected = Utilities.clone(cuJML);
        userInteracting.showMessage(eq(UserInteractionType.MODAL), notNull(String.class));
        syncAbortedListener.synchronisationAborted(change);

        callSynchronizer(change);
        
        assertEqualsModel(expected, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }

    @Test
    public void testChangeReturnType() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        method.changed().setTypeReference(Utilities.clone(method.original().getParameters().get(0).getTypeReference()));
        
        EMFModelChange change = ChangeBuilder.createUpdateChange(method.original(), method.changed(), TypesPackage.eINSTANCE.getTypedElement_TypeReference());
        
        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_CHANGERETURNTYPE, cuJML);
        assertNumberOfRealUpdateCalls(2);
    }
    
    @Test
    public void testCreateException() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        
        CompilationUnit helperCu = loadModelInstance(ResourceFiles.HELPER).getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        NamespaceClassifierReference newException = helperCu.getClassifiers().get(0).getMethods().get(0).getExceptions().get(0);

        method.changed().getExceptions().add(0, newException);
        
        EMFModelChange change = ChangeBuilder.createCreateChange(newException, method.original());

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_ADDEXCEPTION, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(1, newException);
    }
    
    @Test
    public void testCreateModifier() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        Modifier newModifier = ModifiersFactory.eINSTANCE.createFinal();
        method.changed().getAnnotationsAndModifiers().add(newModifier);

        EMFModelChange change = ChangeBuilder.createCreateChange(newModifier, method.original());
        
        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_ADDMODIFIER, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(1, newModifier);
    }
    
    @Test
    public void testDeleteParameter() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        method.changed().getParameters().remove(0);

        EMFModelChange change = ChangeBuilder.createDeleteChange(method.original().getParameters().get(0), method.changed());
        
        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_DeleteParam, cuJML);
        assertNumberOfCorrespondences(0, method.original().getParameters().get(0));
        assertNumberOfRealUpdateCalls(2);
    }
    
    @Test
    public void testDeleteParameterWithConflict() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(2));
        method.changed().getParameters().remove(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(method.original().getParameters().get(0), method.changed());
        
        EObject expected = Utilities.clone(cuJML);
        
        userInteracting.showMessage(eq(UserInteractionType.MODAL), notNull(String.class));
        syncAbortedListener.synchronisationAborted(change);
        
        callSynchronizer(change);

        assertEqualsModel(expected, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(1, method.original().getParameters().get(0));
    }
    
    @Test
    public void testDeleteException() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        method.changed().getExceptions().remove(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(method.original().getExceptions().get(0), method.changed());
        
        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_DeleteException, cuJML);
        assertNumberOfRealUpdateCalls(0);
        assertNumberOfCorrespondences(0, method.original().getExceptions().get(0));
    }
    
    @Test
    public void testDeleteModifier() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        method.changed().getAnnotationsAndModifiers().remove(0);
        
        EMFModelChange change = ChangeBuilder.createDeleteChange(method.original().getAnnotationsAndModifiers().get(0), method.changed());
        
        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_DeleteModifier, cuJML);
        assertNumberOfCorrespondences(0, method.original().getModifiers().get(0));
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testReplaceModifier() throws Exception {
        CloneContainer<Method> method = createClones(cuJava.getClassifiers().get(0).getMethods().get(0));
        Modifier newModifier = ModifiersFactory.eINSTANCE.createProtected();
        method.changed().getAnnotationsAndModifiers().add(newModifier);

        EMFModelChange change = ChangeBuilder.createReplaceChangeInList(method.original(), method.changed(), ModifiersPackage.eINSTANCE.getAnnotableAndModifiable_AnnotationsAndModifiers(), newModifier, method.original().getAnnotationsAndModifiers().get(0));
        
        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_METHOD_ReplaceModifier, cuJML);
        assertNumberOfCorrespondences(1, newModifier);
        assertNumberOfRealUpdateCalls(0);
    }

}
