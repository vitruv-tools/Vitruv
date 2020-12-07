package tools.vitruv.testutils.printing

import java.util.List
import java.util.Set
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*

interface PrintTarget {
	def PrintResult print(String text)

	def PrintResult printValue(Object object)

	def <T> PrintResult printValue(T value, (PrintTarget, T)=>PrintResult elementPrinter) {
		print('<') + elementPrinter.apply(this, value) + print('>')
	}

	def <T> PrintResult printList(List<? extends T> elements, (PrintTarget, T)=>PrintResult elementPrinter) {
		printIterable('[', ']', elements, elementPrinter)
	}

	def <T> PrintResult printSet(Set<? extends T> elements, (PrintTarget, T)=>PrintResult elementPrinter) {
		printIterable('{', '}', elements, elementPrinter)
	}

	def <T> PrintResult printIterable(String start, String end, Iterable<? extends T> elements,
		(PrintTarget, T)=>PrintResult elementPrinter)

	def <T> PrintResult printIterableElements(Iterable<? extends T> elements,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		printIterable('', '', elements, elementPrinter)
	}
}
