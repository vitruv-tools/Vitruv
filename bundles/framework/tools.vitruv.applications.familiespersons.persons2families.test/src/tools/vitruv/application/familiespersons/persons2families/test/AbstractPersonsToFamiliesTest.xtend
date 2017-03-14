package tools.vitruv.application.familiespersons.persons2families.test

import tools.vitruv.framework.tests.VitruviusChangePropagationTest

class AbstractPersonsToFamiliesTest extends VitruviusChangePropagationTest {
	private static val MODEL_FILE_EXTENSION = "uml";
	private static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.root as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new PersonsToFamiliesChangePropagationSpecification()]; 
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new JavaDomain().metamodel];
	}
	
	override protected initializeTestModel() {
		val personsModel = PersonsFactory.eINSTANCE.createModel();
		persons.name = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, personsModel);
	}
	
}



