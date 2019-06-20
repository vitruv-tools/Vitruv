package tools.vitruv.dsls.mappings.generator.reactions

import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference

interface CorrespondingParameterConsumer {
	
	def void accept(NamedMetaclassReference reactionParameter, NamedMetaclassReference correspondingParameter)
}