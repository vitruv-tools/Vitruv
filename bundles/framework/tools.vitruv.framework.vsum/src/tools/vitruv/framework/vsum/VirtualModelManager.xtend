package tools.vitruv.framework.vsum

import java.nio.file.Path
import tools.vitruv.framework.vsum.internal.VirtualModelRegistry
import tools.vitruv.framework.vsum.internal.InternalVirtualModel

final class VirtualModelManager {
	static val instance = new VirtualModelManager();

	private new() {
	}

	static def getInstance() {
		return instance;
	}

	def InternalVirtualModel getVirtualModel(Path folder) {
		var virtualModel = VirtualModelRegistry.instance.getVirtualModel(folder)
		if (virtualModel === null) {
			// get the workspace root
//			val root = ResourcesPlugin.getWorkspace().getRoot(); 
//			// get the project handle 
//			val project = root.getProject(name); 
//			// open up this newly-created project in Eclipse 
//			project.open(new NullProgressMonitor());
//			// TODO HK: Extract VSUM from project
			throw new UnsupportedOperationException("Virtual models cannot be loaded yet");
		}
		return virtualModel
	}

}
