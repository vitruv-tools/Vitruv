package tools.vitruv.applications.demo.familiespersons;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification;
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification;
import tools.vitruv.domains.demo.families.FamiliesDomainProvider;
import tools.vitruv.domains.demo.persons.PersonsDomainProvider;
import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.domains.VitruvDomain;

public class FamiliesPersonsApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specifications = new HashSet<ChangePropagationSpecification>();
		specifications.add(new FamiliesToPersonsChangePropagationSpecification());
		specifications.add(new PersonsToFamiliesChangePropagationSpecification());
		return specifications;
	}

	@Override
	public Set<VitruvDomain> getVitruvDomains() {
		Set<VitruvDomain> domains = new HashSet<VitruvDomain>();
		domains.add(new PersonsDomainProvider().getDomain());
		domains.add(new FamiliesDomainProvider().getDomain());
		return domains;
	}

	@Override
	public String getName() {
		return "Families-Persons";
	}
}
