package tools.vitruv.testutils.printing

import org.junit.jupiter.api.^extension.BeforeAllCallback
import org.junit.jupiter.api.^extension.BeforeEachCallback
import org.junit.jupiter.api.^extension.ExtensionContext
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.testutils.printing.ModelPrinter
import org.junit.jupiter.api.^extension.ExtensionContext.Store.CloseableResource
import org.junit.jupiter.api.^extension.ExtensionContext.Namespace
import java.lang.reflect.AnnotatedElement
import tools.vitruv.testutils.printing.PrintIdProvider
import java.util.List
import tools.vitruv.testutils.printing.DefaultModelPrinter
import static com.google.common.base.Preconditions.checkArgument
import static tools.vitruv.testutils.printing.PrintResult.PRINTED

/**
 * JUnit extension that enables {@link UseModelPrinter}.
 */
class ModelPrinterChange implements BeforeAllCallback, BeforeEachCallback {
	static val namespace = Namespace.create(ModelPrinterChange)

	override beforeAll(ExtensionContext context) {
		context.testClass.ifPresent [ testClass |
			installPrintersFrom(testClass, context)
		]
	}

	override beforeEach(ExtensionContext context) {
		context.testMethod.ifPresent [ testMethod |
			installPrintersFrom(testMethod, context)
		]
	}

	def private installPrintersFrom(AnnotatedElement settingsSource, ExtensionContext context) {
		val annotations = settingsSource.getAnnotationsByType(UseModelPrinter)
		if (annotations.isEmpty) return;

		val printers = annotations.flatMap[value.toList].map [ printerClass |
			val constructor = printerClass.constructors.findFirst[it.parameterCount == 0]
			checkArgument(constructor !== null, '''«printerClass» has no zero-arg constructor!''')
			try {
				constructor.newInstance() as ModelPrinter
			} catch (RuntimeException e) {
				throw new IllegalStateException('''Failed to create an instance of «printerClass»!''', e)
			}
		].toList
		context.getStore(namespace).put(ModelPrinter, new ModelPrintingGuard(ModelPrinting.printer))
		ModelPrinting.printer = new MultiplePrintersPrinter(printers)
	}

	@FinalFieldsConstructor
	private static class MultiplePrintersPrinter implements ModelPrinter {
		val List<? extends ModelPrinter> printers
		val fallback = new DefaultModelPrinter

		override setIdProvider(PrintIdProvider provider) {
			printers.forEach[idProvider = provider]
		}

		override printObject(PrintTarget target, Object object) {
			if (printers.exists[printObject(target, object) == PRINTED]) {
				PRINTED
			} else {
				fallback.printObject(target, object)
			}
		}
	}

	@FinalFieldsConstructor
	private static class ModelPrintingGuard implements CloseableResource {
		val ModelPrinter previousPrinter

		override close() {
			ModelPrinting.printer = previousPrinter
		}
	}
}
