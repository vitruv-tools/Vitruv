package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.pcmjava;

import java.util.ArrayList;
import java.util.List;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import responses.Response5_1ToJavaChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseChange2CommandTransformingProviding;

public class PCMJavaChange2CommandTransformingProviding extends AbstractResponseChange2CommandTransformingProviding {
	public PCMJavaChange2CommandTransformingProviding() {
		List<Change2CommandTransforming> transformationExecutingList = new ArrayList<Change2CommandTransforming>();
		transformationExecutingList.add(new Response5_1ToJavaChange2CommandTransforming());
		initializeChange2CommandTransformationMap(transformationExecutingList);
	}
	
}
