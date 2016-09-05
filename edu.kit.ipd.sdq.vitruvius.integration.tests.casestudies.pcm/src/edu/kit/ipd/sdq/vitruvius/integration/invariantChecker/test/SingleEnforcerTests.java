package edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryImpl;

import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.InvariantEnforcer;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.InvariantEnforcerFacade;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.InvariantEnforcerFacadeBuilder;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.PCMRepositoryElementSelector;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.PCMRepositoryExtractor;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPBeginChar;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPComponentInterfaceImplementsAmbiguity;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPJavaKeywords;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSameIdentifier;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPSpecialChars;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPVitruviusKeywords;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.PCMtoJaMoPPWhiteSpace;
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.RepositoryModelLoader;

//TODO: refactor load/log parts
/**
 * For each existing InvariantEnforcer (ivE) load one problem instance (model), each model has 3-10
 * issues. These should be detected (logged)
 *
 * ivE(model) = model* The second tests checks if ivE(model) = model* = ivE(model*) (each invariant
 * enforcer solves its issue in one step) For convenience reasons, the second test for each class is
 * integrated in the first type of test
 *
 *
 * @author Johannes Hoor
 *
 */
public class SingleEnforcerTests {
    private static File locallog;
    private static WriterAppender wa;

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    /**
     * Redo invariant check.
     *
     * @param model
     *            (precondition: invariants have already been enforced on this model)
     * @param enforcer
     *            the enforcer
     * @param oldctr
     *            the oldctr
     */
    private void redoInvariantCheck(final Resource model, final InvariantEnforcer enforcer, final int oldctr) {

        final Resource rMod = enforcer.loadEnforceReturn(model);
        final String tmpURI = rMod.getURI().toFileString().replaceFirst("MOD", "MOD2");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        rMod.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(rMod);
        // there should be no changes made on the already refactored model, ctr
        // must show the exact
        // same # as oldctr
        assertTrue(ctr == oldctr);
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // TODO: Remove filewriter from logger
    }

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {

        // final Logger logger = LogManager.getLogger(InvariantEnforcer.class); 20.03.15: old
        // version, does not work anymore, as invariant enforce use the root logger
        // FileOutputStream fos = new FileOutputStream(new
        // File("Testmodels/invariantTests/testlog"));

        final Logger logger = LogManager.getRootLogger();
        locallog = new File("testlog");
        final FileWriter fw = new FileWriter(locallog);

        logger.setLevel(Level.INFO);
        wa = new WriterAppender();
        wa.setWriter(fw);
        wa.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
        logger.addAppender(wa);
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
        locallog.delete();
        final Logger logger = LogManager.getRootLogger();
        wa.close();
        logger.removeAppender(wa);

    }

    /**
     * Log file exists.
     */
    @Test
    public void logFileExists() {
        assertTrue(locallog != null);
    }

    private Repository getRepository(final Resource r) {
        final PCMRepositoryExtractor rootExtractor = new PCMRepositoryExtractor();
        return rootExtractor.getImpl(r);
    }

