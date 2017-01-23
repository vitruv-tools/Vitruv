package tools.vitruv.dsls.reactions.api.generator;

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.core.resources.IProject
import org.eclipse.xtext.generator.IFileSystemAccess2
import com.google.inject.ImplementedBy
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.environment.ReactionsEnvironmentGenerator

@ImplementedBy(ReactionsEnvironmentGenerator)
interface IReactionsEnvironmentGenerator {
	public def void cleanAndSetProject(IProject project);
	public def void addReaction(String sourceFileName, Reaction reaction);
	public def void addReactions(Resource reactionResource);
	public def void addReactions(String sourceFileName, Iterable<Reaction> reactions);	
	public def void generateEnvironment(IFileSystemAccess2 fsa);
}