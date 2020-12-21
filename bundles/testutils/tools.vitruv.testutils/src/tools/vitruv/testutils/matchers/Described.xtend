package tools.vitruv.testutils.matchers

interface Described {
	def void describeTo(StringBuilder builder)
	
	def String describe() {
		val target = new StringBuilder
		describeTo(target)
		target.toString
	}
}