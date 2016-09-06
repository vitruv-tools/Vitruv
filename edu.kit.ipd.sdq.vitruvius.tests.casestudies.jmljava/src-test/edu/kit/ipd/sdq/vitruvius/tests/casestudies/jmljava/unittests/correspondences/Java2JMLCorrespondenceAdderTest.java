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

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.FormalParameterDecl;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSinglelineSpec;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecifiedElement;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifier;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclaration;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.NormalClassDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.Utilities;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.CommonTasks;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.Initializer;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;

public class Java2JMLCorrespondenceAdderTest {

    private enum JavaResourceFiles implements IResourceFiles {
        CLASS_WO_PACKAGE("CompilationUnit.java.resource"), CLASS_W_PACKAGE(
                "CompilationUnitWithPackageName.java.resource"), CLASS_FIELDS(
                        "ClassField.java.resource"), CLASS_Methods("ClassMethods.java.resource"), IMPORTS(
                                "Imports.java.resource");

        private final String modelFileName;

        JavaResourceFiles(final String modelFileName) {
            this.modelFileName = modelFileName;
        }

        @Override
        public String getModelFileName() {
            return this.modelFileName;
        }
    }

    private enum JMLResourceFiles implements IResourceFiles {
        CLASS_WO_PACKAGE("CompilationUnit.jml.resource"), CLASS_W_PACKAGE(
                "CompilationUnitWithPackageName.jml.resource"), CLASS_FIELDS("ClassField.jml.resource"), CLASS_Methods(
                        "ClassMethods.jml.resource"), IMPORTS("Imports.jml.resource");

        private final String modelFileName;

        JMLResourceFiles(final String modelFileName) {
            this.modelFileName = modelFileName;
        }

        @Override
        public String getModelFileName() {
            return this.modelFileName;
        }
    }

    private static Mapping MAPPING;
    private CorrespondenceModel ci;
    private CompilationUnit javaCu;
    private edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit jmlCu;

    @BeforeClass
    public static void init() {
        Initializer.initLogging();
        Initializer.initJaMoPP();
        Initializer.initJML();
        MAPPING = CommonTasks.createMapping();
    }

    private void runTest(final JavaResourceFiles javaFile, final JMLResourceFiles jmlFile) throws Exception {
        final ModelInstance java = CommonTasks.loadModelInstance(javaFile, this);
        this.javaCu = java.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        final ModelInstance jml = CommonTasks.loadModelInstance(jmlFile, this);
        this.jmlCu = jml.getUniqueRootEObjectIfCorrectlyTyped(
                edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit.class);
        final Pair<ModelInstance, ModelInstance> modelInstancePair = new Pair<ModelInstance, ModelInstance>(java, jml);
        final ModelProviding mp = CommonTasks.createModelProviding(modelInstancePair);
        this.ci = CommonTasks.createCorrespondenceModel(MAPPING, mp, modelInstancePair);
    }

    private void assertCorrespondenceExistsBidirectional(final EObject o1, final EObject o2) {
        assertNotNull(o1);
        assertNotNull(o2);
        this.assertCorrespondenceFromFirstToSecondExists(o1, o2);
        this.assertCorrespondenceFromFirstToSecondExists(o2, o1);
    }

    private void assertCorrespondenceFromFirstToSecondExists(final EObject o1, final EObject o2) {
        final Set<EObject> o1Corrs = CorrespondenceModelUtil.getCorrespondingEObjects(this.ci, o1);
        boolean correspondenceExists = false;
        for (final EObject o : o1Corrs) {
            if (EcoreUtil.equals(o2, o)) {
                correspondenceExists = true;
                break;
            }
        }
        assertTrue("There is no correspondence between o1 and o2.", correspondenceExists);
    }

