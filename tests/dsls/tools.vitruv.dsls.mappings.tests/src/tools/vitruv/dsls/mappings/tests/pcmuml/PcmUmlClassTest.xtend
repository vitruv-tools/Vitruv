package tools.vitruv.dsls.mappings.tests.pcmuml

import tools.vitruv.testutils.VitruviusApplicationTest

abstract class PcmUmlClassTest extends VitruviusApplicationTest{
	
	override protected createChangePropagationSpecifications() {
		return #[
			new PcmToUmlClassReactionsChangePropagationSpecification, 
			new UmlClassToPcmReactionsChangePropagationSpecification
		];  
	}
	
}