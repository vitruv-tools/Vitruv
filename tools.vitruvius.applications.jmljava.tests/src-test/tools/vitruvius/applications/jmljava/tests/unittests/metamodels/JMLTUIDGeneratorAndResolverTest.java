package tools.vitruvius.applications.jmljava.tests.unittests.metamodels;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import tools.vitruvius.domains.jml.language.jML.ClassDeclaration;
import tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets;
import tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier;
import tools.vitruvius.domains.jml.language.jML.CompilationUnit;
import tools.vitruvius.domains.jml.language.jML.DeclaredException;
import tools.vitruvius.domains.jml.language.jML.FieldDeclaration;
import tools.vitruvius.domains.jml.language.jML.FormalParameterDecl;
import tools.vitruvius.domains.jml.language.jML.ImportDeclaration;
import tools.vitruvius.domains.jml.language.jML.InterfaceDeclaration;
import tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec;
import tools.vitruvius.domains.jml.language.jML.JMLSinglelineSpec;
import tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement;
import tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier;
import tools.vitruvius.domains.jml.language.jML.JMLTypeExpression;
import tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierSpec;
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration;
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration;
import tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration;
import tools.vitruvius.domains.jml.language.jML.PackageDeclaration;
import tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets;
import tools.vitruvius.domains.jml.language.jML.RegularModifier;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.domains.jml.language.jML.VariableDeclarator;
import tools.vitruvius.applications.jmljava.metamodels.JMLTUIDCalculatorAndResolver;
import tools.vitruvius.applications.jmljava.tests.unittests.utils.Initializer;
import tools.vitruvius.applications.jmljava.tests.unittests.utils.ModelLoader.IResourceFiles;

