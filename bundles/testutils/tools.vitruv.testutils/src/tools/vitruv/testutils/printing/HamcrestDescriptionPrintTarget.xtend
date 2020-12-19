package tools.vitruv.testutils.printing

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import static tools.vitruv.testutils.printing.PrintResult.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import static com.google.common.base.Preconditions.checkState

@FinalFieldsConstructor
class HamcrestDescriptionPrintTarget implements PrintTarget {
	val Description description
	val String linePrefix
	var indent = ''

	new(Description description) {
		this(description, '')
	}

	override print(String text) {
		return if (text.isEmpty) {
			PRINTED_NO_OUTPUT
		} else {
			description.appendText(text)
			PRINTED
		}
	}

	override <T> printIterable(String start, String end, Iterable<? extends T> elements, PrintMode mode,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		switch (mode) {
			case MULTI_LINE: printIterableMultiLine(start, end, elements, elementPrinter)
			case SINGLE_LINE: printIterableSingleLine(start, end, elements, elementPrinter)
		}
	}

	def private <T> printIterableSingleLine(String start, String end, Iterable<? extends T> elements,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		val printResults = printElements(elements, elementPrinter)

		return switch (effectiveResult(printResults)) {
			case NOT_RESPONSIBLE:
				NOT_RESPONSIBLE
			case PRINTED: {
				description.appendList(start, ', ', end, printResults.filter[value == PRINTED].map[key])
				PRINTED
			}
			case PRINTED_NO_OUTPUT:
				print(start) + print(end)
		}
	}

	def private <T> printIterableMultiLine(String start, String end, Iterable<? extends T> elements,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		increaseIndent()
		val printResults = printElements(elements) [ target, element |
			// swallow result of the new line on purpose, so that the whole printer is discarded if the
			// elementPrinter prints nothing
			target.newLine()
			elementPrinter.apply(target, element)
		]
		decreaseIndent()

		return switch (effectiveResult(printResults)) {
			case NOT_RESPONSIBLE:
				NOT_RESPONSIBLE
			case PRINTED: {
				description.appendList(start, ',', '', printResults.filter[value == PRINTED].map[key])
				newLine()
				print(end)
				PRINTED
			}
			case PRINTED_NO_OUTPUT:
				print(start) + print(end)
		}
	}

	def private <T> printElements(Iterable<? extends T> elements, (PrintTarget, T)=>PrintResult elementPrinter) {
		elements.map [ element |
			val target = new SelfDescribingPrintTarget(linePrefix + indent)
			target -> elementPrinter.apply(target, element)
		].toList
	}

	def private effectiveResult(Iterable<? extends Pair<? extends PrintTarget, PrintResult>> printResults) {
		printResults.map[value].fold(PRINTED_NO_OUTPUT)[$0 + $1]
	}

	override printValue(Object object) {
		description.appendValue(object)
		return PRINTED
	}

	override toString() {
		description.toString()
	}

	override newLine() {
		print(System.lineSeparator) + print(linePrefix) + print(indent)
	}

	override newLineIncreaseIndent() {
		increaseIndent()
		newLine()
	}

	override newLineDecreaseIndent() {
		decreaseIndent()
		newLine()
	}

	def private increaseIndent() {
		indent = '        ' + indent
	}

	def private decreaseIndent() {
		checkState(indent.length > 0, '''The indentation is already 0!''')
		indent = indent.substring(8)
	}
}
