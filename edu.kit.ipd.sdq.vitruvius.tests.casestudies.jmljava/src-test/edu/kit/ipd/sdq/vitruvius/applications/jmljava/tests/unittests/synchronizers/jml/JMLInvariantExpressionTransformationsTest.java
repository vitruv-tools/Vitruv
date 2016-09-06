package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.unittests.synchronizers.jml;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Expression;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLInvariantExpression;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecifiedElement;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.NormalClassDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.ConcreteSyntaxHelper;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.changesynchronizer.ChangeBuilder;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.Utilities;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase.CloneContainer;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.unittests.utils.ModelLoader.IResourceFiles;

public class JMLInvariantExpressionTransformationsTest extends TransformationTestsBase {

    private static enum ResourceFiles implements IResourceFiles {
        JAVA("JMLInvariantExpressionTransformationsTest.java.resource"),
        JML("JMLInvariantExpressionTransformationsTest.jml.resource"),

        EXPECTED_ReplaceExpressionRemoveHelper("JMLInvariantExpressionTransformationsTest_EXPECTED_ReplaceExpressionRemoveHelper.jml.resource"),
        EXPECTED_ReplaceExpressionAddHelper("JMLInvariantExpressionTransformationsTest_EXPECTED_ReplaceExpressionAddHelper.jml.resource"),
        EXPECTED_ReplaceExpressionAddRemoveHelper("JMLInvariantExpressionTransformationsTest_EXPECTED_ReplaceExpressionAddRemoveHelper.jml.resource"),
        EXPECTED_ReplaceExpressionNoModifierChange("JMLInvariantExpressionTransformationsTest_EXPECTED_ReplaceExpressionNoModifierChange.jml.resource");

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
    private edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit cuJML;

    @Override
    protected Pair<ModelInstance, ModelInstance> getModelInstances() throws Exception {
        ModelInstance miJava = loadModelInstance(ResourceFiles.JAVA);
        //cuJava = miJava.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        ModelInstance miJML = loadModelInstance(ResourceFiles.JML);
        cuJML = miJML
                .getUniqueRootEObjectIfCorrectlyTyped(edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit.class);
        return new Pair<ModelInstance, ModelInstance>(miJava, miJML);
    }

    @Test
    public void testReplaceExpressionRemoveHelper() throws Exception {
        CloneContainer<JMLInvariantExpression> invariant = new CloneContainer<JMLInvariantExpression>(
                Utilities.getFirstChildOfType(cuJML, JMLInvariantExpression.class));

        Expression newExpression = ConcreteSyntaxHelper.convertFromConcreteSyntax("calledMethod2()", Expression.class);
        invariant.changed().setExpr(newExpression);

        EMFModelChange change = ChangeBuilder.createUpdateChange(invariant.original(), invariant.changed(),
                JMLPackage.eINSTANCE.getJMLExpressionHaving_Expr());

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_ReplaceExpressionRemoveHelper, cuJML);
        assertNumberOfRealUpdateCalls(1);
    }
    
    @Test
    public void testReplaceExpressionAddHelper() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        JMLSpecifiedElement jmlSpecifiedElement = (JMLSpecifiedElement)classDecl.getBodyDeclarations().get(1);
        CloneContainer<JMLInvariantExpression> invariant = new CloneContainer<JMLInvariantExpression>((JMLInvariantExpression)jmlSpecifiedElement.getJmlTypeSpecifications().get(0).getSpec());

        Expression newExpression = ConcreteSyntaxHelper.convertFromConcreteSyntax("calledMethod3()", Expression.class);
        invariant.changed().setExpr(newExpression);

        EMFModelChange change = ChangeBuilder.createUpdateChange(invariant.original(), invariant.changed(),
                JMLPackage.eINSTANCE.getJMLExpressionHaving_Expr());

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_ReplaceExpressionAddHelper, cuJML);
        assertNumberOfRealUpdateCalls(1);
    }
    
    @Test
    public void testReplaceExpressionAddRemoveHelper() throws Exception {
        CloneContainer<JMLInvariantExpression> invariant = new CloneContainer<JMLInvariantExpression>(
                Utilities.getFirstChildOfType(cuJML, JMLInvariantExpression.class));

        Expression newExpression = ConcreteSyntaxHelper.convertFromConcreteSyntax("calledMethod3()", Expression.class);
        invariant.changed().setExpr(newExpression);

        EMFModelChange change = ChangeBuilder.createUpdateChange(invariant.original(), invariant.changed(),
                JMLPackage.eINSTANCE.getJMLExpressionHaving_Expr());

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_ReplaceExpressionAddRemoveHelper, cuJML);
        assertNumberOfRealUpdateCalls(1);
    }
    
    @Test
    public void testReplaceExpressionNoModifierChange() throws Exception {
        NormalClassDeclaration classDecl = Utilities.getFirstChildOfType(cuJML, NormalClassDeclaration.class);
        JMLSpecifiedElement jmlSpecifiedElement = (JMLSpecifiedElement)classDecl.getBodyDeclarations().get(2);
        CloneContainer<JMLInvariantExpression> invariant = new CloneContainer<JMLInvariantExpression>((JMLInvariantExpression)jmlSpecifiedElement.getJmlTypeSpecifications().get(0).getSpec());

        Expression newExpression = ConcreteSyntaxHelper.convertFromConcreteSyntax("3 == 3 && calledMethod()", Expression.class);
        invariant.changed().setExpr(newExpression);

        EMFModelChange change = ChangeBuilder.createUpdateChange(invariant.original(), invariant.changed(),
                JMLPackage.eINSTANCE.getJMLExpressionHaving_Expr());

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_ReplaceExpressionNoModifierChange, cuJML);
        assertNumberOfRealUpdateCalls(1);
    }
    
}
