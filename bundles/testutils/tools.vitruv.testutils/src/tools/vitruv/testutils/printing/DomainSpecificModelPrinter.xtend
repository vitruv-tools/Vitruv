package tools.vitruv.testutils.printing

import tools.vitruv.framework.domains.VitruvDomain
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static tools.vitruv.testutils.printing.PrintResult.NOT_RESPONSIBLE

@FinalFieldsConstructor
final class DomainSpecificModelPrinter implements ModelPrinter {
	val VitruvDomain targetDomain
	val ModelPrinter domainPrinter

	final override printObject(PrintTarget target, Object object) {
		switch (object) {
			EObject case targetDomain.isInstanceOfDomainMetamodel(object): domainPrinter.printObject(target, object)
			default: NOT_RESPONSIBLE
		}
	}

	override setIdProvider(PrintIdProvider idProvider) {
		domainPrinter.idProvider = idProvider
	}
}
