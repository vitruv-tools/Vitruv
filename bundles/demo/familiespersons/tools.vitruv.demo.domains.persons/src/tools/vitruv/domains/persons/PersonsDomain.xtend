package tools.vitruv.domains.persons

import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain
import tools.vitruv.domains.persons.tuid.PersonsTuidCalculatorAndResolver
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator

class PersonsDomain extends  AbstractTuidAwareVitruvDomain {
	static final String METAMODEL_NAME = "Persons";
	static final String FILE_EXTENSION = "persons";
	public static val NAMESPACE_URIS = PersonsPackage.eINSTANCE.nsURIsRecursive;

	package new() {
		super(METAMODEL_NAME, PersonsPackage.eINSTANCE, generateTuidCalculator(), FILE_EXTENSION);
	}

	def protected static TuidCalculatorAndResolver generateTuidCalculator() {
		return new PersonsTuidCalculatorAndResolver(PersonsPackage.eNS_URI);
	}
	
	override getBuilderApplicator() {
		return new VitruviusEmfBuilderApplicator();
	}
	
	override boolean isUserVisible() {
		return false;
	}
	
}