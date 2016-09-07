package tools.vitruv.dsls.response.api.generator;

import tools.vitruv.dsls.response.environment.ResponseBuilder;

class ResponseBuilderFactory {
	public def IResponseBuilder createResponseBuilder() {
		return new ResponseBuilder();
	}
}