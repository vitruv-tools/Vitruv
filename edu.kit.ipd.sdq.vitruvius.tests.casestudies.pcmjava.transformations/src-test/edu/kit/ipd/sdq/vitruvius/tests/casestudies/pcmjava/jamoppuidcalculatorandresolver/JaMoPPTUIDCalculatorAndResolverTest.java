package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.jamoppuidcalculatorandresolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.statements.ExpressionStatement;
import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.utils.jamopp.JaMoPPTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.jamopppcm.util.JaMoPPPCMTestUtil;

public class JaMoPPTUIDCalculatorAndResolverTest {

    private static final Logger logger = Logger.getLogger(JaMoPPTUIDCalculatorAndResolverTest.class.getSimpleName());

    private EObject jaMoPPCompilationUnit;
    private EObject jaMoPPPackage;
    private JaMoPPTUIDCalculatorAndResolver jamoppTUIDCR;
    private VURI compUnitUri;
    private VURI packageUri;

    @Before
    public void setUp() throws Exception {
        this.jamoppTUIDCR = new JaMoPPTUIDCalculatorAndResolver();
        Logger.getRootLogger().addAppender(new ConsoleAppender());
        JaMoPPPCMTestUtil.registerMetamodels();

        // create new ResourceSet
        final ResourceSet resSet = new ResourceSetImpl();

        this.compUnitUri = VURI
                .getInstance("MockupProject/src-test/TestCodeForJaMoPPTUIDCalculatorAndResolverTest.java");
        this.jaMoPPCompilationUnit = this.getRootJaMoPPObjectFromURIStr(resSet, this.compUnitUri);
        this.packageUri = VURI.getInstance("MockupProject/src-test/package-info.java");
        this.jaMoPPPackage = this.getRootJaMoPPObjectFromURIStr(resSet, this.packageUri);
    }

    public EObject getRootJaMoPPObjectFromURIStr(final ResourceSet resSet, final VURI uri) {
        // create new Resource
        final Resource jaMoPPResource = resSet.getResource(uri.getEMFUri(), true);

        // get Repository from resource
        return jaMoPPResource.getContents().get(0);
    }

    @Test
    public void testGetTUID() {
        final CompilationUnit cu = (CompilationUnit) this.jaMoPPCompilationUnit;
        logger.info("TUID for comilationUnit '" + cu.getName() + "': " + this.jamoppTUIDCR.calculateTUIDFromEObject(cu));
        for (final Classifier classifier : cu.getClassifiers()) {
            logger.info("TUID for classifier '" + classifier.getName() + "': "
                    + this.jamoppTUIDCR.calculateTUIDFromEObject(classifier));
            for (final Member member : classifier.getAllMembers(null)) {
                logger.info("TUID for member '" + member.toString() + "': "
                        + this.jamoppTUIDCR.calculateTUIDFromEObject(member));
            }
        }
    }

    @Test
    public void testGetTUIDFromPackage() {
        final org.emftext.language.java.containers.Package pack = (org.emftext.language.java.containers.Package) this.jaMoPPPackage;
        logger.info("TUID for package  '" + pack.getName() + "' : " + this.jamoppTUIDCR.calculateTUIDFromEObject(pack));
    }

    @Test
    public void testGetIdentifiedPackage() {
        // create TUIDs from JaMoPP root
        final org.emftext.language.java.containers.Package pack = (Package) this.jaMoPPPackage;
        final String tuidPackage = this.jamoppTUIDCR.calculateTUIDFromEObject(pack);

        // reparse java file
        final ResourceSet newResourceSet = new ResourceSetImpl();

        // find TUIDs in new java file
        final VURI vuri = this.jamoppTUIDCR.getModelVURIContainingIdentifiedEObject(tuidPackage);
        logger.info(vuri);
        final Resource newJaMoPPResource = newResourceSet.getResource(vuri.getEMFUri(), true);
        final EObject newRootEObject = newJaMoPPResource.getContents().get(0);
        final EObject newPack = this.jamoppTUIDCR.resolveEObjectFromRootAndFullTUID(newRootEObject, tuidPackage);
        logger.info("New Package: " + newPack);
    }

    @Test
    public void testGetIdentifiedEObject() {
        // create TUIDs from JaMoPP root
        final CompilationUnit cu = (CompilationUnit) this.jaMoPPCompilationUnit;
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
        final Resource newJaMoPPResource = newResourceSet.getResource(this.compUnitUri.getEMFUri(), true);
        final EObject newRootEObject = newJaMoPPResource.getContents().get(0);

        // find TUIDs in new java file
        final VURI vuri = this.jamoppTUIDCR.getModelVURIContainingIdentifiedEObject(tuidCu);
        System.out.println(vuri);
        final EObject newCompilationUnit = this.jamoppTUIDCR.resolveEObjectFromRootAndFullTUID(newRootEObject, tuidCu);
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

    @Test
    public void testExpressionStatement() {
        final List<ExpressionStatement> expressionStatements = this.findUsageOfClass(ExpressionStatement.class);
        assertTrue("No expression statement found", 0 < expressionStatements.size());
        final List<String> expressionStatementsTUIDs = new ArrayList<String>();
        for (final ExpressionStatement expressionStatement : expressionStatements) {
            final String currentTUID = this.jamoppTUIDCR.calculateTUIDFromEObject(expressionStatement);
            expressionStatementsTUIDs.add(currentTUID);
        }

        final List<ExpressionStatement> foundExpressionStatements = new ArrayList<ExpressionStatement>();
        for (final String expressoinStatementTUID : expressionStatementsTUIDs) {
            final VURI containingFile = this.jamoppTUIDCR
                    .getModelVURIContainingIdentifiedEObject(expressoinStatementTUID);
            final ResourceSet resourceSet = new ResourceSetImpl();
            final Resource jaMoPPResource = resourceSet.getResource(containingFile.getEMFUri(), true);
            final EObject rootEObject = jaMoPPResource.getContents().get(0);
            final EObject eObject = this.jamoppTUIDCR.resolveEObjectFromRootAndFullTUID(rootEObject,
                    expressoinStatementTUID);
            assertTrue("eObject is not an instanceof expression statement", eObject instanceof ExpressionStatement);
            foundExpressionStatements.add((ExpressionStatement) eObject);
        }

        assertEquals("Wrong number of expression statements found", expressionStatements.size(),
                foundExpressionStatements.size());
    }

    private <T> List<T> findUsageOfClass(final Class<T> type) {
        final List<T> expressionStatements = new ArrayList<T>();
        final TreeIterator<Object> eObjects = EcoreUtil.getAllContents(this.jaMoPPCompilationUnit, false);
        while (eObjects.hasNext()) {
            final Object eObject = eObjects.next();
            if (type.isInstance(eObject)) {
                expressionStatements.add((T) eObject);
            }
        }
        return expressionStatements;
    }
}
