package tools.vitruv.framework.variability.vave;

import java.io.IOException;
import org.eclipse.emf.ecore.resource.Resource;
import vavemodel.VavemodelFactory;

public class VaveModel {
	
	public VaveModel() {
	}

	public VaveModel(Resource vaveResource, String systemName) { // needed for vitruv integration
		loadVave(vaveResource, systemName);
	}

	private void loadVave(Resource vaveResource, String systemName) {
		try {
			vaveResource.load(null);
		} catch (IOException e) {
		}
		vavemodel.System system = VavemodelFactory.eINSTANCE.createSystem();
		vaveResource.getContents().add(system);
		system.setName(systemName);
	}

}
