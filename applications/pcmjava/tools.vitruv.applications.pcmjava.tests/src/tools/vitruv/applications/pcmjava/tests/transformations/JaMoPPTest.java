package tools.vitruv.applications.pcmjava.tests.transformations;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.commons.layout.LayoutInformation;
import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.resource.java.mopp.JavaResourceFactory;
import org.emftext.language.java.types.TypesFactory;
import org.junit.Before;
import org.junit.Test;

public class JaMoPPTest {

    @Before
    public void setUp() throws Exception {
        // register JaMoPP package and factory globally
        EPackage.Registry.INSTANCE.put(JavaPackage.eNS_URI, JavaPackage.eINSTANCE);
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("java", new JavaResourceFactory());
        m.put("", new JavaResourceFactory());
    }

    @Test
    public void testJaMoPPRename() throws IOException {
        final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
        final Interface jaMoPPInterface = ClassifiersFactory.eINSTANCE.createInterface();
        jaMoPPInterface.setName("TestJava");
        final CompilationUnit jaMoPPCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
        jaMoPPCompilationUnit.setName("TestJava.java");
        jaMoPPCompilationUnit.getClassifiers().add(jaMoPPInterface);
        jaMoPPPackage.getCompilationUnits().add(jaMoPPCompilationUnit);
        jaMoPPPackage.setName("testJaMoPPPackage");
        final ResourceSet resourceSet = new ResourceSetImpl();
        String uriStr = "src/testpackage/" + jaMoPPCompilationUnit.getName();
        URI uri = URI.createFileURI(uriStr);
        Resource resource = resourceSet.createResource(uri);
        resource.getContents().add(jaMoPPCompilationUnit);
        resource.save(null);
        jaMoPPCompilationUnit.setName("TestRenameJava.java");
        resource.delete(null);
        uriStr = "src/testpackage/" + jaMoPPCompilationUnit.getName();
        uri = URI.createFileURI(uriStr);
        resource = resourceSet.createResource(uri);
        resource.getContents().add(jaMoPPCompilationUnit);
        resource.save(null);
    }

    @Test
    public void testJaMoPPPackage() throws Exception {
        final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
        jaMoPPPackage.setName("testpackageName");
        final ResourceSet resourceSet = new ResourceSetImpl();
        final String srcPath = "src-tmp/";
        String packageName = jaMoPPPackage.getNamespacesAsString() + jaMoPPPackage.getName();
        packageName = packageName.replace(".", "/");
        final URI uri = URI.createFileURI(srcPath + packageName + "/package-info.java");
        final Resource resource = resourceSet.createResource(uri);
        resource.getContents().add(jaMoPPPackage);
        resource.save(null);
    }

    @Test
    public void testAddInterfaceMethod() throws IOException {
        final CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
        cu.setName("TestAddInterfaceMethod.java");
        final Interface jaIf = ClassifiersFactory.eINSTANCE.createInterface();
        jaIf.setName("TestAddInterfaceMethod");
        cu.getClassifiers().add(jaIf);
        final ResourceSet rs = new ResourceSetImpl();
        final String srcPath = "src-tmp/";
        final String cuName = srcPath + cu.getName();
        final URI uri = URI.createFileURI(cuName);
        final Resource resource = rs.createResource(uri);
        // resource.save(null);
        final InterfaceMethod ifMethod = MembersFactory.eINSTANCE.createInterfaceMethod();
        ifMethod.setName("testMethod");
        ifMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        resource.getContents().add(cu);
        resource.save(null);
        jaIf.getMembers().add(ifMethod);
        resource.save(null);
        assertTrue("Resource of interface method is null", null != ifMethod.eResource());
    }

    @Test
    public void testGetLayoutInformations() throws Throwable {
        String uriStr = JaMoPPTest.class.getCanonicalName();
        uriStr = uriStr.replace(".", "/");
        uriStr = "src-test/" + uriStr + ".java";
        final URI uri = URI.createURI(uriStr);
        final ResourceSet resourceSet = new ResourceSetImpl();
        final Resource resource = resourceSet.createResource(uri);
        resource.load(null);
        this.assertLayoutInfosInResource(resource);
    }

    protected void assertLayoutInfosInResource(final Resource resource) {
        final CompilationUnit compilationUnit = (CompilationUnit) resource.getContents().get(0);
        this.printLayoutInformation(compilationUnit);
        for (final ConcreteClassifier classifier : compilationUnit.getClassifiers()) {
            this.printLayoutInformation(classifier);
            for (final Member member : classifier.getMembers()) {
                this.printLayoutInformation(member);
            }
        }
        assertTrue("Could not get layout information", null != compilationUnit.getLayoutInformations());
    }

    private void printLayoutInformation(final NamedElement namedElement) {
        System.out.println("commenatable.getLayoutInformations(): " + namedElement.getLayoutInformations());
        for (final LayoutInformation layoutInformation : namedElement.getLayoutInformations()) {
            final int startOffset = layoutInformation.getStartOffset();
            System.out.println("Start offset for " + namedElement.getName() + ": " + startOffset);
        }
    }
}
