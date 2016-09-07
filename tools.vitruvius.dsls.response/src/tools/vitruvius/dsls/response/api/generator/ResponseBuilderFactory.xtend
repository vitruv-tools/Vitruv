package tools.vitruvius.dsls.response.api.generator;

import tools.vitruvius.dsls.response.environment.ResponseBuilder;

class ResponseBuilderFactory {
	public def IResponseBuilder createResponseBuilder() {
		return new ResponseBuilder();
	}
}