package tools.vitruv.framework.vsum.impl

import java.io.File
import java.util.Map
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.vsum.VirtualModelManager

class VirtualModelManagerImpl implements VirtualModelManager {
	val Map<File, InternalVirtualModel> folderToVirtualModelMap

	static def VirtualModelManager init() {
		new VirtualModelManagerImpl
	}

	private new() {
		this.folderToVirtualModelMap = newHashMap
	}

	static def getInstance() {
		return instance
	}

	override getVirtualModel(File folder) {
		if (folderToVirtualModelMap.containsKey(folder))
			return folderToVirtualModelMap.get(folder)
		else {
//			// get the workspace root
//			val root = ResourcesPlugin::workspace.root;
//			// get the project handle
//			val project = root.getProject(name);
//			// open up this newly-created project in Eclipse
//			project.open(new NullProgressMonitor)
//			// TODO HK: Extract VSUM from project
			throw new UnsupportedOperationException
		}
	}

	override putVirtualModel(InternalVirtualModel model) {
		folderToVirtualModelMap.put(model.folder, model)
	}
}
