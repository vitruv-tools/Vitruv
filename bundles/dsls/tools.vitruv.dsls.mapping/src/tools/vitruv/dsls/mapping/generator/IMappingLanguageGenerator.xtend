package tools.vitruv.dsls.mapping.generator

import java.util.Collection
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import java.util.Map
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction

interface IMappingLanguageGenerator extends IGenerator {
	def void initialize()
	def Map<Resource, Collection<Reaction>> generateAndCreateReactions(Collection<Resource> input, IFileSystemAccess fsa)
}