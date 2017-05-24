package tools.vitruv.framework.vsum

import java.util.Map
import java.io.File

final class VirtualModelManager {
	private Map<File, InternalVirtualModel> folderToVirtualModelMap;
	
	private static val instance = new VirtualModelManager();
	
	private new() {
		this.folderToVirtualModelMap = newHashMap();
	}
	
	public static def getInstance() {
		return instance;
	}
	
	public def getVirtualModel(File folder) {
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
	
	public def putVirtualModel(InternalVirtualModel model) {
		folderToVirtualModelMap.put(model.folder, model);
	}
}