package tools.vitruv.framework.tuid

interface TuidUpdateListener {
	def void performPreAction(Tuid oldTuid)
	def void performPostAction(Tuid newTuid)
}