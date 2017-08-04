package tools.vitruv.dsls.reactions.api.generator;

import org.eclipse.xtext.generator.IFileSystemAccess2
import com.google.inject.ImplementedBy
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.generator.ReactionBuilder
import org.eclipse.xtext.resource.XtextResourceSet
import tools.vitruv.dsls.reactions.builder.FluentReactionsFileBuilder
import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import tools.vitruv.dsls.reactions.generator.ExternalReactionsGenerator

/**
 * Generates all code that is necessary to run reactions. Reactions to generate
 * code for can be added in two ways: 
 * <ul>
 * <li>Artificial reactions, created through {@link ReactionBuilder}, wan be
 * added through {@link #addReaction}.
 * <li>Reaction files parsed by Xtext can be added by adding their
 * {@link XtextResourceSet} through {@link addReactionFiles}.
 * </ul>
 * 
 * <p>This is a one shot generator, meaning that it may only be used once.
 * Clients must obtain instance through Guice, for example by injecting a 
 * {@code Provider<ReactionsGenerator>}.
 */
@ImplementedBy(ExternalReactionsGenerator)
interface IReactionsGenerator {

	/**
	 * Adds the provider {@code reactions} to this generator, so code will
	 * be generated for them.
	 * 
	 * @param sourceFileName
	 * 		The name of the virtual reactions file the provided 
	 * 		{@code reactions} are in. It influences the name of the generated
	 * 		Java classes and shoult thus be set to something sensible,
	 * 		describing what the added {@code reactions} do.
	 * @param reactions
	 * 		The reaction to add for generation. 
	 */
	def void addReaction(String sourceFileName, Reaction... reactions)

	/**
	 * Adds the provider {@code reactions} to this generator, so code will
	 * be generated for them.
	 * 
	 * @param sourceFileName
	 * 		The name of the virtual reactions file the provided 
	 * 		{@code reactions} are in. It influences the name of the generated
	 * 		Java classes and shoult thus be set to something sensible,
	 * 		describing what the added {@code reactions} do.
	 * @param reactions
	 * 		The reaction to add for generation. 
	 */
	def void addReaction(String sourceFileName, Iterable<? extends Reaction> reactions)
	
	def void addReaction(FluentReactionsFileBuilder reactionBuilder)

	/**
	 * Adds reaction files that were parsed by Xtext.
	 * 
	 * @param resourceSet
	 * 		The xtext resource set into which the files were parsed. All
	 * 		contained reactions will be generated.
	 */
	def void addReactionFiles(XtextResourceSet resourceSet)

	/**
	 * Executes this generator. This will create all Java code for the
	 * reactions set on this generator.
	 * 
	 * <p>This method may only be called once in a generator’s lifetime. It
	 * does not support generating environments for different sets of
	 * reactions.
	 */
	def void generate(IFileSystemAccess2 fsa)
	
	def void writeReactionsTo(Path outputFolder)
	def void writeReactionsTo(URI outputFolder)
	
}
