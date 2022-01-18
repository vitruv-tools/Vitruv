package tools.vitruv.framework.vsum.internal;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VirtualModelRegistry {
	private static VirtualModelRegistry instance = new VirtualModelRegistry();

	private Map<Path, InternalVirtualModel> folderToVirtualModelMap = new ConcurrentHashMap<>();

	private VirtualModelRegistry() {
	}

	public static VirtualModelRegistry getInstance() {
		return instance;
	}

	public InternalVirtualModel getVirtualModel(Path folder) {
		return folderToVirtualModelMap.get(folder);
	}

	public void registerVirtualModel(InternalVirtualModel model) {
		folderToVirtualModelMap.put(model.getFolder(), model);
	}

	public void deregisterVirtualModel(InternalVirtualModel model) {
		folderToVirtualModelMap.remove(model.getFolder());
	}
}
