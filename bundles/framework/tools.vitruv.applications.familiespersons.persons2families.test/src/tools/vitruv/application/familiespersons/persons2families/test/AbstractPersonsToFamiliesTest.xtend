package tools.vitruv.application.familiespersons.persons2families.test;

import tools.vitruv.framework.tests.VitruviusApplicationTest
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import tools.vitruv.domains.persons.PersonsDomainProvider
import tools.vitruv.domains.families.FamiliesDomainProvider
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import tools.vitruv.applications.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification

class AbstractPersonsToFamiliesTest extends VitruviusApplicationTest {
	private static val MODEL_FILE_EXTENSION = "persons";
	private static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def PersonRegister getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as PersonRegister;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new PersonsToFamiliesChangePropagationSpecification()]; 
	}
	
	override protected getVitruvDomains() {
		return #[new PersonsDomainProvider().domain, new FamiliesDomainProvider().domain];
	}
	
	override protected setup() {
		val rootObject = PersonsFactory.eINSTANCE.createPersonRegister;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, rootObject);
	}
	
	override protected cleanup() {
		// Do nothing
	}
	
}
