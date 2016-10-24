package tools.vitruv.dsls.response.api.generator;

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.core.resources.IProject
import org.eclipse.xtext.generator.IFileSystemAccess2
import com.google.inject.ImplementedBy
import tools.vitruv.dsls.response.environment.ResponseEnvironmentGenerator
import tools.vitruv.dsls.response.responseLanguage.Reaction

@ImplementedBy(ResponseEnvironmentGenerator)
interface IResponseEnvironmentGenerator {
	public def void cleanAndSetProject(IProject project);
	public def void addResponse(String sourceFileName, Reaction response);
	public def void addResponses(Resource responseResource);
	public def void addResponses(String sourceFileName, Iterable<Reaction> responses);	
	public def void generateEnvironment(IFileSystemAccess2 fsa);
}