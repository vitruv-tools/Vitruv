package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests;

import java.util.ArrayList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransformingProviding;
import responses.ResponseAllElementTypesToAllElementTypesChange2CommandTransforming

public class SimpleTestsChange2CommandTransformingProviding extends AbstractResponseChange2CommandTransformingProviding {
	new() {
		val transformationExecutingList = new ArrayList<Change2CommandTransforming>();
		transformationExecutingList.add(new ResponseAllElementTypesToAllElementTypesChange2CommandTransforming());
		initializeChange2CommandTransformationMap(transformationExecutingList);
	}
	
}
