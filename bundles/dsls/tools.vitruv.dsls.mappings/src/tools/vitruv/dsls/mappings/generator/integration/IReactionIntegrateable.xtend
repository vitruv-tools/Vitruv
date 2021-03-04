package tools.vitruv.dsls.mappings.generator.integration

import tools.vitruv.dsls.reactions.language.toplevelelements.Reaction
import tools.vitruv.dsls.reactions.language.toplevelelements.Routine

interface IReactionIntegrateable {
	
	def void integrate(Reaction reaction)
	def void integrate(Routine routine)
	
}