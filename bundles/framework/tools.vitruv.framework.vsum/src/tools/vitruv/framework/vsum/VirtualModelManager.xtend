package tools.vitruv.framework.vsum

import java.util.Map
import java.io.File

final class VirtualModelManager {
	Map<File, InternalVirtualModel> folderToVirtualModelMap;
	
	static val instance = new VirtualModelManager();
	
	private new() {
		this.folderToVirtualModelMap = newHashMap();
	}
	
	static def getInstance() {
		return instance;
	}
	
	def getVirtualModel(File folder) {
		if (folderToVirtualModelMap.containsKey(folder)) {
			return folderToVirtualModelMap.get(folder);
		} else {
//			// get the workspace root 
//			val root = ResourcesPlugin.getWorkspace().getRoot(); 
//			// get the project handle 
//			val project = root.getProject(name); 
//			// open up this newly-created project in Eclipse 
//			project.open(new NullProgressMonitor());
//			// TODO HK: Extract VSUM from project
			throw new UnsupportedOperationException();
		}
	}
	
	def putVirtualModel(InternalVirtualModel model) {
		folderToVirtualModelMap.put(model.folder, model);
	}
}