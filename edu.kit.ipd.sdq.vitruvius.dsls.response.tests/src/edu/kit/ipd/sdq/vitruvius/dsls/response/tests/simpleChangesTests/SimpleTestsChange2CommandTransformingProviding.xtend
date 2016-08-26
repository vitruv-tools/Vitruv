package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests;

import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.AbstractChange2CommandTransformingProviding
import mir.responses.AbstractChange2CommandTransformingAllElementTypesToAllElementTypes

public class SimpleTestsChange2CommandTransformingProviding extends AbstractChange2CommandTransformingProviding {
	public override void setup() {
		addChange2CommandTransforming(new AbstractChange2CommandTransformingAllElementTypesToAllElementTypes() {});
	}
}
