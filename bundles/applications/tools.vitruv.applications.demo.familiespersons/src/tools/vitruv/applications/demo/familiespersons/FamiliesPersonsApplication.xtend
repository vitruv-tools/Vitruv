package tools.vitruv.applications.demo.familiespersons

import java.util.Set
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.framework.applications.VitruvApplication

class FamiliesPersonsApplication implements VitruvApplication {
	override getChangePropagationSpecifications() {
		Set.of(new FamiliesToPersonsChangePropagationSpecification(),
			new PersonsToFamiliesChangePropagationSpecification())
	}

	override getVitruvDomains() {
		Set.of(new PersonsDomainProvider().getDomain(), new FamiliesDomainProvider().getDomain())
	}

	override getName() {
		"Families-Persons"
	}
}
