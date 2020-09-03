package tools.vitruv.dsls.mappings.generator.utils

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext

class ParameterCorrespondenceTagging {

	static MappingGeneratorContext context
	
	/**
	 * Sets the current generator context
	 */
	static def setContext(MappingGeneratorContext context) {
		ParameterCorrespondenceTagging.context = context
	}

	/**
	 * Returns the correspondence tag between two mapping parameters
	 */
	static def String getCorrespondenceTag(MappingParameter reactionParameter,
		MappingParameter correspondingParameter) {
		if (!context.left2right) {
			return createCorrespondenceTag(correspondingParameter.value, reactionParameter.value)
		}
		createCorrespondenceTag(reactionParameter.value, correspondingParameter.value)
	}

	private static def String createCorrespondenceTag(NamedMetaclassReference reactionParameter,
		NamedMetaclassReference correspondingParameter) '''
	«createMappingContext»_correspondence_«reactionParameter.createParameter»_with_«correspondingParameter.createParameter»'''

	private static def String createParameter(NamedMetaclassReference parameter) '''
	«parameter.metaclass.name»:«parameter.name»'''

	private static def String createMappingContext() '''
	«context.segment.name»_map_«context.segment.leftDomain.domain»_and_«context.segment.rightDomain.domain»'''
}
