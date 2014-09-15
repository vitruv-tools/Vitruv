package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.resource.java.mopp.JavaResourceFactory;
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
}
