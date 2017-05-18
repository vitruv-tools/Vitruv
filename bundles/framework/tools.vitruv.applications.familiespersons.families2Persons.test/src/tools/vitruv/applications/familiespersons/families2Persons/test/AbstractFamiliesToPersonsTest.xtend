package tools.vitruv.applications.familiespersons.families2Persons.test

import tools.vitruv.framework.tests.VitruviusApplicationTest
import edu.kit.ipd.sdq.metamodels.persons.PersonsRoot
import tools.vitruv.domains.persons.PersonsDomainProvider
import tools.vitruv.domains.families.FamiliesDomainProvider
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import edu.kit.ipd.sdq.metamodels.families.FamiliesRoot

class AbstractFamiliesToPersonsTest extends VitruviusApplicationTest {
	private static val MODEL_FILE_EXTENSION = "families";
	private static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def FamiliesRoot getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as FamiliesRoot;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]; 
	}
	
	override protected getVitruvDomains() {
		return #[new FamiliesDomainProvider(), new PersonsDomainProvider()];
	}
	
	override protected setup() {
		val rootObject = PersonsFactory.eINSTANCE.createPersonsRoot;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, rootObject);
	}
	
	override protected cleanup() {
		// Do nothing
	}
	
}
