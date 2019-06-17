package tools.vitruv.dsls.mappings.generator.trigger

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder

abstract class AbstractReactionTypeGenerator<T> {

	def protected getParameterName(MetaclassReference parameter){
		parameter.metaclass.name
	}

	def abstract PreconditionOrRoutineCallBuilder generate(ReactionGeneratorContext context,MetaclassReference parameter, T trigger)
}
