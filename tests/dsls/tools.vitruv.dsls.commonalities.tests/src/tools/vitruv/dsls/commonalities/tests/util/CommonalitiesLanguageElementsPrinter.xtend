package tools.vitruv.dsls.commonalities.tests.util

import tools.vitruv.testutils.printing.ModelPrinter
import tools.vitruv.testutils.printing.PrintTarget
import tools.vitruv.testutils.printing.PrintIdProvider
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import static tools.vitruv.testutils.printing.PrintResult.NOT_RESPONSIBLE

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
			default: NOT_RESPONSIBLE
		}
	}
	
	override withSubPrinter(ModelPrinter subPrinter) {
		this
	} 
}	
