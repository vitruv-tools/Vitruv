package tools.vitruv.dsls.reactions.generator

import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import com.google.inject.Inject
import org.eclipse.xtext.generator.IGenerator

/**
 * This generator is called by Xtext when compiling Reaction language
 * resources. When it’s called, reactions will already have been translated
 * to their JVM types. So this generator only handles the remaining tasks,
 * namely generating the environment for the reactions.
 */
class ReactionsLanguageGenerator extends AbstractGenerator {

	@Inject IGenerator reactionGenerator

	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// reactions
		reactionGenerator.doGenerate(input, fsa)
	}
}
