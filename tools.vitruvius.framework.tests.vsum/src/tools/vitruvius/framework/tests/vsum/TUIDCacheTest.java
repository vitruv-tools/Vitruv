package tools.vitruvius.framework.tests.vsum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tools.vitruvius.framework.tests.util.TestUtil;
import tools.vitruvius.framework.tuid.DefaultTUIDCalculatorAndResolver;
import tools.vitruvius.framework.tuid.TUIDCalculatorAndResolver;
import tools.vitruvius.framework.util.bridges.EcoreResourceBridge;
import pcm_mockup.Component;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Pcm_mockupPackage;
import pcm_mockup.Repository;

/**
 * These tests can be executed as simple JUnit tests (no Plug-in tests)
 */
public class TUIDCacheTest {
    private static final Logger LOGGER = Logger.getLogger(TUIDCacheTest.class.getSimpleName());

    private static final String TEST_FOLDER = "test_tmp";

    @Before
    public void beforeTest() {
        File folder = new File(TEST_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    @After
    public void afterTest() {
        File folder = new File(TEST_FOLDER);
        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                file.delete();
            }
            folder.delete();
        }
    }

    @BeforeClass
    public static void initi() {
        TestUtil.initializeLogger();
    }

    @Test
    public void testCreateChildSaveCalcRemoveCalcSaveCalc() throws IOException {
        TUIDCalculatorAndResolver tuidCAR = prepareTUIDCAR();

        // create
        Repository repository = create();
        // child
        Component component = Pcm_mockupFactory.eINSTANCE.createComponent();
        repository.getComponents().add(component);
        // save
        // calc
        String uncachedTUIDAfterSave = saveAndCalc("", tuidCAR, component, "newtestresource");

        // remove
        remove(component);
        // calc
        String cachedTUIDAfterRemove = calc(tuidCAR, component, uncachedTUIDAfterSave, "cachedTUIDAfterRemove");

        // save
        // calc
        saveAndCalc(cachedTUIDAfterRemove, tuidCAR, component, "newtestresource");
    }

    @Test
    public void testCreateSaveCalcRemoveCalcSaveCalc() throws IOException {
        TUIDCalculatorAndResolver tuidCAR = prepareTUIDCAR();

        // create
        Repository repository = create();
        // save
        // calc
        String uncachedTUIDAfterSave = saveAndCalc("", tuidCAR, repository, "newtestresource");

        // remove
        remove(repository);
        // calc
        String cachedTUIDAfterRemove = calc(tuidCAR, repository, uncachedTUIDAfterSave, "cachedTUIDAfterRemove");

        // save
        // calc
        saveAndCalc(cachedTUIDAfterRemove, tuidCAR, repository, "newtestresource");
    }

    @Test
    public void testCreateCalcSaveCalcRemoveCalcSaveCalc() throws IOException {
        TUIDCalculatorAndResolver tuidCAR = prepareTUIDCAR();

        // create
        Repository repository = create();
        // calc
        String cachedTUIDBeforeSave = calc(tuidCAR, repository, "", "cachedTUIDAfterRemove");
        // save
        // calc
        String uncachedTUIDAfterSave = saveAndCalc(cachedTUIDBeforeSave, tuidCAR, repository, "newtestresource");

        // remove
        remove(repository);
        // calc
        String cachedTUIDAfterRemove = calc(tuidCAR, repository, uncachedTUIDAfterSave, "cachedTUIDAfterRemove");

        // save
        // calc
        saveAndCalc(cachedTUIDAfterRemove, tuidCAR, repository, "newtestresource");
    }

    @Test
    public void testCreateSaveRemoveCalcSaveCalc() throws IOException {
        TUIDCalculatorAndResolver tuidCAR = prepareTUIDCAR();

        // create
        Repository repository = create();
        // save
        save(repository, "newtestresource");

        // remove
        remove(repository);
        // calc
        String cachedTUIDAfterRemove = calc(tuidCAR, repository, "", "cachedTUIDAfterRemove");

        // save
        // calc
        saveAndCalc(cachedTUIDAfterRemove, tuidCAR, repository, "newtestresource");
    }

    @Test
    public void testCreateCalcSaveRemoveCalcSaveCalc() throws IOException {
        TUIDCalculatorAndResolver tuidCAR = prepareTUIDCAR();

        // create
        Repository repository = create();
        // calc
        String cachedTUIDBeforeSave = calc(tuidCAR, repository, "", "cachedTUIDAfterRemove");
        // save
        save(repository, "newtestresource");

        // remove
        remove(repository);
        // calc
        String cachedTUIDAfterRemove = calc(tuidCAR, repository, cachedTUIDBeforeSave, "cachedTUIDAfterRemove", true);

        // save
        // calc
        saveAndCalc(cachedTUIDAfterRemove, tuidCAR, repository, "newtestresource");
    }

    private String calc(final TUIDCalculatorAndResolver tuidCAR, final EObject eObject, final String oldTUID,
            final String msg) {
        return calc(tuidCAR, eObject, oldTUID, msg, false);
    }

    private String calc(final TUIDCalculatorAndResolver tuidCAR, final EObject eObject, final String oldTUID,
            final String msg, final boolean equalTUID) {
        String newTUID = tuidCAR.calculateTUIDFromEObject(eObject);
        traceAndAssert(oldTUID, newTUID, msg, equalTUID);
        return newTUID;
    }

    private void remove(final EObject eObject) {
        EcoreUtil.remove(eObject);
    }

    private String saveAndCalc(final String oldTUID, final TUIDCalculatorAndResolver tuidCAR, final EObject eObject,
            final String fileName) throws IOException {
        String uncachedTUIDAfterSave = saveAndCalculateTUID(eObject, tuidCAR, fileName);
        traceAndAssert(oldTUID, uncachedTUIDAfterSave, "uncachedTUIDAfterSave", false);
        return uncachedTUIDAfterSave;
    }

    private Repository create() {
        Repository repository = Pcm_mockupFactory.eINSTANCE.createRepository();
        return repository;
    }

    private TUIDCalculatorAndResolver prepareTUIDCAR() {
        EcoreResourceBridge.registerMetamodelPackages(Pcm_mockupPackage.eNS_URI, Pcm_mockupFactory.eINSTANCE);
        EcoreResourceBridge.registerDefaultXMIExtensionFactory("pcm_mockup");
        TUIDCalculatorAndResolver tuidCAR = new DefaultTUIDCalculatorAndResolver(Pcm_mockupPackage.eNS_URI);
        return tuidCAR;
    }

    private void traceAndAssert(final String oldTUID, final String newTUID, final String msg, final boolean equalTUID) {
        LOGGER.trace(msg + ": '" + newTUID + "'");
        if (equalTUID) {
            assertEquals(oldTUID, newTUID);
        } else {
            assertNotEquals(oldTUID, newTUID);
        }
    }

    private String saveAndCalculateTUID(final EObject eObject, final TUIDCalculatorAndResolver tuidCAR,
            final String fileName) throws IOException {
        save(eObject, fileName);
        return tuidCAR.calculateTUIDFromEObject(eObject);
    }

    private void save(final EObject eObject, final String fileName) throws IOException {
        URI uri = URI.createFileURI(TEST_FOLDER + "/" + fileName + ".pcm_mockup");
        ResourceSetImpl resourceSet = new ResourceSetImpl();
        Resource resource = EcoreResourceBridge.loadResourceAtURI(uri, resourceSet);
        EObject root = EcoreUtil.getRootContainer(eObject);
        EcoreResourceBridge.saveEObjectAsOnlyContent(root, resource);
    }
}
