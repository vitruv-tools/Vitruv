package tools.vitruv.dsls.mappings.generator

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment

class AbstractMappingsSegmentGenerator {
	@Accessors(PROTECTED_GETTER)
	val String basePackage
	@Accessors(PROTECTED_GETTER)
	val MappingsSegment segment
	
	new(String basePackage, MappingsSegment segment) {
		this.basePackage = basePackage
		this.segment = segment
	}
}