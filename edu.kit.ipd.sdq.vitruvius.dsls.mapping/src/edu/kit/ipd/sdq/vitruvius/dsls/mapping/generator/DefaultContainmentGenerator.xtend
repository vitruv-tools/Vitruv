package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.TemplateGenerator

class DefaultContainmentGenerator {
	private extension MappingLanguageGeneratorState state
	private extension TemplateGenerator generator
	
	new(MappingLanguageGeneratorState state, TemplateGenerator generator) {
		this.state = state
		this.generator = generator
	}
	
	def generate() {
		for (mapping : state.mappings) {
			for (imp : state.getImports(mapping)) {
				appendToTemplateExpression(#[mapping, imp, 'applyEChange']) [
				'''
					// TEST
				'''
				]
			}
		}
	}
	
}