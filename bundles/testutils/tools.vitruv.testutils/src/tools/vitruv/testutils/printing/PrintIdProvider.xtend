package tools.vitruv.testutils.printing

interface PrintIdProvider {
	def String getFallbackId(Object object)
}
