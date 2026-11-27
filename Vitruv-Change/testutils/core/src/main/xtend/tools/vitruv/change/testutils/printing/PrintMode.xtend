package tools.vitruv.change.testutils.printing

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.Accessors

@FinalFieldsConstructor
@Accessors
class PrintMode {
	public static val SINGLE_LINE_LIST = multiLineIfAtLeast(Integer.MAX_VALUE).withSeparator(", ")
	public static val MULTI_LINE_LIST = multiLineIfAtLeast(1).withSeparator(",")
	
	val int multiLineIfAtLeastItemCount
	val String separator
	
	def static multiLineIfAtLeast(int count) { new Builder(count) }
	
	@FinalFieldsConstructor
	static class Builder {
		val int multiLineIfAtLeastItemCount
		
		def withSeparator(String separator) {
			new PrintMode(multiLineIfAtLeastItemCount, separator)
		}
	}
}
