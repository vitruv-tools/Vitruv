package tools.vitruv.testutils.printing

import org.junit.jupiter.api.^extension.BeforeAllCallback
import org.junit.jupiter.api.^extension.BeforeEachCallback
import org.junit.jupiter.api.^extension.ExtensionContext
import tools.vitruv.testutils.printing.ModelPrinter
import org.junit.jupiter.api.^extension.ExtensionContext.Store.CloseableResource
import org.junit.jupiter.api.^extension.ExtensionContext.Namespace
import java.lang.reflect.AnnotatedElement
import static com.google.common.base.Preconditions.checkArgument
import static extension org.junit.platform.commons.support.AnnotationSupport.findAnnotation

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
		val annotation = settingsSource.findAnnotation(UseModelPrinter)
		if (!annotation.isPresent) return;

		val printers = annotation.get.value.map [ printerClass |
			val constructor = printerClass.constructors.findFirst[it.parameterCount == 0]
			checkArgument(constructor !== null, '''«printerClass» has no zero-arg constructor!''')
			try {
				constructor.newInstance() as ModelPrinter
			} catch (RuntimeException e) {
				throw new IllegalStateException('''Failed to create an instance of «printerClass»!''', e)
			}
		]
		val printerChange = ModelPrinting.use[currentPrinter|new CombinedModelPrinter(printers, currentPrinter)]
		context.getStore(namespace).put(ModelPrinter, revertAtEndOfScope(printerChange))
	}

	def private static CloseableResource revertAtEndOfScope(AutoCloseable printerChange) {
		return [printerChange.close()]
	}
}
