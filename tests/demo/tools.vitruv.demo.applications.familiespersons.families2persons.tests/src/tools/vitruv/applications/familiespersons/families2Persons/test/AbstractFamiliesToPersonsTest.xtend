package tools.vitruv.applications.familiespersons.families2Persons.test

import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.domains.persons.PersonsDomainProvider
import tools.vitruv.domains.families.FamiliesDomainProvider
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import tools.vitruv.applications.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification
import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import org.apache.log4j.Logger
import org.apache.log4j.Level

class AbstractFamiliesToPersonsTest extends VitruviusApplicationTest {
	private static val MODEL_FILE_EXTENSION = "families";
	private static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def FamilyRegister getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as FamilyRegister;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]; 
	}
	
	override protected getVitruvDomains() {
		return #[new FamiliesDomainProvider().domain, new PersonsDomainProvider().domain];
	}
	
	override protected setup() {
		Logger.rootLogger.level = Level.DEBUG;
		val rootObject = FamiliesFactory.eINSTANCE.createFamilyRegister;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, rootObject);
	}
	
	override protected cleanup() {
		// Do nothing
	}
	
}
