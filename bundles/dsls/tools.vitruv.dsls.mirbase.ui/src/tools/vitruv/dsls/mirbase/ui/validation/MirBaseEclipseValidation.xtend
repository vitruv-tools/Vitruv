package tools.vitruv.dsls.mirbase.ui.validation

import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import org.eclipse.xtext.validation.ValidationMessageAcceptor
import tools.vitruv.dsls.mirbase.validation.MirBaseValidator
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import org.eclipse.xtext.common.types.util.TypeReferences

class MirBaseEclipseValidation {
	def static checkDomainDependency(ValidationMessageAcceptor acceptor, TypeReferences typeReferences,
		DomainReference domainReference) {
		if (MirBaseValidator.isValid(domainReference)) {
			ProjectValidation.checkDomainProjectIsOnClasspath(acceptor, typeReferences, domainReference.domain,
				domainReference)
		}
	}
}
