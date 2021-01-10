package tools.vitruv.dsls.mirbase.ui.validation

import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import org.eclipse.xtext.validation.ValidationMessageAcceptor
import tools.vitruv.dsls.mirbase.validation.MirBaseValidator
import tools.vitruv.dsls.common.ui.validation.ProjectValidation

class MirBaseEclipseValidation {
	def static checkDomainDependency(ValidationMessageAcceptor acceptor, DomainReference domainReference) {
		if (MirBaseValidator.isValid(domainReference)) {
			ProjectValidation.checkDomainProjectIsOnClasspath(acceptor, domainReference.domain, domainReference)
		}
	}
}
