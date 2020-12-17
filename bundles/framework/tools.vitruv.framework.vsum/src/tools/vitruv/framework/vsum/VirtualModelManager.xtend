package tools.vitruv.framework.vsum

import java.util.Map
import java.io.File
import java.util.concurrent.ConcurrentHashMap

final class VirtualModelManager {
	Map<File, InternalVirtualModel> folderToVirtualModelMap;
	
	static val instance = new VirtualModelManager();
	
	private new() {
		this.folderToVirtualModelMap = new ConcurrentHashMap();
	}
	
	static def getInstance() {
		return instance;
	}
	
	def getVirtualModel(File folder) {
		folderToVirtualModelMap.computeIfAbsent(folder) [
			// get the workspace root
//			val root = ResourcesPlugin.getWorkspace().getRoot(); 
//			// get the project handle 
//			val project = root.getProject(name); 
//			// open up this newly-created project in Eclipse 
//			project.open(new NullProgressMonitor());
//			// TODO HK: Extract VSUM from project
			throw new UnsupportedOperationException();
		]
	}
	
	def putVirtualModel(InternalVirtualModel model) {
		folderToVirtualModelMap.put(model.folder, model);
	}
}