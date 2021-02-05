package tools.vitruv.framework.variability.tests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.variability.vave.VaveModel;

public class Vave2Featureide {
	
	private static final String MODEL_PATH = "models";
	private static final String VAVE_MODEL = MODEL_PATH + "/" + "vaveTest.vavemodel";

	VaveModel vavemodel = new VaveModel();
	ResourceSet resSet = new ResourceSetImpl();

	@Test
	public void createFeatureIDEModel() {
		Resource vaveRes = resSet.getResource(URI.createURI(VAVE_MODEL), true);
//		vavemodel.getFeatureModelForFeatureIDE(vaveRes);
	}

}
