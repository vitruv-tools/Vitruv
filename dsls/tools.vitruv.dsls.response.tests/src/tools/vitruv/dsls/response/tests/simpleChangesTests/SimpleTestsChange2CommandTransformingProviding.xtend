package tools.vitruv.dsls.response.tests.simpleChangesTests;

import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransformingProviding
import mir.responses.AbstractChange2CommandTransformingAllElementTypesToAllElementTypes

public class SimpleTestsChange2CommandTransformingProviding extends AbstractChange2CommandTransformingProviding {
	public override void setup() {
		addChange2CommandTransforming(new AbstractChange2CommandTransformingAllElementTypesToAllElementTypes() {});
	}
}
