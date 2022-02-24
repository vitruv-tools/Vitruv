package tools.vitruv.applications.demo.insurancepersons

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.metamodels.insurance.InsurancePackage

@Utility
class InsurancePersonsHelper {

	def static EObject getPersonRegister(Person person) {

		if (person.eContainer().eClass() == PersonsPackage.eINSTANCE.getPersonRegister()) {
			return person.eContainer()
		}
		throw new IllegalArgumentException("Container of person object is not of type PersonRegister, but " +
			person.eContainer())
	}

	def static EObject getInsuranceDatabase(InsuranceClient client) {

		if (client.eContainer().eClass() == InsurancePackage.eINSTANCE.getInsuranceDatabase()) {
			return client.eContainer()
		}
		throw new IllegalArgumentException("Container of person object is not of type PersonRegister, but " +
			client.eContainer())
	}
}
