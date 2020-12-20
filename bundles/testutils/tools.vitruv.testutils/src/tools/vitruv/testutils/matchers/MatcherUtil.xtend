package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
package class MatcherUtil {
	def package static a(String string) {
		(if (#['a', 'e', 'i', 'o'].contains(string.substring(0, 1).toLowerCase)) "an " else "a ") + string
	}
}
