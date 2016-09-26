package tools.vitruv.framework.vsum

import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.change.processing.Change2CommandTransforming

class VirtualModelConfigurationBuilder {
	private val VirtualModelConfiguration modelConfiguration;
	
	public new() {
		this.modelConfiguration = new VirtualModelConfiguration();	
	}
	
	public def VirtualModelConfigurationBuilder addMetamodel(Metamodel metamodel) {
		modelConfiguration.addMetamodel(metamodel);
		return this;
	}
	
	public def VirtualModelConfigurationBuilder addChange2CommandTransforming(Change2CommandTransforming transforming) {
		modelConfiguration.addChange2CommandTransforming(transforming);
		return this;
	}

	// TODO HK Generate here to avoid that the configuration gets modified by further calls to the builder afterwards	
	public def VirtualModelConfiguration toConfiguration() {
		return modelConfiguration;
	}
}
