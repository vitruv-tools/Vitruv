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
import org.eclipse.emf.ecore.resource.ResourceSet

/**
 * Generates all code that is necessary to run reactions. Reactions to generate
 * code for can be added in different ways: 
 * <ul>
 * <li>Artificial reactions, created through {@link ReactionBuilder}, can be
 * added through {@link #addReaction}.
 * <li>Artificial reaction files, created by {@link FluentReactionsFileBuilder},
 * can be added through {@link #addReactionFile(FluentReactionsFileBuilder)}.
 * <li>Reaction files parsed by Xtext can be added by adding their
 * {@link XtextResourceSet} through {@link addReactionFiles}.
 * </ul>
 * 
 * <p>Reaction generation requires access to certain libraries that must be
 * present at the generation target. In Xtext, resolving such cross references
 * is done through resource sets. The caller must provide an appropriate
 * resource set (through {@link #useResourceSet} to resolve references in if
 * artificial reaction are to be generated. 
 * 
 * <p>This is a one shot generator, meaning that it may only be used once.
 * Clients must obtain instance through Guice, for example by injecting a 
 * {@code Provider<ReactionsGenerator>}.
 */
@ImplementedBy(ExternalReactionsGenerator)
interface IReactionsGenerator {

	/**
	 * Adds the given {@code reactions} to this generator, so code will
	 * be generated for them.
	 * 
	 * <p>Note that a resource set must be provided (through 
	 * {@link #useResourceSet}) before attempting to generate code for 
	 * artificial reactions with this generator.
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
	 * Adds the given {@code reactions} to this generator, so code will
	 * be generated for them.
	 * 
	 * <p>Note that a resource set must be provided (through 
	 * {@link #useResourceSet}) before attempting to generate code for 
	 * artificial reactions with this generator.
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

	/**
	 * Adds the provided reaction file builder to this generator, so code will
	 * be generated for the objects described by it.
	 * 
	 * <p>Note that a resource set must be provided (through 
	 * {@link #useResourceSet}) before attempting to generate code for 
	 * artificial reactions with this generator.
	 * 
	 * @param reactionBuilder
	 * 		The builder describing the reaction file to generate code for.		
	 */
	def void addReactionsFile(FluentReactionsFileBuilder reactionBuilder)

	/**
	 * Adds reaction files that were parsed by Xtext.
	 * 
	 * @param resourceSet
	 * 		The xtext resource set into which the files were parsed. All
	 * 		contained reactions will be generated.
	 */
	def void addReactionsFiles(XtextResourceSet resourceSet)

	/**
	 * Provides the resource set that shall be used when generating code for
	 * artificial reactions. The resource set must be capable of providing all
	 * types that are needed to be referenced during generation. This will 
	 * usually be an {@link XtextResourceSet}.
	 */
	def void useResourceSet(ResourceSet resourceSet)

	/**
	 * Executes this generator. This will create all Java code for the
	 * reactions set on this generator.
	 * 
	 * <p>This method may only be called once in a generator’s lifetime. It
	 * does not support generating environments for different sets of
	 * reactions.
	 * 
	 * @throws IllegalStateException
	 * 		If this generator has been used to generate before
	 */
	def void generate(IFileSystemAccess2 fsa)

	/**
	 * Writes all reactions added to this generator to the given 
	 * {@code outputFolder}, as reactions files.
	 * 
	 * <p>This method’s main purpose is to translate artificial reactions
	 * created with a builder into serialized reaction files.  
	 */
	def void writeReactionsTo(Path outputFolder)

	/**
	 * Writes all reactions added to this generator to the given 
	 * {@code outputFolder}, as reactions files.
	 * 
	 * <p>This method’s main purpose is to translate artificial reactions
	 * created with a builder into serialized reaction files.  
	 */
	def void writeReactionsTo(URI outputFolder)

}
