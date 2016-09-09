package tools.vitruv.framework.tuid

interface TuidUpdateListener {
	def void performPreAction(TUID oldTUID)
	def void performPostAction(TUID newTuid)
}