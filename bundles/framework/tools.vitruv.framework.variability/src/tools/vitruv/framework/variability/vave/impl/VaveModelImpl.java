package tools.vitruv.framework.variability.vave.impl;

import java.io.IOException;
import org.eclipse.emf.ecore.resource.Resource;
import vavemodel.VavemodelFactory;
import tools.vitruv.framework.variability.vave.VaveModel;

public class VaveModelImpl implements VaveModel{
	
	public VaveModelImpl() {
	}

	public VaveModelImpl(Resource vaveResource, String systemName) { 
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
