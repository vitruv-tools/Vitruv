package tools.vitruv.application.familiespersons.persons2families.test;


import tools.vitruv.framework.tests.VitruviusApplicationTest
import edu.kit.ipd.sdq.metamodels.persons.PersonsRoot
import tools.vitruv.domains.persons.PersonsDomainProvider
import tools.vitruv.domains.families.FamiliesDomainProvider
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory

class AbstractPersonsToFamiliesTest extends VitruviusApplicationTest {
	private static val MODEL_FILE_EXTENSION = "persons";
	private static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def PersonsRoot getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as PersonsRoot;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new PersonsToFamiliesChangePropagationSpecification()]; 
	}
	
	override protected getVitruvDomains() {
		return #[new PersonsDomainProvider(), new FamiliesDomainProvider()];
	}
	
	override protected setup() {
		val rootObject = PersonsFactory.eINSTANCE.createPersonsRoot;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, rootObject);
	}
	
	override protected cleanup() {
		// Do nothing
	}
	
}
