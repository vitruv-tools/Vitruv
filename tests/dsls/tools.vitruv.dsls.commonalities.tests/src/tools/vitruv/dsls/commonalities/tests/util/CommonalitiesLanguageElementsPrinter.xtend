package tools.vitruv.dsls.commonalities.tests.util

import tools.vitruv.testutils.printing.ModelPrinter
import tools.vitruv.testutils.printing.PrintTarget
import tools.vitruv.testutils.printing.PrintIdProvider
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import static tools.vitruv.testutils.printing.PrintResult.NOT_RESPONSIBLE
import org.eclipse.emf.ecore.EStructuralFeature
import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*
import tools.vitruv.dsls.commonalities.language.Commonality
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import tools.vitruv.dsls.commonalities.language.elements.EFeatureAdapter
import tools.vitruv.testutils.printing.PrintResult

class CommonalitiesLanguageElementsPrinter implements ModelPrinter {
	override printObject(PrintTarget target, PrintIdProvider idProvider, Object object) {
		switch(object) {
			EClassAdapter,
			EFeatureAdapter: target.printReference [print(object.toString)]
			default: NOT_RESPONSIBLE
		}
	}

	override printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object) {
		switch(object) {
			Commonality: target.printReference [print(object.toString)]
			default: printObject(target, idProvider, object)
		}
	}
	
	override printFeatureValue(
		PrintTarget target,
		PrintIdProvider idProvider,
		EStructuralFeature feature,
		Object value
	) {
		switch(feature) {
			case COMMONALITY_REFERENCE__REFERENCE_TYPE: 
				target.printReference [printObjectShortened(idProvider, value)]
			default: NOT_RESPONSIBLE
		}
	}
	
	def printReference(extension PrintTarget target, (PrintTarget)=>PrintResult valuePrinter) {
		print('\u21AA' /* rightwards arrow with hook */) + valuePrinter.apply(target) 
	}
	
	override withSubPrinter(ModelPrinter subPrinter) {
		this
	} 
}	
