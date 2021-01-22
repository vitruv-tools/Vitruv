package tools.vitruv.framework.tests.vsum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import pcm_mockup.Component;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Pcm_mockupPackage;
import pcm_mockup.Repository;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

/**
 * These tests can be executed as simple JUnit tests (no Plug-in tests)
 */
@ExtendWith({TestLogging.class, RegisterMetamodelsInStandalone.class})
public class TuidCacheTest {
	private static final Logger LOGGER = Logger.getLogger(TuidCacheTest.class.getSimpleName());

	List<File> filesToDelete = new ArrayList<>();

	@AfterEach
	public void cleanupFiles() {
		for (File file : this.filesToDelete) {
			file.delete();
		}
		this.filesToDelete.clear();
	}

	@Test
	public void testCreateChildSaveCalcRemoveCalcSaveCalc() throws IOException {
		TuidCalculatorAndResolver tuidCAR = prepareTuidCAR();

		// create
		Repository repository = create();
		// child
		Component component = Pcm_mockupFactory.eINSTANCE.createComponent();
		repository.getComponents().add(component);
		// save
		// calc
		String uncachedTuidAfterSave = saveAndCalc("", tuidCAR, component, "newtestresource");

		// remove
		remove(component);
		// calc
		String cachedTuidAfterRemove = calc(tuidCAR, component, uncachedTuidAfterSave, "cachedTuidAfterRemove");

		// save
		// calc
		saveAndCalc(cachedTuidAfterRemove, tuidCAR, component, "newtestresource");
	}

	@Test
	public void testCreateSaveCalcRemoveCalcSaveCalc() throws IOException {
		TuidCalculatorAndResolver tuidCAR = prepareTuidCAR();

		// create
		Repository repository = create();
		// save
		// calc
		String uncachedTuidAfterSave = saveAndCalc("", tuidCAR, repository, "newtestresource");

		// remove
		remove(repository);
		// calc
		String cachedTuidAfterRemove = calc(tuidCAR, repository, uncachedTuidAfterSave, "cachedTuidAfterRemove");

		// save
		// calc
		saveAndCalc(cachedTuidAfterRemove, tuidCAR, repository, "newtestresource");
	}

	@Test
	public void testCreateCalcSaveCalcRemoveCalcSaveCalc() throws IOException {
		TuidCalculatorAndResolver tuidCAR = prepareTuidCAR();

		// create
		Repository repository = create();
		// calc
		String cachedTuidBeforeSave = calc(tuidCAR, repository, "", "cachedTuidAfterRemove");
		// save
		// calc
		String uncachedTuidAfterSave = saveAndCalc(cachedTuidBeforeSave, tuidCAR, repository, "newtestresource");

		// remove
		remove(repository);
		// calc
		String cachedTuidAfterRemove = calc(tuidCAR, repository, uncachedTuidAfterSave, "cachedTuidAfterRemove");

		// save
		// calc
		saveAndCalc(cachedTuidAfterRemove, tuidCAR, repository, "newtestresource");
	}

	@Test
	public void testCreateSaveRemoveCalcSaveCalc() throws IOException {
		TuidCalculatorAndResolver tuidCAR = prepareTuidCAR();

		// create
		Repository repository = create();
		// save
		save(repository, "newtestresource");

		// remove
		remove(repository);
		// calc
		String cachedTuidAfterRemove = calc(tuidCAR, repository, "", "cachedTuidAfterRemove");

		// save
		// calc
		saveAndCalc(cachedTuidAfterRemove, tuidCAR, repository, "newtestresource");
	}

	@Test
	public void testCreateCalcSaveRemoveCalcSaveCalc() throws IOException {
		TuidCalculatorAndResolver tuidCAR = prepareTuidCAR();

		// create
		Repository repository = create();
		// calc
		String cachedTuidBeforeSave = calc(tuidCAR, repository, "", "cachedTuidAfterRemove");
		// save
		save(repository, "newtestresource");

		// remove
		remove(repository);
		// calc
		String cachedTuidAfterRemove = calc(tuidCAR, repository, cachedTuidBeforeSave, "cachedTuidAfterRemove", true);

		// save
		// calc
		saveAndCalc(cachedTuidAfterRemove, tuidCAR, repository, "newtestresource");
	}

	private String calc(final TuidCalculatorAndResolver tuidCAR, final EObject eObject, final String oldTuid,
		final String msg) {
		return calc(tuidCAR, eObject, oldTuid, msg, false);
	}

	private String calc(final TuidCalculatorAndResolver tuidCAR, final EObject eObject, final String oldTuid,
		final String msg, final boolean equalTuid) {
		String newTuid = tuidCAR.calculateTuidFromEObject(eObject);
		traceAndAssert(oldTuid, newTuid, msg, equalTuid);
		return newTuid;
	}

	private void remove(final EObject eObject) {
		EcoreUtil.remove(eObject);
	}

	private String saveAndCalc(final String oldTuid, final TuidCalculatorAndResolver tuidCAR, final EObject eObject,
		final String fileName) throws IOException {
		String uncachedTuidAfterSave = saveAndCalculateTuid(eObject, tuidCAR, fileName);
		traceAndAssert(oldTuid, uncachedTuidAfterSave, "uncachedTuidAfterSave", false);
		return uncachedTuidAfterSave;
	}

	private Repository create() {
		Repository repository = Pcm_mockupFactory.eINSTANCE.createRepository();
		return repository;
	}

	private TuidCalculatorAndResolver prepareTuidCAR() {
		EcoreResourceBridge.registerGlobalMetamodelPackage(Pcm_mockupPackage.eNS_URI, Pcm_mockupPackage.eINSTANCE);
		EcoreResourceBridge.registerDefaultXMIExtensionFactory("pcm_mockup");
		TuidCalculatorAndResolver tuidCAR = new AttributeTuidCalculatorAndResolver(Pcm_mockupPackage.eNS_URI, "id");
		return tuidCAR;
	}

	private void traceAndAssert(final String oldTuid, final String newTuid, final String msg, final boolean equalTuid) {
		LOGGER.trace(msg + ": '" + newTuid + "'");
		if (equalTuid) {
			assertEquals(oldTuid, newTuid);
		} else {
			assertNotEquals(oldTuid, newTuid);
		}
	}

	private String saveAndCalculateTuid(final EObject eObject, final TuidCalculatorAndResolver tuidCAR,
		final String fileName) throws IOException {
		save(eObject, fileName);
		return tuidCAR.calculateTuidFromEObject(eObject);
	}

	private void save(final EObject eObject, final String fileName) throws IOException {
		URI uri = URI.createFileURI(SystemUtils.getJavaIoTmpDir().getAbsolutePath() + "/" + fileName + ".pcm_mockup");
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		Resource resource = URIUtil.loadResourceAtURI(uri, resourceSet);
		EObject root = EcoreUtil.getRootContainer(eObject);
		EcoreResourceBridge.saveEObjectAsOnlyContent(root, resource);
	}
}
