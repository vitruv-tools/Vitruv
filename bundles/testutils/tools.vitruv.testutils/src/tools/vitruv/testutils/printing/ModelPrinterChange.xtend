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
import static com.google.common.base.Preconditions.checkArgument
import static tools.vitruv.testutils.printing.PrintResult.*
import static extension org.junit.platform.commons.support.AnnotationSupport.findAnnotation
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import java.util.Collection
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

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
		val currentPrinter = ModelPrinting.printer
		context.getStore(namespace).put(ModelPrinter, new ModelPrintingGuard(currentPrinter))
		ModelPrinting.printer = new MultiplePrintersPrinter(printers, currentPrinter)
	}

	private static class MultiplePrintersPrinter implements ModelPrinter {
		val List<ModelPrinter> printers

		new(List<? extends ModelPrinter> printers, ModelPrinter fallback) {
			this.printers = (printers + List.of(fallback)).mapFixed[withSubPrinter(this)]
		}

		private new(List<ModelPrinter> printers) {
			this.printers = printers
		}

		override printObject(PrintTarget target, PrintIdProvider idProvider, Object object) {
			useFirstResponsible[printObject(target, idProvider, object)]
		}

		override printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object) {
			useFirstResponsible[printObjectShortened(target, idProvider, object)]
		}

		override PrintResult printFeature(
			PrintTarget target,
			PrintIdProvider idProvider,
			EObject object,
			EStructuralFeature feature
		) {
			useFirstResponsible[printFeature(target, idProvider, object, feature)]
		}

		override printFeatureValueList(
			PrintTarget target,
			PrintIdProvider idProvider,
			EStructuralFeature feature,
			Collection<?> valueList
		) {
			useFirstResponsible[printFeatureValueList(target, idProvider, feature, valueList)]
		}

		override printFeatureValueSet(
			PrintTarget target,
			PrintIdProvider idProvider,
			EStructuralFeature feature,
			Collection<?> valueSet
		) {
			useFirstResponsible[printFeatureValueSet(target, idProvider, feature, valueSet)]
		}

		override PrintResult printFeatureValue(
			PrintTarget target,
			PrintIdProvider idProvider,
			EStructuralFeature feature,
			Object value
		) {
			useFirstResponsible[printFeatureValue(target, idProvider, feature, value)]
		}

		override withSubPrinter(ModelPrinter subPrinter) {
			new MultiplePrintersPrinter(printers.mapFixed[withSubPrinter(subPrinter)])
		}

		def private PrintResult useFirstResponsible((ModelPrinter)=>PrintResult action) {
			for (printer : printers) {
				val result = action.apply(printer)
				if (result != NOT_RESPONSIBLE) return result
			}
			throw new IllegalStateException(
				'Could not find a responsible printer! Please make sure that you configure a suitable fallback!')
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
