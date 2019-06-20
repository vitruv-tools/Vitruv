package tools.vitruv.dsls.mappings.generator.reactions

import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.action.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.TaggedWithBuilder

abstract class AbstractReactionTypeGenerator {

	@Accessors(PUBLIC_GETTER)
	protected EClass metaclass
	@Accessors(#[PUBLIC_GETTER, PUBLIC_SETTER])
	private ConflictingTriggerCheck conflictingTriggerCheck
	@Accessors(PUBLIC_GETTER)
	private List<NamedMetaclassReference> reactionParameters
	@Accessors(PUBLIC_GETTER)
	private List<NamedMetaclassReference> correspondingParameters

	new(EClass metaclass) {
		this.metaclass = metaclass
	}

	def void init(List<NamedMetaclassReference> reactionParameters,
		List<NamedMetaclassReference> correspondingParameters) {
		this.reactionParameters = reactionParameters.involvedParameters
		this.correspondingParameters = correspondingParameters
	}

	private def getInvolvedParameters(List<NamedMetaclassReference> fromParameters) {
		fromParameters.filter [
			it.metaclass == this.metaclass
		].toList
	}

	def boolean isSubordinateToTrigger(AbstractReactionTypeGenerator generator) {
		if (generator.metaclass == metaclass && conflictingTriggerCheck !== null) {
			return conflictingTriggerCheck.isSubordinateTrigger(generator)
		}
		false
	}
	
	def protected taggedWith(TaggedWithBuilder builder, NamedMetaclassReference reactionParameter, NamedMetaclassReference correspondingParameter){
		builder.taggedWith(ParameterCorrespondenceTagging.getCorrespondenceTag(reactionParameter, correspondingParameter))
	}

	def protected iterateParameters(CorrespondingParameterConsumer consumer) {
		reactionParameters.forEach [ reactionParameter |
			correspondingParameters.forEach [ correspondingParameter |
				consumer.accept(reactionParameter, correspondingParameter)
			]
		]
	}
	
	def protected String getParameterName(NamedMetaclassReference p1, NamedMetaclassReference p2)'''
	«p1.parameterName»__«p2.parameterName»'''
	
	def protected String getParameterName(NamedMetaclassReference parameter)'''
	«parameter.metaclass.name»_«parameter.name»'''

	def protected getAttributeName(MetaclassFeatureReference parameter) {
		parameter.feature.name
	}

	def protected getParameterName(MetaclassReference parameter) {
		parameter.metaclass.getParameterName
	}

	def protected getParameterName(EClass clazz) {
		clazz.name
	}

	def abstract PreconditionOrRoutineCallBuilder generateTrigger(ReactionGeneratorContext context)

	def abstract void generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder)

	def abstract void generateCorrespondenceActions(ActionStatementBuilder builder)

}
