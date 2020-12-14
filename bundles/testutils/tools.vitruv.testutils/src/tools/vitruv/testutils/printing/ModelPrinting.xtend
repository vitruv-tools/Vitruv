package tools.vitruv.testutils.printing

import org.eclipse.xtend.lib.annotations.Accessors
import org.hamcrest.Description
import java.util.List
import java.util.Set
import org.hamcrest.Description.NullDescription

final class ModelPrinting {
	private new() {
	}

	@Accessors
	static var ModelPrinter printer = new DefaultModelPrinter()

	def static appendModelValue(Description description, Object object, PrintIdProvider idProvider) {
		description.applyAsPrintTargetUsingIds(idProvider)[printValue(object)[printer.printObject($0, $1)]]
	}

	def static appendModelValueList(Description description, List<?> objects, PrintIdProvider idProvider) {
		description.applyAsPrintTargetUsingIds(idProvider)[printList(objects)[printer.printObject($0, $1)]]
	}

	def static appendModelValueSet(Description description, Set<?> objects, PrintIdProvider idProvider) {
		description.applyAsPrintTargetUsingIds(idProvider)[printSet(objects)[printer.printObject($0, $1)]]
	}

	def static appendModelValue(Description description, Object object) {
		appendModelValue(description, object, new PrintIdProvider)
	}

	def static appendModelValueList(Description description, List<?> objects) {
		appendModelValueList(description, objects, new PrintIdProvider)
	}

	def static appendModelValueSet(Description description, Set<?> objects) {
		appendModelValueSet(description, objects, new PrintIdProvider)
	}

	def private static Description applyAsPrintTargetUsingIds(Description description, PrintIdProvider idProvider,
		(PrintTarget)=>void block) {
		// printing can be expensive, so avoid it if the result will not be used anyway
		if (description instanceof NullDescription) return description

		printer.idProvider = idProvider
		block.apply(new HamcrestDescriptionPrintTarget(description))
		return description
	}
}
