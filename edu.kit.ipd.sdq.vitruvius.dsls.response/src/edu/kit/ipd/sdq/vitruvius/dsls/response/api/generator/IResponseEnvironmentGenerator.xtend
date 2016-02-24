package edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator;

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.core.resources.IProject
import org.eclipse.xtext.generator.IFileSystemAccess2
import com.google.inject.ImplementedBy
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.ResponseEnvironmentGenerator

@ImplementedBy(ResponseEnvironmentGenerator)
interface IResponseEnvironmentGenerator {
	public def void cleanAndSetProject(IProject project);
	public def void addResponse(String sourceFileName, Response response);
	public def void addResponses(Resource responseResource);
	public def void addResponses(String sourceFileName, Iterable<Response> responses);	
	public def void generateEnvironment(IFileSystemAccess2 fsa);
}