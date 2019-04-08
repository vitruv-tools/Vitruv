package tools.vitruv.dsls.mappings.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.xtext.generator.IGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import java.util.function.Supplier
import com.google.inject.Inject
import com.google.inject.Provider

//import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

class MappingsLanguageGenerator implements IGenerator2 {
	val FluentReactionsLanguageBuilder create = new FluentReactionsLanguageBuilder()

	@Inject
	new(Provider<IReactionsGenerator> reactionsGeneratorProvider)
   {
   		
   }
   	
	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
//		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
//		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val mappingsFiles = input?.contents?.filter(MappingsFile)
		for (mappingsFile : mappingsFiles) {
			val segments = mappingsFile.mappingsSegments
			val mappingsPackage = mappingsFile.eResource.URI.trimSegments(1) + ".mappings"
			for (segment : segments) {
				val basePackageForSegment = mappingsPackage + "." + segment.name 
				val l2rReactionsFileGenerator = new MappingsReactionsFileGenerator(basePackageForSegment, segment, true, create, mappingsFile);
				val l2rReactionsContext = l2rReactionsFileGenerator.createAndInitializeReactionsFile()
				val r2lReactionsFileGenerator = new MappingsReactionsFileGenerator(basePackageForSegment, segment, false, create, mappingsFile);
				val r2lReactionsContext = r2lReactionsFileGenerator.createAndInitializeReactionsFile()
				for (mapping : segment.mappings) {
					val l2rGenerator = new MappingReactionsGenerator(basePackageForSegment, segment, true, create, mapping)
					val r2lGenerator = new MappingReactionsGenerator(basePackageForSegment, segment, false, create, mapping)
					l2rGenerator.generateReactionsAndRoutines(l2rReactionsContext)
					r2lGenerator.generateReactionsAndRoutines(r2lReactionsContext)
				}
				l2rReactionsFileGenerator.generateFile(fsa);
				r2lReactionsFileGenerator.generateFile(fsa);
			}
		}
		
	}
}