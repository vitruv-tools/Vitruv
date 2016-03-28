package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses;

import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.AbstractChange2CommandTransformingProviding;
import mir.responses.Change2CommandTransforming5_1ToJava;

public class PCMJavaChange2CommandTransformingProviding extends AbstractChange2CommandTransformingProviding {
	public PCMJavaChange2CommandTransformingProviding() {
		addChange2CommandTransforming(new Change2CommandTransforming5_1ToJava());
	}
}
