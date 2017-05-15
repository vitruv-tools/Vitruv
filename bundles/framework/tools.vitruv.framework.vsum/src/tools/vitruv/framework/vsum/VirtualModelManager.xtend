package tools.vitruv.framework.vsum

import java.util.Map
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.NullProgressMonitor

final class VirtualModelManager {
	private Map<String, InternalVirtualModel> nameToVirtualModelMap

	private static val instance = new VirtualModelManager

	private new() {
		nameToVirtualModelMap = newHashMap
	}

	public static def getInstance() {
		instance
	}

	public def getVirtualModel(String name) {
		if (nameToVirtualModelMap.containsKey(name)) {
			nameToVirtualModelMap.get(name)
		} else {
			// get the workspace root 
			val root = ResourcesPlugin::workspace.root
			// get the project handle 
			val project = root.getProject(name)
			// open up this newly-created project in Eclipse 
			project.open(new NullProgressMonitor)
			// TODO HK: Extract VSUM from project
			throw new UnsupportedOperationException
		}
	}

	public def putVirtualModel(InternalVirtualModel model) {
		nameToVirtualModelMap.put(model.name, model)
	}
}
