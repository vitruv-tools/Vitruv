package tools.vitruv.testutils.printing

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.Accessors

@FinalFieldsConstructor
@Accessors
class PrintMode {
	public static val SINGLE_LINE = multiLineIfAtLeast(Integer.MAX_VALUE)
	public static val MULTI_LINE = multiLineIfAtLeast(1)
	
	val int multiLineIfAtLeastItemCount
	
	def static multiLineIfAtLeast(int count) { new PrintMode(count) }
}
