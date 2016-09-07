package tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.custom;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.isA;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.LocalVariableStatement;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.statements.StatementsPackage;
import org.junit.Test;

import tools.vitruvius.applications.jmljava.helper.JaMoPPConcreteSyntax;
import tools.vitruvius.applications.jmljava.helper.Utilities;
import tools.vitruvius.applications.jmljava.synchronizers.custom.JavaMethodBodyChangedTransformation;
import tools.vitruvius.framework.change.description.CompositeChange;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import tools.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import tools.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import tools.vitruvius.framework.util.datatypes.Pair;
import tools.vitruvius.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase;
import tools.vitruvius.applications.jmljava.tests.unittests.utils.ModelLoader.IResourceFiles;

public class JavaMethodBodyChangesTransformationTest extends TransformationTestsBase {
    
    private static enum ResourceFiles implements IResourceFiles {
        JAVA("JavaMethodBodyChangesTransformationTest.java.resource"),
        JML("JavaMethodBodyChangesTransformationTest.jml.resource"),
        
        EXPECTED_AddNonPureStatementToNotUsedPureMethod("JavaMethodBodyChangesTransformationTest_EXPECTED_AddNonPureStatementToNotUsedPureMethod.jml.resource"),
        EXPECTED_AddNonPureStatementToPureMethodCalledByOtherPureMethodNotUsedInSpecification("JavaMethodBodyChangesTransformationTest_EXPECTED_AddNonPureStatementToPureMethodCalledByOtherPureMethodNotUsedInSpecification.jml.resource"),
        EXPECTED_RemoveAllNonPureStatementsFromNonPureMethod("JavaMethodBodyChangesTransformationTest_EXPECTED_RemoveAllNonPureStatementsFromNonPureMethod.jml.resource"),
        EXPECTED_RemoveOfAllNonPureStatementsAddsPureToCallingMethod("JavaMethodBodyChangesTransformationTest_EXPECTED_RemoveOfAllNonPureStatementsAddsPureToCallingMethod.jml.resource");

        
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
    
    private LocalVariableStatement convertToStatement(String stmt, EObject newParent) {
        return JaMoPPConcreteSyntax.convertFromConcreteSyntax(stmt, StatementsPackage.eINSTANCE.getLocalVariableStatement(), LocalVariableStatement.class, newParent.eResource().getURI());
    }
    
    private ExpressionStatement convertToMethodCallStatement(String stmt, EObject newParent) {
        return JaMoPPConcreteSyntax.convertFromConcreteSyntax(stmt, StatementsPackage.eINSTANCE.getExpressionStatement(), ExpressionStatement.class, newParent.eResource().getURI());
    }
    
    private CompositeChange createCompositeChange(CloneContainer<ClassMethod> clones) {
        return createCompositeChange(clones.original(), clones.changed());
    }
    
    private CompositeChange createCompositeChange(ClassMethod oldMethod, ClassMethod newMethod) {
        List<EMFModelChange> changes = new ArrayList<EMFModelChange>();
        
        for (Statement stmt : oldMethod.getStatements()) {
            DeleteNonRootEObjectInList<EObject> change = ContainmentFactory.eINSTANCE.createDeleteNonRootEObjectInList();
            change.setAffectedFeature(StatementsPackage.eINSTANCE.getStatementListContainer_Statements());
            change.setIndex(oldMethod.getStatements().indexOf(stmt));
            change.setNewAffectedEObject(newMethod);
            change.setOldAffectedEObject(oldMethod);
            change.setOldValue(stmt);
            changes.add(new EMFModelChange(change, VURI.getInstance(stmt.eResource())));
        }
        
        for (Statement stmt : newMethod.getStatements()) {
            CreateNonRootEObjectInList<EObject> change = ContainmentFactory.eINSTANCE.createCreateNonRootEObjectInList();
            change.setAffectedFeature(StatementsPackage.eINSTANCE.getStatementListContainer_Statements());
            change.setIndex(oldMethod.getStatements().indexOf(stmt));
            change.setNewAffectedEObject(newMethod);
            change.setOldAffectedEObject(oldMethod);
            change.setNewValue(stmt);
            changes.add(new EMFModelChange(change, VURI.getInstance(stmt.eResource())));
        }
        
        return new CompositeChange(changes.toArray(new EMFModelChange[0]));
    }
    
