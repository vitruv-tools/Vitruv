package edu.kit.ipd.sdq.vitruvius.tests.infrastructure;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;
import pcm_mockup.Component;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Pcm_mockupPackage;
import pcm_mockup.Repository;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

public class TUIDCacheTest {
    private static final Logger LOGGER = Logger.getLogger(TUIDCacheTest.class.getSimpleName());
	
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
		String uncachedTUIDAfterResave = saveAndCalc(cachedTUIDAfterRemove, tuidCAR, component, "newtestresource");
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
		String uncachedTUIDAfterResave = saveAndCalc(cachedTUIDAfterRemove, tuidCAR, repository, "newtestresource");
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
		String uncachedTUIDAfterResave = saveAndCalc(cachedTUIDAfterRemove, tuidCAR, repository, "newtestresource");
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
		String uncachedTUIDAfterResave = saveAndCalc(cachedTUIDAfterRemove, tuidCAR, repository, "newtestresource");
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
		String uncachedTUIDAfterResave = saveAndCalc(cachedTUIDAfterRemove, tuidCAR, repository, "newtestresource");
	}
    
	private String calc(TUIDCalculatorAndResolver tuidCAR, EObject eObject, String oldTUID, String msg) {
		return calc(tuidCAR, eObject, oldTUID, msg, false);
	}

	private String calc(TUIDCalculatorAndResolver tuidCAR, EObject eObject, String oldTUID, String msg, boolean equalTUID) {
		String newTUID = tuidCAR.calculateTUIDFromEObject(eObject);
		traceAndAssert(oldTUID, newTUID, msg, equalTUID);
		return newTUID;
	}

	private void remove(EObject eObject) {
		EcoreUtil.remove(eObject);
	}

	private String saveAndCalc(String oldTUID, TUIDCalculatorAndResolver tuidCAR, EObject eObject, String fileName) throws IOException {
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

	private void traceAndAssert(String oldTUID, String newTUID, String msg, boolean equalTUID) {
		LOGGER.trace(msg + ": '" + newTUID + "'");
		if (equalTUID) {
		assertEquals(oldTUID, newTUID);
		} else {
			assertNotEquals(oldTUID, newTUID);		
		}
	}

	private String saveAndCalculateTUID(EObject eObject, TUIDCalculatorAndResolver tuidCAR, String fileName)
			throws IOException {
		save(eObject, fileName);
		return tuidCAR.calculateTUIDFromEObject(eObject);
	}

	private void save(EObject eObject, String fileName) throws IOException {
		URI uri = URI.createFileURI("models/" + fileName + ".pcm_mockup");
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		Resource resource = EcoreResourceBridge.loadResourceAtURI(uri, resourceSet);
		EObject root = EcoreUtil.getRootContainer(eObject);
		EcoreResourceBridge.saveEObjectAsOnlyContent(root, resource);
	}
}
