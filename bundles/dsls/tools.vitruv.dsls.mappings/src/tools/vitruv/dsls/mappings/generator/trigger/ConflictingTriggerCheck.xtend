package tools.vitruv.dsls.mappings.generator.trigger

interface ConflictingTriggerCheck {
	
	def boolean isSubordinateTrigger(AbstractReactionTypeGenerator generator)
	
}