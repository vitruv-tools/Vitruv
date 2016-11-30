package tools.vitruv.framework.vsum

import java.util.Map

final class VirtualModelManager {
	private Map<String, InternalVirtualModel> nameToVirtualModelMap;
	
	private static val instance = new VirtualModelManager();
	
	private new() {
		this.nameToVirtualModelMap = newHashMap();
	}
	
	public static def getInstance() {
		return instance;
	}
	
	public def getVirtualModel(String name) {
		return nameToVirtualModelMap.get(name);
	}
	
	public def putVirtualModel(InternalVirtualModel model) {
		nameToVirtualModelMap.put(model.name, model);
	}
}