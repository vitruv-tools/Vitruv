package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import org.eclipse.xtext.generator.IFileSystemAccess2

class MappingXtendGenerator extends AbstractMappingsSegmentGenerator {
	val Mapping mapping
	val IFileSystemAccess2 fsa
	
	new(String basePackage, MappingsSegment segment, Mapping mapping, IFileSystemAccess2 fsa) {
		super(basePackage, segment)
		this.mapping = mapping
		this.fsa = fsa
	}
		
	def generateXtendMappingsHalvesAndInstances() {
		for (mapping : segment.mappings) {
			val mappingFileName = basePackage + mapping.name + "Mapping.xtend"
			val mappingFilePath = segment.eResource.URI.trimSegments(1).toFileString + mappingFileName
			val content = '''a'''
			fsa.generateFile(mappingFilePath, content)
			// FIXME MK KEEP ON WORKING HERE
		}
	}
}