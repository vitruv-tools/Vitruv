package edu.kit.ipd.sdq.vitruvius.dsls.mapping.tests.generated.testinfrastructure

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import java.util.List
import mir.responses.responsesAllElementTypesToAllElementTypes2.allElementTypes.ExecutorAllElementTypesToAllElementTypes2
import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.AbstractChange2CommandTransformingProviding
import mir.responses.Change2CommandTransformingAllElementTypes2ToAllElementTypes
import mir.responses.Change2CommandTransformingAllElementTypesToAllElementTypes2
import mir.responses.responsesAllElementTypes2ToAllElementTypes.allElementTypes.ExecutorAllElementTypes2ToAllElementTypes

class AllElementTypesChange2CommandTransformingProviding extends AbstractChange2CommandTransformingProviding {
	new() {
		val List<Change2CommandTransforming> change2CommandTransformings = #[
			new Change2CommandTransformingAllElementTypes2ToAllElementTypes() {
				override protected setup() {
					this.addResponseExecutor(
						new ExecutorAllElementTypes2ToAllElementTypes(userInteracting));
				}
			},
			new Change2CommandTransformingAllElementTypesToAllElementTypes2() {
				override protected setup() {
					this.addResponseExecutor(
						new ExecutorAllElementTypesToAllElementTypes2(userInteracting));
				}				
			}
		]
		change2CommandTransformings.forEach[addChange2CommandTransforming];
	}
}
