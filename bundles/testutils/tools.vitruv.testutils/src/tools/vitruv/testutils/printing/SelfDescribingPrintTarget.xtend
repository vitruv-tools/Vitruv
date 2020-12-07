package tools.vitruv.testutils.printing

import org.hamcrest.SelfDescribing
import org.hamcrest.Description
import org.hamcrest.StringDescription
import org.eclipse.xtend.lib.annotations.Delegate

class SelfDescribingPrintTarget implements PrintTarget, SelfDescribing {
	val StringBuilder content = new StringBuilder()
	@Delegate
	val PrintTarget delegate = new HamcrestDescriptionPrintTarget(new StringDescription(content))

	def getResult() {
		content.toString
	}

	override describeTo(Description description) {
		description.appendText(result)
	}
	
	override toString() { result }
}
