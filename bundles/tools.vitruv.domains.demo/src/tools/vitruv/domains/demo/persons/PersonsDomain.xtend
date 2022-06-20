package tools.vitruv.domains.demo.persons

import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage
import tools.vitruv.domains.demo.VitruvDemoDomain

class PersonsDomain extends VitruvDemoDomain {
	public static val String METAMODEL_NAME = "Persons"
	public static val String FILE_EXTENSION = "persons"

	package new() {
		super(METAMODEL_NAME, PersonsPackage.eINSTANCE, FILE_EXTENSION)
	}

}
