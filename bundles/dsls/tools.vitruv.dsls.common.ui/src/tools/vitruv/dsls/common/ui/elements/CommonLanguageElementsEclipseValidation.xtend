package tools.vitruv.dsls.common.ui.elements

import tools.vitruv.dsls.common.elements.DomainReference
import org.eclipse.xtext.validation.ValidationMessageAcceptor
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import org.eclipse.xtext.common.types.util.TypeReferences
import tools.vitruv.dsls.common.elements.CommonLanguageElementsValidation

class CommonLanguageElementsEclipseValidation {

	def static checkDomainDependency(ValidationMessageAcceptor acceptor, TypeReferences typeReferences,
		DomainReference domainReference) {
		if (CommonLanguageElementsValidation.isValid(domainReference)) {
			ProjectValidation.checkDomainProjectIsOnClasspath(acceptor, typeReferences, domainReference.domain,
				domainReference)
		}
	}

}
