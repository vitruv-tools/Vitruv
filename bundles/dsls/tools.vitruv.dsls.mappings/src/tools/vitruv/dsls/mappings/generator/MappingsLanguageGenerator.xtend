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
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider
import allElementTypes.AllElementTypesPackage

//import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

class MappingsLanguageGenerator implements IGenerator2 {
	@Inject FluentReactionsLanguageBuilder create
	@Inject	Provider<IReactionsGenerator> reactionsGeneratorProvider

	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
//		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
//		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
		static val AllElementTypes = new AllElementTypesDomainProvider().domain
		static val Root = AllElementTypesPackage.eINSTANCE.root
	
	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val reactionsGenerator = reactionsGeneratorProvider.get		
		val mappingsFiles = input?.contents?.filter(MappingsFile)
		for (mappingsFile : mappingsFiles) {
			reactionsGenerator.useResourceSet(mappingsFile.eResource.resourceSet)
			val segments = mappingsFile.mappingsSegments
			val mappingsPackage = mappingsFile.eResource.URI.trimSegments(1) + ".mappings"
			for (segment : segments) {
				val basePackageForSegment = mappingsPackage + "." + segment.name 
				val l2rReactionsFileGenerator = new MappingsReactionsFileGenerator(basePackageForSegment, segment, true, reactionsGenerator, create, mappingsFile);
				var l2rReactionsContext = l2rReactionsFileGenerator.createAndInitializeReactionsFile()
				val r2lReactionsFileGenerator = new MappingsReactionsFileGenerator(basePackageForSegment, segment, false, reactionsGenerator, create, mappingsFile);
				val r2lReactionsContext = r2lReactionsFileGenerator.createAndInitializeReactionsFile()
				for (mapping : segment.mappings) {
					val l2rGenerator = new MappingReactionsGenerator(basePackageForSegment, segment, true, reactionsGenerator, create, mapping)
					val r2lGenerator = new MappingReactionsGenerator(basePackageForSegment, segment, false, reactionsGenerator, create, mapping)
					l2rGenerator.generateReactionsAndRoutines(l2rReactionsContext)
					r2lGenerator.generateReactionsAndRoutines(r2lReactionsContext)
				}
				reactionsGenerator.addReactionsFile(l2rReactionsContext.file)	
				reactionsGenerator.addReactionsFile(r2lReactionsContext.file)	
			}
		}
		reactionsGenerator.generate(fsa)
		reactionsGenerator.writeReactions(fsa)
	}
}