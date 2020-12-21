package tools.vitruv.testutils.printing

import tools.vitruv.framework.domains.VitruvDomain
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static tools.vitruv.testutils.printing.PrintResult.NOT_RESPONSIBLE
import org.eclipse.emf.ecore.resource.Resource

@FinalFieldsConstructor
final class DomainSpecificModelPrinter implements ModelPrinter {
	val VitruvDomain targetDomain
	val ModelPrinter domainPrinter

	final override printObject(PrintTarget target, PrintIdProvider idProvider, Object object) {
		if (responsibleFor(object))
			domainPrinter.printObject(target, idProvider, object)
		else
			NOT_RESPONSIBLE
	}

	final override printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object) {
		if (responsibleFor(object))
			domainPrinter.printObjectShortened(target, idProvider, object)
		else
			NOT_RESPONSIBLE
	}

	def private responsibleFor(Object object) {
		switch (object) {
			EObject: targetDomain.isInstanceOfDomainMetamodel(object)
			Resource: targetDomain.fileExtensions.contains(object.URI.fileExtension)
			default: false
		}
	}
	
	override withSubPrinter(ModelPrinter subPrinter) {
		new DomainSpecificModelPrinter(targetDomain, domainPrinter.withSubPrinter(subPrinter))
	}
}
