package tools.vitruv.dsls.mappings.generator.reactions

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter

interface CorrespondingParameterConsumer {
	
	def void accept(MappingParameter reactionParameter, MappingParameter correspondingParameter)
}