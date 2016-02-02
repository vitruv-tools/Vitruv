package edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator;

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import org.eclipse.xtext.generator.IFileSystemAccess

interface IResponseEnvironmentGenerator {
	public def void addResponse(Response response);
	public def void addResponses(Iterable<Response> responses);	
	public def void generateEnvironment(IFileSystemAccess fsa);
	
}