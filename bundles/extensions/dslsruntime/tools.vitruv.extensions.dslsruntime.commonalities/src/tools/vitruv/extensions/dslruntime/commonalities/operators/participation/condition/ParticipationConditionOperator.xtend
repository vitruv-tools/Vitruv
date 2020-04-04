package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

interface ParticipationConditionOperator {

	def void enforce()

	def boolean check()
}