    /**
     * checks the amount of changes made by the invariant enforcer.
     *
     * @param rMod
     *            the r mod
     * @return the int
     */
    private int checkAmountOfChanges(final Resource rMod) {
        try {
            rMod.save(null);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        int ctr = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(locallog))) {
            String line;

            while ((line = br.readLine()) != null) {
                ctr++;
            }
        } catch (final IOException e) {

            e.printStackTrace();
        }
        return ctr;
    }

    /**
     * Begin char test.
     */
    @Test
    public void beginCharTest() {
        final String path = "Testmodels/invariantTests/beginchar.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);
        final InvariantEnforcerFacade tester = InvariantEnforcerFacadeBuilder
                .buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(), new PCMtoJaMoPPBeginChar());
        final Resource rMod = tester.loadEnforceReturn(r);
        final String tmpURI = rMod.getURI().toFileString().replaceFirst("beginchar", "begincharMOD");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        rMod.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(rMod);

        assertTrue(ctr == 4);
        // qualitative tests
        final Repository repo = this.getRepository(rMod);
        assertTrue(repo.getComponents__Repository().get(0).getEntityName().equals("RN3_BadComponent"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(0)).getSignatures__OperationInterface()
                .get(0).getEntityName().equals("RN0_badMethod"));
        assertTrue(repo.getInterfaces__Repository().get(1).getEntityName().equals("RN1_BadInterface"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(1)).getSignatures__OperationInterface()
                .get(0).getParameters__OperationSignature().get(0).getEntityName().equals("RN2_Badparam"));

        this.redoInvariantCheck(rMod, tester, ctr);
    }

    /**
     * Sameidentifier test.
     */
    @Test
    public void sameidentifierTest() {
        final String path = "Testmodels/invariantTests/sameidentifier.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);
        final PCMtoJaMoPPSameIdentifier tester = new PCMtoJaMoPPSameIdentifier();
        final Resource rMod = tester.loadEnforceReturn(r);
        final String tmpURI = rMod.getURI().toFileString().replaceFirst("sameidentifier", "sameidentifierMOD");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        rMod.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(rMod);
        assertTrue(ctr == 1);
        // qualitative tests
        final Repository repo = this.getRepository(rMod);
        assertTrue(repo.getInterfaces__Repository().get(0).getEntityName().equals("I_WebShop"));

        this.redoInvariantCheck(rMod, tester, ctr);
    }

    /**
     * Ambiguity test.
     */
    @Test
    public void ambiguityTest() {
        final String path = "Testmodels/invariantTests/ambig.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);
        final PCMtoJaMoPPComponentInterfaceImplementsAmbiguity tester = new PCMtoJaMoPPComponentInterfaceImplementsAmbiguity();
        final Resource rMod = tester.loadEnforceReturn(r);
        final String tmpURI = rMod.getURI().toFileString().replaceFirst("ambig", "ambigMOD");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        rMod.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(rMod);
        assertTrue(ctr == 2);
        // qualitative tests
        final Repository repo = this.getRepository(rMod);
        for (final Interface i : repo.getInterfaces__Repository()) {
            if (i.getEntityName().equals("InterfaceB")) {
                assertTrue(((OperationInterface) i).getSignatures__OperationInterface().get(0).getEntityName()
                        .equals("RNDUPLICATE_0foo"));
            } else if (i.getEntityName().equals("InterfaceE")) {
                assertTrue(((OperationInterface) i).getSignatures__OperationInterface().get(0).getEntityName()
                        .equals("RNDUPLICATE_1world"));
                assertTrue(((OperationInterface) i).getSignatures__OperationInterface().get(1).getEntityName()
                        .equals("RNDUPLICATE_2doSomething"));
            }
        }

        this.redoInvariantCheck(rMod, tester, ctr);
    }

    /**
     * Specialchars test.
     */
    @Test
    public void specialcharsTest() {
        final String path = "Testmodels/invariantTests/specialchars.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);
        final InvariantEnforcerFacade tester = InvariantEnforcerFacadeBuilder
                .buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(), new PCMtoJaMoPPSpecialChars());
        final Resource rMod = tester.loadEnforceReturn(r);
        final String tmpURI = rMod.getURI().toFileString().replaceFirst("specialchars", "specialcharsMOD");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        rMod.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(rMod);
        assertTrue(ctr == 4);
        // qualitative tests
        final RepositoryImpl repo = (RepositoryImpl) this.getRepository(rMod);
        assertTrue(repo.getComponents__Repository().get(0).getEntityName().equals("Bad_3Component"));
        assertTrue(repo.getInterfaces__Repository().get(0).getEntityName().equals("Bad_0Inter9Face"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(0)).getSignatures__OperationInterface()
                .get(0).getEntityName().equals("bad_1Method"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(0)).getSignatures__OperationInterface()
                .get(0).getParameters__OperationSignature().get(1).getEntityName().equals("bad_2_2_2_2"));
        this.redoInvariantCheck(rMod, tester, ctr);
    }

    /**
     * Vitkeyword test.
     */
    @Test
    public void vitkeywordTest() {
        final String path = "Testmodels/invariantTests/vitkeyword.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);
        final InvariantEnforcerFacade tester = InvariantEnforcerFacadeBuilder
                .buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(), new PCMtoJaMoPPVitruviusKeywords());
        final Resource rMod = tester.loadEnforceReturn(r);
        final String tmpURI = rMod.getURI().toFileString().replaceFirst("vitkeyword", "vitkeywordMOD");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        rMod.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(rMod);

        assertTrue(ctr == 4);
        final RepositoryImpl repo = (RepositoryImpl) this.getRepository(rMod);
        assertTrue(repo.getComponents__Repository().get(0).getEntityName().equals("RN3_contract"));
        assertTrue(repo.getInterfaces__Repository().get(0).getEntityName().equals("RN0_datatypes"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(0)).getSignatures__OperationInterface()
                .get(0).getEntityName().equals("RN1_datatypes"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(1)).getSignatures__OperationInterface()
                .get(0).getEntityName().equals("RN2_contract"));

        this.redoInvariantCheck(rMod, tester, ctr);
    }

    /**
     * Whitespace test.
     */
    @Test
    public void whitespaceTest() {
        final String path = "Testmodels/invariantTests/whitespace.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);
        final InvariantEnforcerFacade tester = InvariantEnforcerFacadeBuilder
                .buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(), new PCMtoJaMoPPWhiteSpace());
        final Resource rMod = tester.loadEnforceReturn(r);
        final String tmpURI = rMod.getURI().toFileString().replaceFirst("whitespace", "whitespaceMOD");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        rMod.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(rMod);

        assertTrue(ctr == 4);

        // qualitative tests
        final RepositoryImpl repo = (RepositoryImpl) this.getRepository(rMod);
        assertTrue(repo.getComponents__Repository().get(0).getEntityName().equals("Bad_Component"));
        assertTrue(repo.getInterfaces__Repository().get(1).getEntityName().equals("Bad_Interface"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(1)).getSignatures__OperationInterface()
                .get(1).getEntityName().equals("bad_method"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(0)).getSignatures__OperationInterface()
                .get(0).getParameters__OperationSignature().get(0).getEntityName().equals("bad_param"));

        this.redoInvariantCheck(rMod, tester, ctr);

    }

    /**
     * Javakeywords test.
     */
    @Test
    public void javakeywordsTest() {
        final String path = "Testmodels/invariantTests/javakeywords.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);
        final InvariantEnforcerFacade tester = InvariantEnforcerFacadeBuilder
                .buildInvariantEnforcerFacade(new PCMRepositoryElementSelector(), new PCMtoJaMoPPJavaKeywords());
        final Resource rMod = tester.loadEnforceReturn(r);
        final String tmpURI = rMod.getURI().toFileString().replaceFirst("javakeywords", "javakeywordsMOD");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        rMod.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(rMod);
        assertTrue(ctr == 4);

        // qualitative tests
        final RepositoryImpl repo = (RepositoryImpl) this.getRepository(rMod);
        assertTrue(repo.getComponents__Repository().get(1).getEntityName().equals("RN3_if"));
        assertTrue(repo.getInterfaces__Repository().get(1).getEntityName().equals("RN0_transient"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(2)).getSignatures__OperationInterface()
                .get(0).getEntityName().equals("RN2_break"));
        assertTrue(((OperationInterface) repo.getInterfaces__Repository().get(1)).getSignatures__OperationInterface()
                .get(0).getParameters__OperationSignature().get(0).getEntityName().equals("RN1_goto"));

        this.redoInvariantCheck(rMod, tester, ctr);

    }

}
