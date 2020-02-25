package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

interface ParticipationConditionOperator {

	def boolean check()

	def void enforce()
}
