package tools.vitruv.dsls.mappings.generator.integration

import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine

interface IReactionIntegrateable {
	
	def void integrate(Reaction reaction)
	def void integrate(Routine routine)
	
}