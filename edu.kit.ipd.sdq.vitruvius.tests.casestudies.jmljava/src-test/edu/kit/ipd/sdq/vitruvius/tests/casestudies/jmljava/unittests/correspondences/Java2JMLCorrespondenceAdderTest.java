package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.correspondences;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.FormalParameterDecl;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSinglelineSpec;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSpecifiedElement;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclWithModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.NormalClassDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.Utilities;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.CommonTasks;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.Initializer;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;

public class Java2JMLCorrespondenceAdderTest {

    private enum JavaResourceFiles implements IResourceFiles {
        CLASS_WO_PACKAGE("CompilationUnit.java.resource"),
        CLASS_W_PACKAGE("CompilationUnitWithPackageName.java.resource"),
        CLASS_FIELDS("ClassField.java.resource"),
        CLASS_Methods("ClassMethods.java.resource"),
        IMPORTS("Imports.java.resource");

        private final String modelFileName;

        JavaResourceFiles(String modelFileName) {
            this.modelFileName = modelFileName;
        }

        @Override
        public String getModelFileName() {
            return modelFileName;
        }
    }
    
    private enum JMLResourceFiles implements IResourceFiles {
        CLASS_WO_PACKAGE("CompilationUnit.jml.resource"),
        CLASS_W_PACKAGE("CompilationUnitWithPackageName.jml.resource"),
        CLASS_FIELDS("ClassField.jml.resource"),
        CLASS_Methods("ClassMethods.jml.resource"),
        IMPORTS("Imports.jml.resource");

        private final String modelFileName;

        JMLResourceFiles(String modelFileName) {
            this.modelFileName = modelFileName;
        }

        @Override
        public String getModelFileName() {
            return modelFileName;
        }
    }
    
    private static Mapping MAPPING;
    private CorrespondenceInstance ci;
    private CompilationUnit javaCu;
    private edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit jmlCu;
    
    @BeforeClass
    public static void init() {
        Initializer.initLogging();
        Initializer.initJaMoPP();
        Initializer.initJML();
        MAPPING = CommonTasks.createMapping();
    }
    

    private void runTest(JavaResourceFiles javaFile, JMLResourceFiles jmlFile) throws Exception {
        ModelInstance java = CommonTasks.loadModelInstance(javaFile, this);
        javaCu = java.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        ModelInstance jml = CommonTasks.loadModelInstance(jmlFile, this);
        jmlCu = jml.getUniqueRootEObjectIfCorrectlyTyped(edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit.class);
        Pair<ModelInstance, ModelInstance> modelInstancePair = new Pair<ModelInstance, ModelInstance>(java, jml);
        ModelProviding mp = CommonTasks.createModelProviding(modelInstancePair);
        this.ci = CommonTasks.createCorrespondenceInstance(MAPPING, mp, modelInstancePair);
    }


    
    private void assertCorrespondenceExistsBidirectional(EObject o1, EObject o2) {
        assertNotNull(o1);
        assertNotNull(o2);
        assertCorrespondenceFromFirstToSecondExists(o1, o2);
        assertCorrespondenceFromFirstToSecondExists(o2, o1);
    }
    
    private void assertCorrespondenceFromFirstToSecondExists(EObject o1, EObject o2) {
        Set<EObject> o1Corrs = ci.getAllCorrespondingEObjects(o1);
        boolean correspondenceExists = false;
        for (EObject o : o1Corrs) {
            if (EcoreUtil.equals(o2, o)) {
                correspondenceExists = true;
                break;
            }
        }
        assertTrue("There is no correspondence between o1 and o2.", correspondenceExists);
    }
    
