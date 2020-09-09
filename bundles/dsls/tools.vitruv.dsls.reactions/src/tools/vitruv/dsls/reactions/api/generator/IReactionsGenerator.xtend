package tools.vitruv.dsls.reactions.api.generator;

import org.eclipse.xtext.generator.IFileSystemAccess2
import com.google.inject.ImplementedBy
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import org.eclipse.xtext.resource.XtextResourceSet
import tools.vitruv.dsls.reactions.builder.FluentReactionsFileBuilder
import tools.vitruv.dsls.reactions.generator.ExternalReactionsGenerator
import org.eclipse.emf.ecore.resource.ResourceSet
import java.io.IOException
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile

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
 * <p>Reaction generation requires linking to elements inside and outside of
 * the created element’s scope. In Xtext, resolving such cross references
 * is done through resource sets. The caller must provide an appropriate
 * resource set (through {@link #useResourceSet}) before adding artificial
 * resources to this generator, to make linking them possible.
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
	 * <p>A resource set must be provided (through {@link #useResourceSet})
	 * before calling this method.
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
	 * <p>A resource set must be provided (through {@link #useResourceSet})
	 * before calling this method.
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
	 * <p>A resource set must be provided (through {@link #useResourceSet})
	 * before calling this method.
	 * 
	 * @param reactionBuilder
	 * 		The builder describing the reaction file to generate code for.
	 */
	def void addReactionsFile(FluentReactionsFileBuilder reactionBuilder)

	/**
	 * Adds the reactions of the provided {@link ReactionsFile} to this generator
	 * in a synthetic resource with the given file name.
	 *
	 * <p>A resource set must be provided (through {@link #useResourceSet})
	 * before calling this method.
	 *
	 * @param sourceFileName
	 * 		The name of the synthetically generated file from which reactions shall be
	 * 		created (without file extension)
	 * @param reactionsFile
	 * 		The {@link ReactionsFile} from which the reactions shall be added for
	 * 		generation.
	 */
	def void addReactionsFile(String sourceFileName, ReactionsFile reactionsFile)

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
	 * @param fsa
	 * 		The file system acces to use for writing the results. Results will
	 * 		be written to the root of the default output configuration.
	 * 
	 * @throws IllegalStateException
	 * 		If this generator has been used to generate before
	 */
	def void generate(IFileSystemAccess2 fsa)

	/**
	 * Writes all reactions added to this generator to the given 
	 * file system provider, as reactions files.
	 * 
	 * <p>This method’s main purpose is to translate artificial reactions
	 * created with a builder into serialized reaction files.  
	 * 
	 * @param fsa
	 * 		The file system acces to use for writing the results. Results will
	 * 		be written to the root of the default output configuration.
	 */
	def void writeReactions(IFileSystemAccess2 fsa) throws IOException

	/**
	 * Writes all reactions added to this generator to the given 
	 * file system provider, as reactions files.
	 * 
	 * <p>This method’s main purpose is to translate artificial reactions
	 * created with a builder into serialized reaction files.  
	 * 
	 * @param fsa
	 * 		The file system access to use for writing the results.
	 * 
	 * @param subPath
	 * 		The path in the given file system provider’s default output
	 * 		configuration to write the results to.
	 */
	def void writeReactions(IFileSystemAccess2 fsa, String subPath) throws IOException
}
