package tools.vitruv.testutils.printing

import org.eclipse.xtend.lib.annotations.Accessors
import org.hamcrest.Description
import java.util.List
import java.util.Set
import org.hamcrest.Description.NullDescription
import tools.vitruv.testutils.printing.PrintMode
import static com.google.common.base.Preconditions.checkState
import static tools.vitruv.testutils.printing.PrintResult.NOT_RESPONSIBLE

final class ModelPrinting {
	private new() {}

	@Accessors(PUBLIC_GETTER)
	static var ModelPrinter printer = new DefaultModelPrinter()

	def static appendModelValue(Description description, Object object, PrintIdProvider idProvider) {
		description.appendPrintResult [
			printValue(object) [ subTarget, theObject |
				printer.printObject(subTarget, idProvider, theObject)
			]
		]
	}

	def static appendModelValueList(Description description, List<?> objects, PrintMode mode,
		PrintIdProvider idProvider) {
		description.appendPrintResult [
			printList(objects, mode) [ target, element |
				printer.printObject(target, idProvider, element)
			]
		]
	}

	def static appendModelValueSet(Description description, Set<?> objects, PrintMode mode,
		PrintIdProvider idProvider) {
		description.appendPrintResult [
			printSet(objects, mode) [ target, element |
				printer.printObject(target, idProvider, element)
			]
		]
	}

	def static appendShortenedModelValue(Description description, Object object, PrintIdProvider idProvider) {
		description.appendPrintResult [
			printValue(object) [ subTarget, theObject |
				printer.printObjectShortened(subTarget, idProvider, theObject)
			]
		]
	}

	def static appendShortenedModelValueList(Description description, List<?> objects, PrintMode mode,
		PrintIdProvider idProvider) {
		description.appendPrintResult [
			printList(objects, mode) [ target, element |
				printer.printObjectShortened(target, idProvider, element)
			]
		]
	}

	def static appendShortenedModelValueSet(Description description, Set<?> objects, PrintMode mode,
		PrintIdProvider idProvider) {
		description.appendPrintResult [
			printSet(objects, mode) [ target, element |
				printer.printObjectShortened(target, idProvider, element)
			]
		]
	}

	def static appendModelValue(Description description, Object object) {
		appendModelValue(description, object, new DefaultPrintIdProvider)
	}

	def static appendModelValueList(Description description, List<?> objects, PrintMode mode) {
		appendModelValueList(description, objects, mode, new DefaultPrintIdProvider)
	}

	def static appendModelValueSet(Description description, Set<?> objects, PrintMode mode) {
		appendModelValueSet(description, objects, mode, new DefaultPrintIdProvider)
	}

	def static appendShortenedModelValue(Description description, Object object) {
		appendShortenedModelValue(description, object, new DefaultPrintIdProvider)
	}

	def static appendShortenedModelValueList(Description description, List<?> objects, PrintMode mode) {
		appendShortenedModelValueList(description, objects, mode, new DefaultPrintIdProvider)
	}

	def static appendShortenedModelValueSet(Description description, Set<?> objects, PrintMode mode) {
		appendShortenedModelValueSet(description, objects, mode, new DefaultPrintIdProvider)
	}

	/**
	 * Makes model printing use the printer provided by {@code printerProvider}. Replaces the current printer.
	 * 
	 * @param printerProvider function that will receive the current model printer and returns the new printer to use.
	 * @return A closeable that, when closed, will revert the printer change. 
	 */
	def static AutoCloseable use((ModelPrinter)=>ModelPrinter printerProvider) {
		val oldPrinter = ModelPrinting.printer
		ModelPrinting.printer = printerProvider.apply(oldPrinter)
		return [ModelPrinting.printer = oldPrinter]
	}
	
	/**
	 * Makes model printing use the provided printers. Installs a printer that will try the provided printers one after
	 * the other until a responsible printer is found, and fall back to the currently installed printer none of the
	 * provided printers are responsible.
	 * 
	 * @return A closeable that, when closed, will revert the printer change. 
	 */
	def static AutoCloseable prepend(ModelPrinter... printers) {
		use [currentPrinter | new CombinedModelPrinter(printers, currentPrinter)]
	}

	def static Description appendPrintResult(Description description, (PrintTarget)=>PrintResult block) {
		// printing can be expensive, so avoid it if the result will not be used anyway
		if (description instanceof NullDescription) return description

		val printTarget = new DefaultPrintTarget()
		assertResponsible(block.apply(printTarget))
		description.appendText(printTarget.toString())
		return description
	}

	def private static assertResponsible(PrintResult result) {
		checkState(
			result != NOT_RESPONSIBLE,
			'''The current printer is not responsible for printing the provided content! Please make sure that you have set up an appropriate printer!'''
		)
		result
	}
}
