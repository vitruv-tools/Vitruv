package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

interface IParticipationConditionOperator {
	def void enforce()

	def boolean check()
}
