package tools.vitruv.dsls.mappings.generator.reactions

interface ConflictingTriggerCheck {
	
	def boolean isSubordinateTrigger(AbstractReactionTriggerGenerator generator)
	
}
