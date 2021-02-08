package tools.vitruv.testutils.printing

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import java.util.Collection
import static tools.vitruv.testutils.printing.PrintResult.*
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class CombinedModelPrinter implements ModelPrinter {
		val List<ModelPrinter> printers
		
		new(ModelPrinter printer, ModelPrinter fallback) {
			this(List.of(printer), fallback)
		}

		new(List<? extends ModelPrinter> printers, ModelPrinter fallback) {
			this.printers = (printers + List.of(fallback))
			.flatMap[if (it instanceof CombinedModelPrinter) it.printers else List.of(it)]
			.mapFixed[withSubPrinter(this)]
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
			EObject object,
			EStructuralFeature feature,
			Collection<?> valueList
		) {
			useFirstResponsible[printFeatureValueList(target, idProvider, object, feature, valueList)]
		}

		override printFeatureValueSet(
			PrintTarget target,
			PrintIdProvider idProvider,
			EObject object,
			EStructuralFeature feature,
			Collection<?> valueSet
		) {
			useFirstResponsible[printFeatureValueSet(target, idProvider, object, feature, valueSet)]
		}

		override PrintResult printFeatureValue(
			PrintTarget target,
			PrintIdProvider idProvider,
			EObject object,
			EStructuralFeature feature,
			Object value
		) {
			useFirstResponsible[printFeatureValue(target, idProvider, object, feature, value)]
		}

		override withSubPrinter(ModelPrinter subPrinter) {
			new CombinedModelPrinter(printers.mapFixed[withSubPrinter(subPrinter)])
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