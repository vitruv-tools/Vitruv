package edu.kit.ipd.sdq.vitruvius.dsls.mapping.tests.generated.testinfrastructure

import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import java.util.List
import responses.ResponsePcm_mockupToUml_mockupChange2CommandTransforming
import responses.ResponseUml_mockupToPcm_mockupChange2CommandTransforming
import responses.testPCM2UMLChild.responsesPcm_mockupToUml_mockup.ResponsePcm_mockupToUml_mockupExecutor
import responses.testPCM2UMLChild.responsesUml_mockupToPcm_mockup.ResponseUml_mockupToPcm_mockupExecutor

class TestPCM2UMLChildChange2CommandTransformingProviding extends AbstractResponseChange2CommandTransformingProviding {
	new() {
		val List<Change2CommandTransforming> change2CommandTransformings = #[
			new ResponsePcm_mockupToUml_mockupChange2CommandTransforming() {
				override protected setup() {
					this.addResponseExecutor(
						new ResponsePcm_mockupToUml_mockupExecutor(userInteracting))
				}
			},
			new ResponseUml_mockupToPcm_mockupChange2CommandTransforming() {
				override protected setup() {
					this.addResponseExecutor(
						new ResponseUml_mockupToPcm_mockupExecutor(userInteracting))
				}				
			}
		]
		initializeChange2CommandTransformationMap(change2CommandTransformings);
	}
}
