package tools.vitruv.change.testutils.printing

interface PrintIdProvider {
	def String getFallbackId(Object object)
}
