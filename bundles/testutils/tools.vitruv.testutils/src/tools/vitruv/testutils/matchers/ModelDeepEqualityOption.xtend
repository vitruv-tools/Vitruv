package tools.vitruv.testutils.matchers

interface ModelDeepEqualityOption {
	def abstract void describeTo(StringBuilder builder)
	
	def String describe() {
		val target = new StringBuilder
		describeTo(target)
		target.toString
	}
}