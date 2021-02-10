package tools.vitruv.framework.variability.vave

import tools.vitruv.framework.variability.vave.impl.VaveModelImpl

final class VaveModelFactory {
	static VaveModelFactory instance;
	
	private new() {	}
	
	static def getInstance() {
		if (instance === null) {
			instance = new VaveModelFactory();
		}
		
		return instance;
	}
	
	def createVaveModel() {
		return new VaveModelImpl();
	}
}