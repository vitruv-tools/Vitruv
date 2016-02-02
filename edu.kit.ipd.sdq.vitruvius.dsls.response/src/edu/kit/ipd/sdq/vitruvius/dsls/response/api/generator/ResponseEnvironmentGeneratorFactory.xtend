package edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator;

import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.ResponseEnvironmentGenerator

public class ResponseEnvironmentGeneratorFactory {
	public def IResponseEnvironmentGenerator createResponseEnvironmentGenerator() {
		return new ResponseEnvironmentGenerator();
	}
}