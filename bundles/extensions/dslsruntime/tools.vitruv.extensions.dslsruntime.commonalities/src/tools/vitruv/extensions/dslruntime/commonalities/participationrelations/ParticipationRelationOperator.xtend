package tools.vitruv.extensions.dslruntime.commonalities.participationrelations

interface ParticipationRelationOperator {
	def void afterCreated(){}
	def void afterInserted(){}
	def boolean check()
}	
