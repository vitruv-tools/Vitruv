package edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator;

import edu.kit.ipd.sdq.vitruvius.dsls.response.environment.ResponseBuilder;

class ResponseBuilderFactory {
	public def IResponseBuilder createResponseBuilder() {
		return new ResponseBuilder();
	}
}