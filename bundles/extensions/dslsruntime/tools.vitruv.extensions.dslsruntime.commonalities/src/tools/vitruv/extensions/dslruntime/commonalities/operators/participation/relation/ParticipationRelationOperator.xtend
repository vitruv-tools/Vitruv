package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation

interface ParticipationRelationOperator {

	def void afterCreated() {}

	def void afterInserted() {}

	def boolean check()
}
