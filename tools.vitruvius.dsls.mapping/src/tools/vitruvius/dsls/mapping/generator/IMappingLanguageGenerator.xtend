package tools.vitruvius.dsls.mapping.generator

import tools.vitruvius.dsls.response.responseLanguage.Response
import java.util.Collection
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import java.util.Map

interface IMappingLanguageGenerator extends IGenerator {
	def void initialize()
	def Map<Resource, Collection<Response>> generateAndCreateResponses(Collection<Resource> input, IFileSystemAccess fsa)
}