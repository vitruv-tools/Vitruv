package tools.vitruv.framework.vsum.impl

import java.util.Map
import java.io.File
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.vsum.VirtualModelManager

class VirtualModelManagerImpl implements VirtualModelManager {
	private Map<File, InternalVirtualModel> folderToVirtualModelMap;

	static def VirtualModelManager init() {
		new VirtualModelManagerImpl
	}

	private new() {
		this.folderToVirtualModelMap = newHashMap();
	}

	public static def getInstance() {
		return instance;
	}

	public override getVirtualModel(File folder) {
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

	public override putVirtualModel(InternalVirtualModel model) {
		folderToVirtualModelMap.put(model.folder, model);
	}
}