    @Test
    public void testClassWithoutPackageCorrespondence() throws Exception {
        this.runTest(JavaResourceFiles.CLASS_WO_PACKAGE, JMLResourceFiles.CLASS_WO_PACKAGE);

        this.assertCorrespondenceExistsBidirectional(this.javaCu, this.jmlCu);
        this.assertCorrespondenceExistsBidirectional(this.javaCu.getClassifiers().get(0),
                this.jmlCu.getTypedeclaration().get(0));
        this.assertCorrespondenceExistsBidirectional(this.javaCu.getClassifiers().get(0),
                this.jmlCu.getTypedeclaration().get(0).getClassOrInterfaceDeclaration());
        this.assertCorrespondenceExistsBidirectional(
                ((Class) this.javaCu.getClassifiers().get(0)).getImplements().get(0),
                ((NormalClassDeclaration) this.jmlCu.getTypedeclaration().get(0).getClassOrInterfaceDeclaration())
                        .getImplementedTypes().get(0));
        this.assertCorrespondenceExistsBidirectional(((Class) this.javaCu.getClassifiers().get(0)).getExtends(),
                ((NormalClassDeclaration) this.jmlCu.getTypedeclaration().get(0).getClassOrInterfaceDeclaration())
                        .getSuperType());
    }

    @Test
    public void testClassWithPackageCorrespondence() throws Exception {
        this.runTest(JavaResourceFiles.CLASS_W_PACKAGE, JMLResourceFiles.CLASS_W_PACKAGE);

        this.assertCorrespondenceExistsBidirectional(this.javaCu, this.jmlCu);
        this.assertCorrespondenceExistsBidirectional(this.javaCu.getClassifiers().get(0),
                this.jmlCu.getTypedeclaration().get(0));
        this.assertCorrespondenceExistsBidirectional(this.javaCu.getClassifiers().get(0),
                this.jmlCu.getTypedeclaration().get(0).getClassOrInterfaceDeclaration());
    }

    @Test
    public void testImportCorrespondence() throws Exception {
        this.runTest(JavaResourceFiles.IMPORTS, JMLResourceFiles.IMPORTS);

        this.assertCorrespondenceExistsBidirectional(this.javaCu, this.jmlCu);
        this.assertCorrespondenceExistsBidirectional(this.javaCu.getImports().get(0),
                this.jmlCu.getImportdeclaration().get(3));
        this.assertCorrespondenceExistsBidirectional(this.javaCu.getImports().get(1),
                this.jmlCu.getImportdeclaration().get(2));
        this.assertCorrespondenceExistsBidirectional(this.javaCu.getImports().get(2),
                this.jmlCu.getImportdeclaration().get(1));
        this.assertCorrespondenceExistsBidirectional(this.javaCu.getImports().get(3),
                this.jmlCu.getImportdeclaration().get(0));
    }

    @Test
    public void testFieldCorrespondence() throws Exception {
        this.runTest(JavaResourceFiles.CLASS_FIELDS, JMLResourceFiles.CLASS_FIELDS);

        this.assertCorrespondenceExistsBidirectional(this.javaCu, this.jmlCu);
        this.assertFieldCorrespondences(this.javaCu.getClassifiers().get(0).getFields().get(0),
                (JMLSinglelineSpec) Utilities.getFirstChildOfType(this.jmlCu, NormalClassDeclaration.class)
                        .getBodyDeclarations().get(4));
        this.assertFieldCorrespondences(this.javaCu.getClassifiers().get(0).getFields().get(1),
                (JMLSinglelineSpec) Utilities.getFirstChildOfType(this.jmlCu, NormalClassDeclaration.class)
                        .getBodyDeclarations().get(3));
        this.assertFieldCorrespondences(this.javaCu.getClassifiers().get(0).getFields().get(2),
                (JMLSinglelineSpec) Utilities.getFirstChildOfType(this.jmlCu, NormalClassDeclaration.class)
                        .getBodyDeclarations().get(2));
        this.assertFieldCorrespondences(this.javaCu.getClassifiers().get(0).getFields().get(3),
                (JMLSinglelineSpec) Utilities.getFirstChildOfType(this.jmlCu, NormalClassDeclaration.class)
                        .getBodyDeclarations().get(1));
        this.assertFieldCorrespondences(this.javaCu.getClassifiers().get(0).getFields().get(4),
                (JMLSinglelineSpec) Utilities.getFirstChildOfType(this.jmlCu, NormalClassDeclaration.class)
                        .getBodyDeclarations().get(0));
    }

