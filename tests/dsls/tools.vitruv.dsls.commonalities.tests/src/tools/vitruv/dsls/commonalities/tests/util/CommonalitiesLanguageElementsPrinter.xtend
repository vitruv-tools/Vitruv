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

class CommonalitiesLanguageElementsPrinter implements ModelPrinter {
	override printObject(extension PrintTarget target, PrintIdProvider idProvider, Object object) {
		switch(object) {
			EClassAdapter: print(object.toString)
			default: NOT_RESPONSIBLE
		}
	}

	override printObjectShortened(extension PrintTarget target, PrintIdProvider idProvider, Object object) {
		switch(object) {
			EClassAdapter: print(object.toString)
			Commonality: print(object.toString)
			default: NOT_RESPONSIBLE
		}
	}
	
	override printFeatureValue(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		EStructuralFeature feature,
		Object value
	) {
		switch(feature) {
			case COMMONALITY_REFERENCE__REFERENCE_TYPE: 
				print('{{') + printObjectShortened(target, idProvider, value) + print('}}')
			default: NOT_RESPONSIBLE
		}
	}
	
	override withSubPrinter(ModelPrinter subPrinter) {
		this
	} 
}	
