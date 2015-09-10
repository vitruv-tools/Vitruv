package edu.kit.ipd.sdq.vitruvius.tests.infrastructure;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Pcm_mockupPackage;
import pcm_mockup.Repository;
import static org.junit.Assert.assertNotEquals;

public class TUIDCacheTest {
    private static final Logger LOGGER = Logger.getLogger(TUIDCacheTest.class.getSimpleName());
	
    @BeforeClass
    public static void initi() {
    	TestUtil.initializeLogger();
    }
    
    @Test
	public void testCacheCreateSaveCalcRemoveCalcSaveCalc() throws IOException {
    	EcoreResourceBridge.registerMetamodelPackages(Pcm_mockupPackage.eNS_URI, Pcm_mockupFactory.eINSTANCE);
    	EcoreResourceBridge.registerDefaultXMIExtensionFactory("pcm_mockup");
    	
		Repository repository = Pcm_mockupFactory.eINSTANCE.createRepository();
		TUIDCalculatorAndResolver tuidCAR = new DefaultTUIDCalculatorAndResolver(Pcm_mockupPackage.eNS_URI);
		String uncachedTUIDAfterSave = saveRepositoryAndCalculateTUID(repository, tuidCAR, "newtestresource");
		LOGGER.trace("uncachedTUIDAfterSave: '" + uncachedTUIDAfterSave + ".");
		
		EcoreUtil.remove(repository);
		String cachedTUIDAfterRemove = tuidCAR.calculateTUIDFromEObject(repository);
		LOGGER.trace("cachedTUIDAfterRemove: '" + cachedTUIDAfterRemove + ".");
		assertNotEquals(uncachedTUIDAfterSave, cachedTUIDAfterRemove);

		String uncachedTUIDAfterResave = saveRepositoryAndCalculateTUID(repository, tuidCAR, "newtestresource");
		LOGGER.trace("uncachedTUIDAfterResave: '" + uncachedTUIDAfterResave + ".");
		assertNotEquals(cachedTUIDAfterRemove, uncachedTUIDAfterResave);
		
	}

	public String saveRepositoryAndCalculateTUID(Repository repository, TUIDCalculatorAndResolver tuidCAR, String fileName)
			throws IOException {
		URI uri = URI.createFileURI("models/" + fileName + ".pcm_mockup");
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		Resource resource = EcoreResourceBridge.loadResourceAtURI(uri, resourceSet);
		EcoreResourceBridge.saveEObjectAsOnlyContent(repository, resource);
		return tuidCAR.calculateTUIDFromEObject(repository);
	}
}
