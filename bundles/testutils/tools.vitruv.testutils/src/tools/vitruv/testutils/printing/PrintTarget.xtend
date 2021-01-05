package tools.vitruv.testutils.printing

import static extension tools.vitruv.testutils.printing.PrintResultExtension.*

interface PrintTarget {
	def PrintResult print(String text)

	def PrintResult newLine()

	def PrintResult newLineIncreaseIndent()

	def PrintResult newLineDecreaseIndent()

	def <T> PrintResult printIterable(String start, String end, Iterable<? extends T> elements, PrintMode mode,
		(PrintTarget, T)=>PrintResult elementPrinter)

	def <T> PrintResult printList(Iterable<? extends T> elements, PrintMode mode,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		printIterable('[', ']', elements, mode, elementPrinter)
	}

	def <T> PrintResult printSet(Iterable<? extends T> elements, PrintMode mode,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		printIterable('{', '}', elements, mode, elementPrinter)
	}

	def <T> PrintResult printIterableElements(Iterable<? extends T> elements, PrintMode mode,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		printIterable('', '', elements, mode, elementPrinter)
	}
	
	def <T> PrintResult printValue(T value, (PrintTarget, T)=>PrintResult valuePrinter) {
		switch (value) {
			Number:
				valuePrinter.apply(this, value)
			String:
				print('"') + valuePrinter.apply(this, value) + print('"')
			default:
				print('<') + valuePrinter.apply(this, value) + print('>')
		}
	}
}
