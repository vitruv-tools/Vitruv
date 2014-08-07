package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.jamoppuidcalculatorandresolver;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.JaMoPPTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public class JaMoPPTUIDCalculatorAndResolverTest {

    private EObject rootJaMoPP;
    private JaMoPPTUIDCalculatorAndResolver jamoppTUIDCR;
    private URI uri;

    @Before
    public void setUp() throws Exception {
        this.jamoppTUIDCR = new JaMoPPTUIDCalculatorAndResolver();

        TestUtils.registerMetamodels();

        // create new ResourceSet
        final ResourceSet resSet = new ResourceSetImpl();

        // Create URI
        this.uri = URI
                .createURI("src-test/edu/kit/ipd/sdq/vitruvius/tests/casestudies/pcmjava/jamoppuidcalculatorandresolver/JaMoPPTUIDCalculatorAndResolverTest.java");

        // create new Resource
        final Resource jaMoPPResource = resSet.getResource(this.uri, true);

        // get Repository from resource
        this.rootJaMoPP = jaMoPPResource.getContents().get(0);
    }

    @Test
    public void testGetTUID() {
        final CompilationUnit cu = (CompilationUnit) this.rootJaMoPP;
        System.out.println("TUID for comilationUnit '" + cu.getName() + "': " + this.jamoppTUIDCR.calculateTUIDFromEObject(cu));
        for (final Classifier classifier : cu.getClassifiers()) {
            System.out.println("TUID for classifier '" + classifier.getName() + "': "
                    + this.jamoppTUIDCR.calculateTUIDFromEObject(classifier));
            for (final Member member : classifier.getAllMembers(null)) {
                System.out.println("TUID for member '" + member.toString() + "': " + this.jamoppTUIDCR.calculateTUIDFromEObject(member));
            }
        }
    }

    @Test
    public void testGetIdentifiedEObject() {
        // create TUIDs from JaMoPP root
        final CompilationUnit cu = (CompilationUnit) this.rootJaMoPP;
        final String tuidCu = this.jamoppTUIDCR.calculateTUIDFromEObject(cu);
        final List<String> classifierTuids = new ArrayList<String>();
        final List<String> methodTuids = new ArrayList<String>(16);
        for (final Classifier classifier : cu.getClassifiers()) {
            classifierTuids.add(this.jamoppTUIDCR.calculateTUIDFromEObject(classifier));
            for (final Member member : classifier.getAllMembers(cu)) {
                if (member instanceof Method) {
                    methodTuids.add(this.jamoppTUIDCR.calculateTUIDFromEObject(member));
                }
            }
        }

        // reparse java file
        final ResourceSet newResourceSet = new ResourceSetImpl();
        final Resource newJaMoPPResource = newResourceSet.getResource(this.uri, true);
        final EObject newRootEObject = newJaMoPPResource.getContents().get(0);

        // find TUIDs in new java file
        final VURI vuri = this.jamoppTUIDCR.getModelVURIContainingIdentifiedEObject(tuidCu);
        System.out.println(vuri);
        final EObject newCompilationUnit = this.jamoppTUIDCR.resolveEObjectFromRootAndFullTUID(newRootEObject,
                tuidCu);
        System.out.println("New Compilation unit: " + newCompilationUnit);
        for (final String classifierTuid : classifierTuids) {
            System.out.println("Classifier for classifier with tuid " + classifierTuid + ": "
                    + this.jamoppTUIDCR.resolveEObjectFromRootAndFullTUID(newRootEObject, classifierTuid));
        }
        for (final String methodTuid : methodTuids) {
            System.out.println("Method for method with tuid " + methodTuid + ": "
                    + this.jamoppTUIDCR.resolveEObjectFromRootAndFullTUID(newRootEObject, methodTuid));
        }

    }

    /**
     * Since the class @JaMoPPTUIDCalculatorAndResolverTest itself is used for the test the
     * following methods are necessary.
     */
    @SuppressWarnings("unused")
    private String testWithStringAsTypeReference() {
        return "";
    }

    @SuppressWarnings("unused")
    private String testWithParameters(final String param1, final String param2) {
        return "";
    }

    @SuppressWarnings("unused")
    private String testWithParameters(final String param1, final String param2, final String param3) {
        return "";
    }

    @SuppressWarnings("unused")
    private String testWithParams2(final int intParam, final String strParam) {
        return "";
    }

}
