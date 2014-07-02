package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

public class Models {
	private static Logger LOGGER = Logger.getLogger(Models.class);
	

	public static final String ROOT_OBJECT_URI = "/";

	
	static {
		LOGGER.setLevel(Level.ALL);
	}
	
	public static Resource loadModel(URL modelURL) {
		EcoreResourceFactoryImpl ecoreResFact = new EcoreResourceFactoryImpl();
		URI fileName = URI.createFileURI(modelURL.getFile());
		LOGGER.info("Trying to load " + fileName);
		Resource ecoreRes = ecoreResFact.createResource(fileName);
		try {
			ecoreRes.load(null);
		} catch (IOException e) {
			fail("Could not load " + Files.EXAMPLEMODEL_ECORE.getFile() + ". Reason: " + e);
		}
		return ecoreRes;
	}
	
	public static void unloadModel(Resource res) {
		LOGGER.info("Unloading model " + res.getURI().toFileString());
		res.unload();
	}
}