    @Test
    public void testAddNonPureStatementToNonPureMethod() throws Exception {
        // nonPureMethodNotMarked
        CloneContainer<ClassMethod> method = createClones((ClassMethod)cuJava.getClassifiers().get(0).getMethods().get(2));
        Statement newStatement = Utilities.clone(convertToStatement("Object a = nonPureMethodCalled();", method.changed()));
        method.changed().getStatements().add(0, newStatement);
        
        EObject expectedJMLCu = Utilities.clone(cuJML);
        
        CompositeChange change = createCompositeChange(method);

        callSynchronizer(change);

        assertEqualsModel(expectedJMLCu, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testAddNonPureStatementToNotUsedPureMethod() throws Exception {
        // pureMethodMarked
        CloneContainer<ClassMethod> method = createClonesForSerializedObject((ClassMethod)(cuJava.getClassifiers().get(0).getMethods().get(1)));
        Statement newStatement = convertToStatement("Object a = nonPureMethodCalled();", method.changed());
        method.changed().getStatements().add(0, newStatement);
        
        CompositeChange change = createCompositeChange(method);

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_AddNonPureStatementToNotUsedPureMethod, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // pure modifier is removed
    }
    
    @Test
    public void testAddNonPureStatementToPureMethodDirectlyUsedInSpecification() throws Exception {
        // pureMethodUsedInSpec
        CloneContainer<ClassMethod> method = createClonesForSerializedObject((ClassMethod)(cuJava.getClassifiers().get(0).getMethods().get(4)));
        Statement newStatement = convertToMethodCallStatement("nonPureMethodCalled();", method.changed());
        method.changed().getStatements().add(0, newStatement);       
        
        EObject expectedJMLCu = Utilities.clone(cuJML);
        
        CompositeChange change = createCompositeChange(method);
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(isA(JavaMethodBodyChangedTransformation.class));
        
        callSynchronizer(change);

        assertEqualsModel(expectedJMLCu, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // change is blocked
    }
    
    @Test
    public void testAddNonPureStatementToPureMethodCalledByOtherPureMethodNotUsedInSpecification() throws Exception {
        // pureCalledMethodMarked
        CloneContainer<ClassMethod> method = createClonesForSerializedObject((ClassMethod)(cuJava.getClassifiers().get(0).getMethods().get(0)));
        Statement newStatement = convertToStatement("Object a = nonPureMethodCalled();", method.changed());
        method.changed().getStatements().add(0, newStatement);

        CompositeChange change = createCompositeChange(method);

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_AddNonPureStatementToPureMethodCalledByOtherPureMethodNotUsedInSpecification, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // pure removed from both methods
    }
    
    @Test
    public void testAddNonPureStatementToPureMethodTransitivelyUsedInSpec() throws Exception {
        // pureMethodTransitivelyUsedInSpec
        CloneContainer<ClassMethod> method = createClonesForSerializedObject((ClassMethod)(cuJava.getClassifiers().get(0).getMethods().get(6)));
        Statement newStatement = convertToStatement("Object a = nonPureMethodCalled();", method.changed());
        method.changed().getStatements().add(0, newStatement);
        
        EObject expectedJMLCu = Utilities.clone(cuJML);
        
        CompositeChange change = createCompositeChange(method);
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(isA(JavaMethodBodyChangedTransformation.class));
        
        callSynchronizer(change);

        assertEqualsModel(expectedJMLCu, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // change is blocked
    }
    
    @Test
    public void testRemovePureStatementFromNonPureMethod() throws Exception {
        // nonPureMethodNotMarked
        CloneContainer<ClassMethod> method = createClones((ClassMethod)cuJava.getClassifiers().get(0).getMethods().get(2));
        method.changed().getStatements().remove(1);
        
        EObject expectedJMLCu = Utilities.clone(cuJML);
        
        CompositeChange change = createCompositeChange(method);

        callSynchronizer(change);

        assertEqualsModel(expectedJMLCu, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // models are not changed by the transformation
    }
    
    @Test
    public void testRemoveAllNonPureStatementsFromNonPureMethod() throws Exception {
        // nonPureMethodNotMarked2
        CloneContainer<ClassMethod> method = createClones((ClassMethod)cuJava.getClassifiers().get(0).getMethods().get(3));
        method.changed().getStatements().remove(0);
        
        CompositeChange change = createCompositeChange(method);

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_RemoveAllNonPureStatementsFromNonPureMethod, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // pure modifier is added
    }
    
    @Test
    public void testRemoveOfAllNonPureStatementsAddsPureToCallingMethod() throws Exception {
        // nonPureMethodCalled
        CloneContainer<ClassMethod> method = createClones((ClassMethod)cuJava.getClassifiers().get(0).getMethods().get(10));
        method.changed().getStatements().remove(0);
        
        CompositeChange change = createCompositeChange(method);

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_RemoveOfAllNonPureStatementsAddsPureToCallingMethod, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // pure modifier is added to method and calling method
    }
    
}
