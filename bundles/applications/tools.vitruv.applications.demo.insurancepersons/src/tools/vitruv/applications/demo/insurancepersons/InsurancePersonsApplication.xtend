package tools.vitruv.applications.demo.insurancepersons

import java.util.Set
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.framework.applications.VitruvApplication
import tools.vitruv.domains.demo.insurance.InsuranceDomainProvider
import tools.vitruv.applications.demo.insurancepersons.insurance2persons.InsuranceToPersonsChangePropagationSpecification
import tools.vitruv.applications.demo.insurancepersons.persons2insurance.PersonsToInsuranceChangePropagationSpecification

class InsurancePersonsApplication implements VitruvApplication {
	override getChangePropagationSpecifications() {
		Set.of(new InsuranceToPersonsChangePropagationSpecification(),
			new PersonsToInsuranceChangePropagationSpecification())
	}

	override getVitruvDomains() {
		Set.of(new PersonsDomainProvider().getDomain(), new InsuranceDomainProvider().getDomain())
	}

	override getName() {
		"Insurance-Persons"
	}
}
