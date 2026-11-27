package tools.vitruv.change.testutils.printing

import org.eclipse.emf.common.util.URI
import static tools.vitruv.change.testutils.printing.PrintResult.NOT_RESPONSIBLE
import java.util.List
import static com.google.common.base.Preconditions.checkArgument

class UriReplacingPrinter implements ModelPrinter {
	val ModelPrinter subPrinter
	val List<Pair<URI, URI>> replacements

	private new(ModelPrinter subPrinter, List<Pair<URI, URI>> replacements) {
		this.subPrinter = subPrinter
		this.replacements = replacements
	}

	new(List<Pair<URI, URI>> replacements) {
		subPrinter = this
		this.replacements = checkReplacements(replacements)
	}

	def private static checkReplacements(List<Pair<URI, URI>> replacements) {
		for (usedUri : replacements.flatMap[List.of(key, value)]) {
			checkArgument(usedUri.isPrefix, '''This is not a prefix URI: «usedUri»''')
		}
		replacements
	}

	override PrintResult printObject(PrintTarget target, PrintIdProvider idProvider, Object object) {
		switch (object) {
			URI: {
				val replacement = replacementFor(object)
				if (replacement !== null)
					subPrinter.printObject(target, idProvider, replacement)
				else
					NOT_RESPONSIBLE // don’t call subPrinter, because it might call us again
			}
			default:
				NOT_RESPONSIBLE
		}
	}

	override PrintResult printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object) {
		switch (object) {
			URI: {
				val replacement = replacementFor(object)
				if (replacement !== null)
					subPrinter.printObjectShortened(target, idProvider, replacement)
				else
					NOT_RESPONSIBLE // don’t call subPrinter, because it might call us again
			}
			default:
				NOT_RESPONSIBLE
		}
	}

	def private URI replacementFor(URI uri) {
		replacements.map[uri.replacePrefix(key, value)].findFirst[it !== null]
	}

	override withSubPrinter(ModelPrinter subPrinter) {
		new UriReplacingPrinter(subPrinter, this.replacements)
	}
}
