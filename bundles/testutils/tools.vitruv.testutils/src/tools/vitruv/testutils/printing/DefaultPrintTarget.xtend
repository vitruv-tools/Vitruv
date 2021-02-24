package tools.vitruv.testutils.printing

import static tools.vitruv.testutils.printing.PrintResult.*
import java.util.ArrayList
import static com.google.common.base.Preconditions.checkState
import java.util.Collection
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*

class DefaultPrintTarget implements PrintTarget {
	// empty as long as we have not printed anything,
	// populated with a builder for each line while in use,
	// null if we got incorporated into another target (which modifies the builders, so no more interaction is allowed)
	var lines = new ArrayList<StringBuilder>()
	var indent = ''
	
	override print(String text) {
		checkIsOpen()
		
		if (text.isEmpty) PRINTED_NO_OUTPUT
		else {
			if (lines.isEmpty) lines += new StringBuilder(indent)
			lines.get(lines.size - 1).append(text)
			PRINTED
		}
	}
	
	def private print(DefaultPrintTarget otherTarget) {
		if (otherTarget.lines.isEmpty) {
			PRINTED_NO_OUTPUT
		} else {
			val otherLines = otherTarget.lines.iterator
			if (!lines.isEmpty) {
				lines.get(lines.size - 1).append(otherLines.next)
			}
			while (otherLines.hasNext) {
				lines += otherLines.next.insert(0, indent)
			}
			otherTarget.lines = null
			PRINTED
		}
	}
	
	override <T> printIterable(String start, String end, Iterable<? extends T> elements, PrintMode mode,
		(PrintTarget, T)=>PrintResult elementPrinter) {
		checkIsOpen()
		var preprinted = if (elements instanceof Collection<?>) new ArrayList(elements.size) else new ArrayList()
		for (val elementsIterator = elements.iterator; elementsIterator.hasNext;) {
			val element = elementsIterator.next
			val subTarget = new DefaultPrintTarget
			switch (elementPrinter.apply(subTarget, element)) {
				case NOT_RESPONSIBLE: {
					if (preprinted.isEmpty) return NOT_RESPONSIBLE
					else throw new IllegalStateException('''Got NOT_RESPONSIBLE for ‹«element»› after already having printed something!''')
				}
				case PRINTED: preprinted += subTarget
				case PRINTED_NO_OUTPUT: {/* ignore */}
			}
		}
		
		return if (preprinted.isEmpty) print(start) + print(end)
		else if (preprinted.size >= mode.multiLineIfAtLeastItemCount)
		 	printMultiLine(start, end, mode.separator, preprinted)
		else printSingleLine(start, end, mode.separator, preprinted)
	}

	def private <T> printSingleLine(String start, String end, String separator, Collection<DefaultPrintTarget> preprinted) {
		var result = print(start)
		for (val outputs = preprinted.iterator; outputs.hasNext;) {
			result += print(outputs.next)
			if (outputs.hasNext) {
				result += print(separator)
			}
		}
		return result + print(end)
	}

	def private <T> printMultiLine(String start, String end, String separator, Collection<DefaultPrintTarget> preprinted) {
		var result = print(start) + newLineIncreaseIndent()
		for (val outputs = preprinted.iterator; outputs.hasNext;) {
			result += print(outputs.next)
			if (outputs.hasNext) {
				result += print(separator) + newLine()
			}
		}
		return result + newLineDecreaseIndent() + print(end)
	}

	
	override newLine() {
		checkIsOpen()
		if (lines.isEmpty) lines += new StringBuilder(0)
		lines += new StringBuilder(indent)
		PRINTED
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
		indent += '        '
	}

	def private decreaseIndent() {
		checkState(indent.length > 0, 'The indentation is already 0!')
		indent = indent.substring(8)
	}
	
	def private checkIsOpen() {
		if (lines === null) {
			throw new IllegalStateException("This printer has already been incorporated into another printer and " + 
				"must not be used anymore!")
		}
	}
	
	override toString() {
		checkIsOpen()
		return if (lines.isEmpty) ''
		else if (lines.size === 1) lines.get(0).toString
		else {
			var resultLength = 0
			for (line: lines) resultLength += line.length
			resultLength += (lines.size - 1) * System.lineSeparator.length
			
			val result = new StringBuilder(resultLength)
			for (val linesIt = lines.iterator; linesIt.hasNext;) {
				result.append(linesIt.next)
				if (linesIt.hasNext) result.append(System.lineSeparator)
			} 
			result.toString()
		}
	}
}