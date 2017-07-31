package tools.vitruv.framework.vsum

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

class VirtualModelConfigurationBuilder {
	val VirtualModelConfiguration modelConfiguration

	new() {
		this.modelConfiguration = new VirtualModelConfiguration;
	}

	def VirtualModelConfigurationBuilder addMetamodel(VitruvDomain metamodel) {
		modelConfiguration.addMetamodel(metamodel)
		return this
	}

	def VirtualModelConfigurationBuilder addChangePropagationSpecification(
		ChangePropagationSpecification changePropagationSpecification) {
		modelConfiguration.addChangePropagationSpecification(changePropagationSpecification)
		return this
	}

	// TODO HK Generate here to avoid that the configuration gets modified by further calls to the builder afterwards
	def VirtualModelConfiguration toConfiguration() {
		modelConfiguration
	}
}
