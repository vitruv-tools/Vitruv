package edu.kit.ipd.sdq.vitruvius.dsls.mapping.tests.generated.testinfrastructure

import responses.ResponseAllElementTypesToAllElementTypes2Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransformingProviding
import responses.ResponseAllElementTypes2ToAllElementTypesChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import java.util.List
import responses.allElementTypes.responsesAllElementTypesToAllElementTypes2.ResponseAllElementTypesToAllElementTypes2Executor
import responses.allElementTypes.responsesAllElementTypes2ToAllElementTypes.ResponseAllElementTypes2ToAllElementTypesExecutor

class AllElementTypesChange2CommandTransformingProviding extends AbstractResponseChange2CommandTransformingProviding {
	new() {
		val List<Change2CommandTransforming> change2CommandTransformings = #[
			new ResponseAllElementTypes2ToAllElementTypesChange2CommandTransforming() {
				override protected setup() {
					this.addResponseExecutor(
						new ResponseAllElementTypes2ToAllElementTypesExecutor(userInteracting));
				}
			},
			new ResponseAllElementTypesToAllElementTypes2Change2CommandTransforming() {
				override protected setup() {
					this.addResponseExecutor(
						new ResponseAllElementTypesToAllElementTypes2Executor(userInteracting));
				}				
			}
		]
		initializeChange2CommandTransformationMap(change2CommandTransformings);
	}
}
