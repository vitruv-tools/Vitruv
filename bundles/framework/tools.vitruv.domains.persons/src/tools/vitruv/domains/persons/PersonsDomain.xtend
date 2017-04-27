package tools.vitruv.domains.persons

import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import tools.vitruv.framework.domains.AbstractVitruvDomain
import tools.vitruv.domains.persons.tuid.PersonsTuidCalculatorAndResolver

class PersonsDomain extends AbstractVitruvDomain{
	private static final String METAMODEL_NAME = "Persons";
	public static val NAMESPACE_URIS = PersonsPackage.eINSTANCE.nsURIsRecursive;

	package new() {
		super(METAMODEL_NAME, PersonsPackage.eINSTANCE, generateTuidCalculator());
	}

	def protected static TuidCalculatorAndResolver generateTuidCalculator() {
		return new PersonsTuidCalculatorAndResolver(PersonsPackage.eNS_URI);
	}
	
}