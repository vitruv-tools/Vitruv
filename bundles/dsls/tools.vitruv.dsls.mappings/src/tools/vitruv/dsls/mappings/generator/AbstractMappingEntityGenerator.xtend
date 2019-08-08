package tools.vitruv.dsls.mappings.generator

import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.reactions.CorrespondingParameterConsumer
import tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.TaggedWithBuilder

abstract class AbstractMappingEntityGenerator {

	@Accessors(PUBLIC_GETTER)
	protected List<MappingParameter> reactionParameters
	@Accessors(PUBLIC_GETTER)
	protected List<MappingParameter> correspondingParameters
	@Accessors(PUBLIC_GETTER)
	protected String mappingName

	def void init(String mappingName, List<MappingParameter> reactionParameters,
		List<MappingParameter> correspondingParameters) {
		this.mappingName = mappingName
		this.reactionParameters = reactionParameters
		this.correspondingParameters = correspondingParameters
	}
	
	def void initWith(AbstractMappingEntityGenerator entity){
		init(entity.mappingName, entity.reactionParameters, entity.correspondingParameters)
	}

	def protected iterateParameters(CorrespondingParameterConsumer consumer) {
		reactionParameters.forEach [ reactionParameter |
			correspondingParameters.forEach [ correspondingParameter |
				consumer.accept(reactionParameter, correspondingParameter)
			]
		]
	}

	def protected taggedWith(TaggedWithBuilder builder, MappingParameter reactionParameter,
		MappingParameter correspondingParameter) {
		builder.taggedWith(
			ParameterCorrespondenceTagging.getCorrespondenceTag(reactionParameter, correspondingParameter))
	}

	def protected String getParameterName(MappingParameter p1, MappingParameter p2) '''
	«p2.value.name»_correspondingTo_«p1.value.name»'''

	def protected String getParameterName(MappingParameter parameter) '''
	«parameter.value.name»_'''

	def protected getAttributeName(MetaclassEAttributeReference parameter) {
		parameter.feature.name
	}

	def protected getMetaclassName(MetaclassReference parameter) {
		parameter.metaclass.getParameterName
	}
	
	def protected getMetaclassFeatureName(MetaclassFeatureReference parameter)'''
	«parameter.metaclass.getParameterName»:«parameter.feature.name»'''
	

	def protected getParameterName(EClass clazz) {
		clazz.name
	}
}
