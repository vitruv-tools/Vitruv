package tools.vitruv.dsls.mappings.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.xtext.generator.IGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

class MappingsLanguageGenerator implements IGenerator2 {
	val FluentReactionsLanguageBuilder create = new FluentReactionsLanguageBuilder()
	
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
				val l2rReactionsFile = new MappingsReactionsFileGenerator(basePackageForSegment, segment, true, create).createAndInitializeReactionsFile()
				val r2lReactionsFile = new MappingsReactionsFileGenerator(basePackageForSegment, segment, false, create).createAndInitializeReactionsFile()
				for (mapping : segment.mappings) {
					val l2rGenerator = new MappingReactionsGenerator(basePackageForSegment, segment, true, create, mapping)
					val r2lGenerator = new MappingReactionsGenerator(basePackageForSegment, segment, false, create, mapping)
//					l2rReactionsFile += l2rGenerator.generateReactionsAndRoutines()
//					r2lReactionsFile += r2lGenerator.generateReactionsAndRoutines()
					new MappingXtendGenerator(basePackageForSegment, segment, mapping, fsa).generateXtendMappingsHalvesAndInstances()
				}
			}
		}
	}
}