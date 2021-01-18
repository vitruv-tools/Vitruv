package tools.vitruv.domains.demo.addresses

import edu.kit.ipd.sdq.metamodels.addresses.AddressesPackage
import tools.vitruv.domains.demo.VitruvDemoDomain
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage

class AddressesDomain extends VitruvDemoDomain {
	public static val String METAMODEL_NAME = "Addresses"
	public static val String FILE_EXTENSION = "addresses"

	package new() {
		super(METAMODEL_NAME, PersonsPackage.eINSTANCE, identifyingAttributes, FILE_EXTENSION)
	}

	def private static getIdentifyingAttributes() {
		#[AddressesPackage.Literals.IDENTIFIED_ELEMENT__ID]
	}

}