    @Test
    public void testMethodCorrespondence() throws Exception {
        this.runTest(JavaResourceFiles.CLASS_Methods, JMLResourceFiles.CLASS_Methods);

        this.assertCorrespondenceExistsBidirectional(this.javaCu, this.jmlCu);
        this.assertMethodCorrespondences(this.javaCu.getClassifiers().get(0).getMethods().get(0),
                (JMLSpecifiedElement) Utilities.getFirstChildOfType(this.jmlCu, NormalClassDeclaration.class)
                        .getBodyDeclarations().get(0));
        this.assertMethodCorrespondences(this.javaCu.getClassifiers().get(0).getMethods().get(1),
                (JMLSpecifiedElement) Utilities.getFirstChildOfType(this.jmlCu, NormalClassDeclaration.class)
                        .getBodyDeclarations().get(1));
        this.assertMethodCorrespondences(this.javaCu.getClassifiers().get(0).getMethods().get(2),
                (JMLSpecifiedElement) Utilities.getFirstChildOfType(this.jmlCu, NormalClassDeclaration.class)
                        .getBodyDeclarations().get(2));
    }

    /**
     * Asserts that all relevant correspondences between the fields are set. Assumptions: Modifiers
     * are in same order and only one variable is declared.
     * 
     * @param javaField
     * @param jmlField
     */
    private void assertFieldCorrespondences(final Field javaField, final JMLSpecifiedElement jmlFieldSpec) {
        this.assertCorrespondenceExistsBidirectional(javaField, jmlFieldSpec);
        final MemberDeclWithModifier jmlField = jmlFieldSpec.getElement();
        this.assertCorrespondenceExistsBidirectional(javaField, jmlField);
        this.assertCorrespondenceExistsBidirectional(javaField, jmlField.getMemberdecl());
        this.assertCorrespondenceExistsBidirectional(javaField,
                ((MemberDeclaration) jmlField.getMemberdecl()).getField());
        this.assertCorrespondenceExistsBidirectional(javaField,
                ((MemberDeclaration) jmlField.getMemberdecl()).getField().getVariabledeclarator().get(0));
        this.assertCorrespondenceExistsBidirectional(javaField.getTypeReference(),
                ((MemberDeclaration) jmlField.getMemberdecl()).getType());
        for (int i = 0; i < javaField.getModifiers().size(); ++i) {
            this.assertCorrespondenceExistsBidirectional(javaField.getModifiers().get(i),
                    jmlField.getModifiers().get(i));
        }
    }

    private void assertMethodCorrespondences(final Method javaMethod, final JMLSpecifiedElement jmlMethodSpec) {
        this.assertCorrespondenceExistsBidirectional(javaMethod, jmlMethodSpec);
        final MemberDeclWithModifier jmlMethod = jmlMethodSpec.getElement();
        this.assertCorrespondenceExistsBidirectional(javaMethod, jmlMethod);
        this.assertCorrespondenceExistsBidirectional(javaMethod, jmlMethod.getMemberdecl());
        this.assertCorrespondenceExistsBidirectional(javaMethod,
                ((MemberDeclaration) jmlMethod.getMemberdecl()).getMethod());
        if (((MemberDeclaration) jmlMethod.getMemberdecl()).getType() != null) {
            this.assertCorrespondenceExistsBidirectional(javaMethod.getTypeReference(),
                    ((MemberDeclaration) jmlMethod.getMemberdecl()).getType());
        }
        for (int i = 0; i < javaMethod.getModifiers().size(); ++i) {
            this.assertCorrespondenceExistsBidirectional(javaMethod.getModifiers().get(i),
                    jmlMethod.getModifiers().get(i));
        }
        for (int i = 0; i < javaMethod.getParameters().size(); ++i) {
            final Parameter javaParameter = javaMethod.getParameters().get(i);
            final FormalParameterDecl jmlParameter = ((MemberDeclaration) jmlMethod.getMemberdecl()).getMethod()
                    .getParameters().get(i);
            this.assertCorrespondenceExistsBidirectional(javaParameter, jmlParameter);
            this.assertCorrespondenceExistsBidirectional(javaParameter.getTypeReference(), jmlParameter.getType());
            for (int j = 0; j < javaParameter.getModifiers().size(); ++j) {
                this.assertCorrespondenceExistsBidirectional(javaParameter.getModifiers().get(j),
                        jmlParameter.getModifiers().get(j));
            }
        }
        for (int i = 0; i < javaMethod.getExceptions().size(); ++i) {
            this.assertCorrespondenceExistsBidirectional(javaMethod.getExceptions().get(i),
                    ((MemberDeclaration) jmlMethod.getMemberdecl()).getMethod().getExceptions().get(i));
        }
    }

}
