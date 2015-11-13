package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.metamodels;

import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.PackageImport;
import org.emftext.language.java.imports.StaticClassifierImport;
import org.emftext.language.java.imports.StaticMemberImport;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JaMoPPTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.Initializer;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils.ModelLoader.IResourceFiles;

public class JaMoPPTUIDCalculatorAndResolverTest extends
        TUIDCalculatorAndResolverTestBase<JaMoPPTUIDCalculatorAndResolver, CompilationUnit> {

    private enum ResourceFiles implements IResourceFiles {
        DEFAULT("Java_CU.java.resource"), TopLevelInterface("Java_CU_TopLevelInterface.java.resource"), Packageless(
                "Java_CU_Packageless.java.resource");

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
        Initializer.initJaMoPP();
    }

    @Override
    protected JaMoPPTUIDCalculatorAndResolver getNewTUIDGenerator() {
        return new JaMoPPTUIDCalculatorAndResolver();
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
    public void testTopLevelClass() {
        testCalculationAndResolution(rootObject.getClassifiers().get(0), Class.class);
    }

    @Test
    public void testTopLevelInterface() throws IOException {
        rootObject = loadResourceModel(ResourceFiles.TopLevelInterface);
        testCalculationAndResolution(rootObject.getClassifiers().get(0), Interface.class);
    }

    @Test
    public void testClassifierImport() {
        testCalculationAndResolution(rootObject.getImports().get(0), ClassifierImport.class);
    }

    @Test
    public void testPackageImport() {
        testCalculationAndResolution(rootObject.getImports().get(1), PackageImport.class);
    }

    @Test
    public void testStaticClassifierImport() {
        testCalculationAndResolution(rootObject.getImports().get(2), StaticClassifierImport.class);
    }

    @Test
    public void testStaticMemberImport() {
        testCalculationAndResolution(rootObject.getImports().get(3), StaticMemberImport.class);
    }
    
    @Test
    public void testClassModifier() {
        testCalculationAndResolution(rootObject.getClassifiers().get(0).getAnnotationsAndModifiers().get(0), Modifier.class);
    }

    @Test
    public void testCompilationUnitPackageless() throws IOException {
        rootObject = loadResourceModel(ResourceFiles.Packageless);
        testCalculationAndResolution(rootObject, CompilationUnit.class);
    }

    @Test
    public void testMethod() {
        testCalculationAndResolution(rootObject.getClassifiers().get(0).getMethods().get(0), Method.class);
    }

    @Test
    public void testField() {
        testCalculationAndResolution(rootObject.getClassifiers().get(0).getFields().get(0), Field.class);
    }
    
    @Test
    public void testFieldModifier() {
        testCalculationAndResolution(rootObject.getClassifiers().get(0).getFields().get(0).getModifiers().get(0), Modifier.class);
    }
    
    @Test
    public void testParameter() {
        testCalculationAndResolution(rootObject.getClassifiers().get(0).getMethods().get(1).getParameters().get(0), Parameter.class);
    }
    
    @Test
    public void testParameterPrimitiveType() {
        testCalculationAndResolution(rootObject.getClassifiers().get(0).getMethods().get(1).getParameters().get(0).getTypeReference(), PrimitiveType.class);
    }
    
    @Test
    public void testNamespaceClassifierReferenceForExceptions() {
        testCalculationAndResolution(rootObject.getClassifiers().get(0).getMethods().get(2).getExceptions().get(0), NamespaceClassifierReference.class);
    }
    
    @Test
    public void testPrefixTUIDConstructionForDirectMember() {
        ConcreteClassifier c = rootObject.getClassifiers().get(0);
        Method m = c.getMethods().get(0);
        String prefix = tuidGenerator.calculateTUIDFromEObject(c);
        String tuid = tuidGenerator.calculateTUIDFromEObject(m, c, prefix);
        EObject actualObject = tuidGenerator.resolveEObjectFromRootAndFullTUID(rootObject, tuid);
        
        assertSame(m, actualObject);
    }
    
    @Test
    public void testPrefixTUIDConstructionForTwoLayersMember() {
        ConcreteClassifier c = rootObject.getClassifiers().get(0);
        Parameter p = c.getMethods().get(1).getParameters().get(0);
        String prefix = tuidGenerator.calculateTUIDFromEObject(c);
        String tuid = tuidGenerator.calculateTUIDFromEObject(p, c, prefix);
        EObject actualObject = tuidGenerator.resolveEObjectFromRootAndFullTUID(rootObject, tuid);
        
        assertSame(p, actualObject);
    }
    
    @Test
    public void testPrefixTUIDConstructionWithSameObject() {
        String prefix = tuidGenerator.calculateTUIDFromEObject(rootObject);
        String tuid = tuidGenerator.calculateTUIDFromEObject(rootObject, rootObject, prefix);
        
        EObject actualObject = tuidGenerator.resolveEObjectFromRootAndFullTUID(rootObject, tuid);
        
        assertSame(rootObject, actualObject);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidPrefix() {
        tuidGenerator.calculateTUIDFromEObject(rootObject, null, "");
    }

}
