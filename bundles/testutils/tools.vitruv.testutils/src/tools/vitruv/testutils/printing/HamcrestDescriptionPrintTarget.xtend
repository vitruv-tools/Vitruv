package tools.vitruv.testutils.printing

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import static tools.vitruv.testutils.printing.PrintResult.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*

@FinalFieldsConstructor
class HamcrestDescriptionPrintTarget implements PrintTarget {
	val Description description

	override print(String text) {
		return if (text.isEmpty) {
			PRINTED_NO_OUTPUT
		} else {
			description.appendText(text)
			PRINTED
		}
	}

	override <T> printIterable(String start, String end, Iterable<? extends T> elements,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		val printResults = elements.map [ element |
			val target = new SelfDescribingPrintTarget
			target -> elementPrinter.apply(target, element)
		].toList
		return switch (printResults.map[value].fold(PRINTED_NO_OUTPUT)[$0 + $1]) {
			case NOT_RESPONSIBLE:
				NOT_RESPONSIBLE
			case PRINTED,
			case PRINTED_NO_OUTPUT: {
				description.appendList(start, ', ', end, printResults.filter[value == PRINTED].map[key])
				PRINTED
			}
		}
	}

	override printValue(Object object) {
		description.appendValue(object)
		return PRINTED
	}

	override toString() {
		description.toString()
	}
}
