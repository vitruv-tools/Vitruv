package tools.vitruv.dsls.mappings.generator.integration

import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionOrientation
import java.util.HashMap
import java.util.Map
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration

class ReactionIntegrationCache {
	
	private Map<String,ReactionOrientation> cache = new HashMap<String, ReactionOrientation>();
	
	def void createdReaction(ReactionIntegration reaction){
	
	}
	
	def findOrientation(RoutineIntegration routine){
		
	}
	
}