    @Test
    public void testClassWithoutPackageCorrespondence() throws Exception {
        runTest(JavaResourceFiles.CLASS_WO_PACKAGE, JMLResourceFiles.CLASS_WO_PACKAGE);
        
        assertCorrespondenceExistsBidirectional(javaCu, jmlCu);
        assertCorrespondenceExistsBidirectional(javaCu.getClassifiers().get(0), jmlCu.getTypedeclaration().get(0));
        assertCorrespondenceExistsBidirectional(javaCu.getClassifiers().get(0), jmlCu.getTypedeclaration().get(0).getClassOrInterfaceDeclaration());
        assertCorrespondenceExistsBidirectional(((Class)javaCu.getClassifiers().get(0)).getImplements().get(0), ((NormalClassDeclaration)jmlCu.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getImplementedTypes().get(0));
        assertCorrespondenceExistsBidirectional(((Class)javaCu.getClassifiers().get(0)).getExtends(), ((NormalClassDeclaration)jmlCu.getTypedeclaration().get(0).getClassOrInterfaceDeclaration()).getSuperType());
    }
    
    @Test
    public void testClassWithPackageCorrespondence() throws Exception {
        runTest(JavaResourceFiles.CLASS_W_PACKAGE, JMLResourceFiles.CLASS_W_PACKAGE);
        
        assertCorrespondenceExistsBidirectional(javaCu, jmlCu);
        assertCorrespondenceExistsBidirectional(javaCu.getClassifiers().get(0), jmlCu.getTypedeclaration().get(0));
        assertCorrespondenceExistsBidirectional(javaCu.getClassifiers().get(0), jmlCu.getTypedeclaration().get(0).getClassOrInterfaceDeclaration());
    }
    
    @Test
    public void testImportCorrespondence() throws Exception {
        runTest(JavaResourceFiles.IMPORTS, JMLResourceFiles.IMPORTS);
        
        assertCorrespondenceExistsBidirectional(javaCu, jmlCu);
        assertCorrespondenceExistsBidirectional(javaCu.getImports().get(0), jmlCu.getImportdeclaration().get(3));
        assertCorrespondenceExistsBidirectional(javaCu.getImports().get(1), jmlCu.getImportdeclaration().get(2));
        assertCorrespondenceExistsBidirectional(javaCu.getImports().get(2), jmlCu.getImportdeclaration().get(1));
        assertCorrespondenceExistsBidirectional(javaCu.getImports().get(3), jmlCu.getImportdeclaration().get(0));
    }
    
    @Test
    public void testFieldCorrespondence() throws Exception {
        runTest(JavaResourceFiles.CLASS_FIELDS, JMLResourceFiles.CLASS_FIELDS);
        
        assertCorrespondenceExistsBidirectional(javaCu, jmlCu);
        assertFieldCorrespondences(javaCu.getClassifiers().get(0).getFields().get(0), (JMLSinglelineSpec)Utilities.getFirstChildOfType(jmlCu, NormalClassDeclaration.class).getBodyDeclarations().get(4));
        assertFieldCorrespondences(javaCu.getClassifiers().get(0).getFields().get(1), (JMLSinglelineSpec)Utilities.getFirstChildOfType(jmlCu, NormalClassDeclaration.class).getBodyDeclarations().get(3));
        assertFieldCorrespondences(javaCu.getClassifiers().get(0).getFields().get(2), (JMLSinglelineSpec)Utilities.getFirstChildOfType(jmlCu, NormalClassDeclaration.class).getBodyDeclarations().get(2));
        assertFieldCorrespondences(javaCu.getClassifiers().get(0).getFields().get(3), (JMLSinglelineSpec)Utilities.getFirstChildOfType(jmlCu, NormalClassDeclaration.class).getBodyDeclarations().get(1));
        assertFieldCorrespondences(javaCu.getClassifiers().get(0).getFields().get(4), (JMLSinglelineSpec)Utilities.getFirstChildOfType(jmlCu, NormalClassDeclaration.class).getBodyDeclarations().get(0));
    }
    
    @Test
    public void testMethodCorrespondence() throws Exception {
        runTest(JavaResourceFiles.CLASS_Methods, JMLResourceFiles.CLASS_Methods);
        
        assertCorrespondenceExistsBidirectional(javaCu, jmlCu);
        assertMethodCorrespondences(javaCu.getClassifiers().get(0).getMethods().get(0), (JMLSpecifiedElement)Utilities.getFirstChildOfType(jmlCu, NormalClassDeclaration.class).getBodyDeclarations().get(0));
        assertMethodCorrespondences(javaCu.getClassifiers().get(0).getMethods().get(1), (JMLSpecifiedElement)Utilities.getFirstChildOfType(jmlCu, NormalClassDeclaration.class).getBodyDeclarations().get(1));
        assertMethodCorrespondences(javaCu.getClassifiers().get(0).getMethods().get(2), (JMLSpecifiedElement)Utilities.getFirstChildOfType(jmlCu, NormalClassDeclaration.class).getBodyDeclarations().get(2));
    }
    
    /**
     * Asserts that all relevant correspondences between the fields are set.
     * Assumptions: Modifiers are in same order and only one variable is declared.
     * @param javaField
     * @param jmlField
     */
    private void assertFieldCorrespondences(Field javaField, JMLSpecifiedElement jmlFieldSpec) {
        assertCorrespondenceExistsBidirectional(javaField, jmlFieldSpec);
        MemberDeclWithModifier jmlField = jmlFieldSpec.getElement();
        assertCorrespondenceExistsBidirectional(javaField, jmlField);
        assertCorrespondenceExistsBidirectional(javaField, jmlField.getMemberdecl());
        assertCorrespondenceExistsBidirectional(javaField, ((MemberDeclaration)jmlField.getMemberdecl()).getField());
        assertCorrespondenceExistsBidirectional(javaField, ((MemberDeclaration)jmlField.getMemberdecl()).getField().getVariabledeclarator().get(0));
        assertCorrespondenceExistsBidirectional(javaField.getTypeReference(), ((MemberDeclaration)jmlField.getMemberdecl()).getType());
        for (int i = 0; i < javaField.getModifiers().size(); ++i) {
            assertCorrespondenceExistsBidirectional(javaField.getModifiers().get(i), jmlField.getModifiers().get(i));
        }
    }
    
    private void assertMethodCorrespondences(Method javaMethod, JMLSpecifiedElement jmlMethodSpec) {
        assertCorrespondenceExistsBidirectional(javaMethod, jmlMethodSpec);
        MemberDeclWithModifier jmlMethod = jmlMethodSpec.getElement();
        assertCorrespondenceExistsBidirectional(javaMethod, jmlMethod);
        assertCorrespondenceExistsBidirectional(javaMethod, jmlMethod.getMemberdecl());
        assertCorrespondenceExistsBidirectional(javaMethod, ((MemberDeclaration)jmlMethod.getMemberdecl()).getMethod());
        if (((MemberDeclaration)jmlMethod.getMemberdecl()).getType() != null) {
            assertCorrespondenceExistsBidirectional(javaMethod.getTypeReference(), ((MemberDeclaration)jmlMethod.getMemberdecl()).getType());            
        }
        for (int i = 0; i < javaMethod.getModifiers().size(); ++i) {
            assertCorrespondenceExistsBidirectional(javaMethod.getModifiers().get(i), jmlMethod.getModifiers().get(i));
        }
        for (int i = 0; i < javaMethod.getParameters().size(); ++i) {
            Parameter javaParameter = javaMethod.getParameters().get(i);
            FormalParameterDecl jmlParameter = ((MemberDeclaration)jmlMethod.getMemberdecl()).getMethod().getParameters().get(i);
            assertCorrespondenceExistsBidirectional(javaParameter, jmlParameter);
            assertCorrespondenceExistsBidirectional(javaParameter.getTypeReference(), jmlParameter.getType());
            for (int j = 0; j < javaParameter.getModifiers().size(); ++j) {
                assertCorrespondenceExistsBidirectional(javaParameter.getModifiers().get(j), jmlParameter.getModifiers().get(j));
            }
        }
        for (int i = 0; i < javaMethod.getExceptions().size(); ++i) {
            assertCorrespondenceExistsBidirectional(javaMethod.getExceptions().get(i), ((MemberDeclaration)jmlMethod.getMemberdecl()).getMethod().getExceptions().get(i));
        }
    }
    
}
