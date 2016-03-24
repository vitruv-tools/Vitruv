package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests;

import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.AbstractChange2CommandTransformingProviding
import mir.responses.Change2CommandTransformingAllElementTypesToAllElementTypes

public class SimpleTestsChange2CommandTransformingProviding extends AbstractChange2CommandTransformingProviding {
	public new() {
		addChange2CommandTransforming(new Change2CommandTransformingAllElementTypesToAllElementTypes());
	}
	
}