public class JMLTUIDGeneratorAndResolverTest extends
        TUIDCalculatorAndResolverTestBase<JMLTUIDCalculatorAndResolver, CompilationUnit> {

    private enum ResourceFiles implements IResourceFiles {
        DEFAULT("JML_CU.jml.resource"), TopLevelInterface("JML_CU_TopLevelInterface.jml.resource"), PackageLess(
                "JML_CU_Packageless.jml.resource"), SpecifiedElements("JML_CU_Specifications.jml.resource");

        private final String modelFileName;

        ResourceFiles(String modelFileName) {
            this.modelFileName = modelFileName;
        }

        @Override
        public String getModelFileName() {
            return modelFileName;
        }
    }

    @BeforeClass
    public static void init() {
        Initializer.initLogging();
        Initializer.initJML();
    }

    @Override
    protected JMLTUIDCalculatorAndResolver getNewTUIDGenerator() {
        return new JMLTUIDCalculatorAndResolver();
    }

    @Override
    protected IResourceFiles getDefaultResourceFile() {
        return ResourceFiles.DEFAULT;
    }

    @Test
    public void testCompilationUnit() {
        testCalculationAndResolution(rootObject, CompilationUnit.class);
    }

    @Test
    public void testTopLevelTypeDeclaration() {
        testCalculationAndResolution(rootObject.getTypedeclaration().get(0), ClassifierDeclarationWithModifier.class);
    }

    @Test
    public void testTopLevelClass() {
        testCalculationAndResolution(rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration(),
                ClassDeclaration.class);
    }

    @Test
    public void testTopLevelInterface() throws IOException {
        rootObject = loadResourceModel(ResourceFiles.TopLevelInterface);
        testCalculationAndResolution(rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration(),
                InterfaceDeclaration.class);
    }

    @Test
    public void testCompilationUnitPackageless() throws IOException {
        rootObject = loadResourceModel(ResourceFiles.PackageLess);
        testCalculationAndResolution(rootObject, CompilationUnit.class);
    }

    @Test
    public void testPackageDeclaration() {
        testCalculationAndResolution(rootObject.getPackagedeclaration(), PackageDeclaration.class);
    }

    @Test
    public void testImportDeclaration() {
        testCalculationAndResolution(rootObject.getImportdeclaration().get(0), ImportDeclaration.class);
    }

    @Test
    public void testClassifierModifier() {
        testCalculationAndResolution(rootObject.getTypedeclaration().get(0).getModifiers().get(0),
                RegularModifier.class);
    }

    
    
    
    @Test
    public void testJMLSinglelineSpecForField() {
        JMLSinglelineSpec decl = (JMLSinglelineSpec)((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(0);
        testJMLSinglelineSpecField(decl);
        testCalculationAndResolution(decl, JMLSinglelineSpec.class);
    }
    
    @Test
    public void testMemberDeclarationForField() {
        MemberDeclaration decl = (MemberDeclaration)((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(0)).getElement().getMemberdecl();
        assertNotNull(decl.getField());
        testCalculationAndResolution(decl, MemberDeclaration.class);
    }
    
    @Test
    public void testFieldDeclaration() {
        FieldDeclaration decl = ((MemberDeclaration)((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(0)).getElement().getMemberdecl()).getField();      
        testCalculationAndResolution(decl, FieldDeclaration.class);
    }
    
    @Test
    public void testVariableDeclaratorForField() {
        VariableDeclarator decl = ((MemberDeclaration)((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(0)).getElement().getMemberdecl()).getField().getVariabledeclarator().get(0);      
        testCalculationAndResolution(decl, VariableDeclarator.class);
    }
    
    @Test
    public void testMemberDeclWithModifierForFieldWithMultipleNames() {
        JMLSinglelineSpec decl = ((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(1));
        testJMLSinglelineSpecField(decl);
        testCalculationAndResolution(decl, JMLSinglelineSpec.class);
    }

    
    
    @Test
    public void testMethodWithoutParameter() {
        JMLSinglelineSpec decl = ((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(2));
        testJMLSinglelineSpecMethod(decl);
    }

    @Test
    public void testMethodWithParam() {
        JMLSinglelineSpec decl = ((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(3));
        testJMLSinglelineSpecMethod(decl);
    }

    @Test
    public void testVoidMethodWithoutParameter() {
        JMLSinglelineSpec decl = ((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(4));
        testJMLSinglelineSpecMethod(decl);
    }
    
    @Test
    public void testVoidMethodWithParameter() {
        JMLSinglelineSpec decl = ((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(5));
        testJMLSinglelineSpecMethod(decl);
    }
    
    @Test
    public void testVoidMethodWithVarargsParameter() {
        JMLSinglelineSpec decl = ((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(6));
        testJMLSinglelineSpecMethod(decl);
    }
    
    @Test
    public void testMethodDeclaration() {
        MethodDeclaration decl = ((MemberDeclaration)((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(6)).getElement().getMemberdecl()).getMethod();      
        testCalculationAndResolution(decl, MethodDeclaration.class);
    }
    
    @Test
    public void testMethodParameter() {
        FormalParameterDecl decl = ((MemberDeclaration)((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(6)).getElement().getMemberdecl()).getMethod().getParameters().get(0);
        testCalculationAndResolution(decl, FormalParameterDecl.class);
    }
    
    @Test
    public void testMethodParameterPrimitiveType() {
        Type paramType = ((MemberDeclaration)((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(6)).getElement().getMemberdecl()).getMethod().getParameters().get(0).getType();
        testCalculationAndResolution(paramType, PrimitiveTypeWithBrackets.class);
    }
    
    @Test
    public void testDeclaredExceptionForMethod() {
        DeclaredException ex = ((MemberDeclaration)((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(4)).getElement().getMemberdecl()).getMethod().getExceptions().get(0);
        testCalculationAndResolution(ex, DeclaredException.class);
    }

    @Test
    public void testMethodReturnType() {
        Type type = ((MemberDeclaration)((JMLSinglelineSpec) ((NormalClassDeclaration) rootObject
                .getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(2)).getElement().getMemberdecl()).getType();    
        testCalculationAndResolution(type, ClassOrInterfaceTypeWithBrackets.class);
    }
    
    @Test
    public void testInvalidTUID() {
        assertNull(tuidGenerator.resolveEObjectFromRootAndFullTUID(rootObject, "abc"));
    }

    @Test
    public void testNotExistingTUID() {
        String tuid = tuidGenerator.calculateTUIDFromEObject(rootObject.getTypedeclaration().get(0)) + "a";
        EObject result = tuidGenerator.resolveEObjectFromRootAndFullTUID(rootObject, tuid);
        assertNull(result);
    }

    @Test
    public void testInvalidEObject() {
        String tuid = tuidGenerator.calculateTUIDFromEObject(EcoreFactory.eINSTANCE.createEObject());
        assertEquals(tuidGenerator.getTUIDPrefixAndSeparator(), tuid);
    }
    
    @Test
    public void testJMLMultilineSpecWithSpecifiedModelElement() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        JMLMultilineSpec spec = ((JMLMultilineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(2));
        testCalculationAndResolution(spec, JMLMultilineSpec.class);
    }
    
    @Test
    public void testJMLMultilineSpecWithModelElement() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        JMLMultilineSpec spec = ((JMLMultilineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(3));
        testCalculationAndResolution(spec, JMLMultilineSpec.class);
    }
    
    @Test
    public void testJMLMultilineSpecWithSpecifiedElement() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        JMLMultilineSpec spec = ((JMLMultilineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(5));
        testCalculationAndResolution(spec, JMLMultilineSpec.class);
    }
    
    @Test
    public void testMemberDeclarationInsideJMLSpecification() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        MemberDeclWithModifierRegular spec = ((JMLSinglelineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(4)).getElement();
        testCalculationAndResolution(spec, MemberDeclWithModifierRegular.class);
    }
    
    @Test
    public void testModelElementWithModifier() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        JMLSpecificationOnlyElementWithModifier spec = ((JMLMultilineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(2)).getModelElement();
        testCalculationAndResolution(spec, JMLSpecificationOnlyElementWithModifier.class);
    }
    
    @Test
    public void testModelElement() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        JMLSpecificationOnlyElement spec = ((JMLMultilineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(2)).getModelElement().getElement();
        testCalculationAndResolution(spec, JMLSpecificationOnlyElement.class);
    }
    
    @Test
    public void testModelElementMemberDeclaration() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        MemberDeclWithModifierSpec spec = ((JMLMultilineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(2)).getModelElement().getElement().getElement();
        testCalculationAndResolution(spec, MemberDeclWithModifierSpec.class);
    }
    
    @Test
    public void testJMLSinglelineSpecWithSpecifiedElement() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        JMLSinglelineSpec spec = ((JMLSinglelineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(4));
        testCalculationAndResolution(spec, JMLSinglelineSpec.class);
    }
    
    @Test
    public void testJMLTypeExpressionWithModifier() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        JMLTypeExpressionWithModifier expr = ((JMLSinglelineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(0)).getJmlTypeSpecifications().get(0);
        testCalculationAndResolution(expr, JMLTypeExpressionWithModifier.class);
    }
    
    @Test
    public void testJMLTypeExpression() throws Exception {
        rootObject = loadResourceModel(ResourceFiles.SpecifiedElements);
        JMLTypeExpression expr = ((JMLSinglelineSpec)((NormalClassDeclaration)rootObject.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getBodyDeclarations().get(0)).getJmlTypeSpecifications().get(0).getSpec();
        testCalculationAndResolution(expr, JMLTypeExpression.class);
    }

    private void testJMLSinglelineSpecMethod(JMLSinglelineSpec decl) {
        assertThat(decl.getElement().getMemberdecl(), instanceOf(MemberDeclaration.class));
        assertNotNull(((MemberDeclaration) decl.getElement().getMemberdecl()).getMethod());
        testCalculationAndResolution(decl, JMLSinglelineSpec.class);
    }
    
    private void testJMLSinglelineSpecField(JMLSinglelineSpec decl) {
        assertThat(decl.getElement().getMemberdecl(), instanceOf(MemberDeclaration.class));
        assertNotNull(((MemberDeclaration) decl.getElement().getMemberdecl()).getField());
        testCalculationAndResolution(decl, JMLSinglelineSpec.class);
    }

}
