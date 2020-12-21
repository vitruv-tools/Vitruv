package tools.vitruv.testutils.printing

import org.eclipse.xtend.lib.annotations.Accessors
import org.hamcrest.Description
import java.util.List
import java.util.Set
import org.hamcrest.Description.NullDescription
import tools.vitruv.testutils.printing.PrintMode
import static com.google.common.base.Preconditions.checkState

final class ModelPrinting {
	private new() {
	}

	@Accessors
	static var ModelPrinter printer = new DefaultModelPrinter()

	def static appendModelValue(Description description, Object object, PrintIdProvider idProvider) {
		description.applyAsPrintTarget [
			printValue(object) [ target, element |
				printer.printObject(target, idProvider, element)
			]
		]
	}

	def static appendModelValueList(Description description, List<?> objects, PrintMode mode,
		PrintIdProvider idProvider) {
		description.applyAsPrintTarget [
			printList(objects, mode) [ target, element |
				printer.printObject(target, idProvider, element)
			]
		]
	}

	def static appendModelValueSet(Description description, Set<?> objects, PrintMode mode,
		PrintIdProvider idProvider) {
		description.applyAsPrintTarget [
			printSet(objects, mode) [ target, element |
				printer.printObject(target, idProvider, element)
			]
		]
	}

	def static appendShortenedModelValue(Description description, Object object, PrintIdProvider idProvider) {
		description.applyAsPrintTarget [
			printValue(object) [ target, element |
				printer.printObjectShortened(target, idProvider, element)
			]
		]
	}

	def static appendShortenedModelValueList(Description description, List<?> objects, PrintMode mode,
		PrintIdProvider idProvider) {
		description.applyAsPrintTarget [
			printList(objects, mode) [ target, element |
				printer.printObjectShortened(target, idProvider, element)
			]
		]
	}

	def static appendShortenedModelValueSet(Description description, Set<?> objects, PrintMode mode,
		PrintIdProvider idProvider) {
		description.applyAsPrintTarget [
			printSet(objects, mode) [ target, element |
				printer.printObjectShortened(target, idProvider, element)
			]
		]
	}

	def static appendModelValue(Description description, Object object) {
		appendModelValue(description, object, new PrintIdProvider)
	}

	def static appendModelValueList(Description description, List<?> objects, PrintMode mode) {
		appendModelValueList(description, objects, mode, new PrintIdProvider)
	}

	def static appendModelValueSet(Description description, Set<?> objects, PrintMode mode) {
		appendModelValueSet(description, objects, mode, new PrintIdProvider)
	}

	def static appendShortenedModelValue(Description description, Object object) {
		appendShortenedModelValue(description, object, new PrintIdProvider)
	}

	def static appendShortenedModelValueList(Description description, List<?> objects, PrintMode mode) {
		appendShortenedModelValueList(description, objects, mode, new PrintIdProvider)
	}

	def static appendShortenedModelValueSet(Description description, Set<?> objects, PrintMode mode) {
		appendShortenedModelValueSet(description, objects, mode, new PrintIdProvider)
	}

	def private static Description applyAsPrintTarget(Description description, (PrintTarget)=>PrintResult block) {
		// printing can be expensive, so avoid it if the result will not be used anyway
		if (description instanceof NullDescription) return description

		assertResponsible(block.apply(new HamcrestDescriptionPrintTarget(description)))
		return description
	}
	
	def private static assertResponsible(PrintResult result) {
		checkState(result != PrintResult.NOT_RESPONSIBLE, '''The current printer is not responsible for printing the provided content! Please make sure that you have set up an appropriate printer!''')
		result
	}
}
