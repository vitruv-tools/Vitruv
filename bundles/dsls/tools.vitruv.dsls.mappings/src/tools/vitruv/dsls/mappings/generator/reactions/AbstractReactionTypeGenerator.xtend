package tools.vitruv.dsls.mappings.generator.reactions

import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.TaggedWithBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

abstract class AbstractReactionTypeGenerator {

	@Accessors(PUBLIC_GETTER)
	protected EClass metaclass
	@Accessors(#[PUBLIC_GETTER, PUBLIC_SETTER])
	private ConflictingTriggerCheck conflictingTriggerCheck
	@Accessors(PUBLIC_GETTER)
	private List<MappingParameter> reactionParameters
	@Accessors(PUBLIC_GETTER)
	private List<MappingParameter> correspondingParameters
	@Accessors(PUBLIC_SETTER)
	private String mappingName

	protected String reactionName

	new(EClass metaclass) {
		this.metaclass = metaclass
	}

	def void init(List<MappingParameter> reactionParameters, List<MappingParameter> correspondingParameters) {
		this.reactionParameters = reactionParameters.involvedParameters
		this.correspondingParameters = correspondingParameters
	}

	private def getInvolvedParameters(List<MappingParameter> fromParameters) {
		fromParameters.filter [
			it.value.metaclass == this.metaclass
		].toList
	}

	def boolean isSubordinateToTrigger(AbstractReactionTypeGenerator generator) {
		if (generator.metaclass == metaclass && conflictingTriggerCheck !== null) {
			return conflictingTriggerCheck.isSubordinateTrigger(generator)
		}
		false
	}

	def protected taggedWith(TaggedWithBuilder builder, MappingParameter reactionParameter,
		MappingParameter correspondingParameter) {
		builder.taggedWith(
			ParameterCorrespondenceTagging.getCorrespondenceTag(reactionParameter, correspondingParameter))
	}

	def protected iterateParameters(CorrespondingParameterConsumer consumer) {
		reactionParameters.forEach [ reactionParameter |
			correspondingParameters.forEach [ correspondingParameter |
				consumer.accept(reactionParameter, correspondingParameter)
			]
		]
	}

	def public String reactionName() '''
	On«mappingName.toFirstUpper»«reactionName»'''

	def protected String getRemoveElementName(NamedMetaclassReference ref) '''
	remove«ref.parameterName.toFirstUpper»'''

	def protected String getNewElementName(MappingParameter ref) '''
	new«ref.parameterName.toFirstUpper»'''

	def protected String getParameterName(MappingParameter p1, MappingParameter p2) '''
	«p1.value.parameterName»__«p2.value.parameterName»'''

	def protected String getParameterName(MappingParameter parameter) '''
	«parameter.value.metaclass.name»_«parameter.value.name»'''

	def protected getAttributeName(MetaclassFeatureReference parameter) {
		parameter.feature.name
	}

	def protected getAttributeName(MetaclassEAttributeReference parameter) {
		parameter.feature.name
	}

	def protected getParameterName(MetaclassReference parameter) {
		parameter.metaclass.getParameterName
	}

	def protected getParameterName(EClass clazz) {
		clazz.name
	}

	def abstract PreconditionOrRoutineCallBuilder generateTrigger(ReactionGeneratorContext context)

	def abstract void generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder,
		MappingParameter parameter)

	def abstract void generateCorrespondenceActions(ActionStatementBuilder builder, MappingParameter parameter)

}
