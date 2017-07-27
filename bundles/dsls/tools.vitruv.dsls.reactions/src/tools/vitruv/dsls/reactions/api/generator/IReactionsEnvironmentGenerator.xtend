package tools.vitruv.dsls.reactions.api.generator;

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import com.google.inject.ImplementedBy
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.environment.ReactionsEnvironmentGenerator
import org.eclipse.xtext.generator.OutputConfiguration

/**
 * Collects {@linkplain Reaction reactions} and generates all code required
 * to execute them.
 * 
 * <p>This is a one shot generator, meaning that an instance’s  
 * {@link #generateEnvironment} method should only ever be called once.
 * Instances must be obtained through Guice.
 */
@ImplementedBy(ReactionsEnvironmentGenerator)
interface IReactionsEnvironmentGenerator {
	public def void addReaction(String sourceFileName, Reaction reaction);

	public def void addReactions(Resource reactionResource);

	public def void addReactions(String sourceFileName, Iterable<Reaction> reactions);

	/**
	 * Creates the environment for the reactoins set on this generator.
	 * 
	 * <p>This method may only be called once in a generator’s lifetime. It
	 * does not support generating environments for different reaction sets.
	 * (And generating an environment for different reaction sets does not
	 * make sense). 
	 */
	public def void generateEnvironment(IFileSystemAccess2 fsa);
}
